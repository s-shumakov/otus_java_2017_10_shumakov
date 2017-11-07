package ru.otus.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.sun.management.GcInfo;

import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

public class MemoryUtil {
    private static HashMap<String, Integer> gcCount = new HashMap<>();
    private static HashMap<String, Long> gcDuration = new HashMap<>();
    private static HashMap<String, GcStat> gcStats = new HashMap<>();

    private static class GcStat {
        private Integer count = 0;
        private Long duration = 0L;

        public GcStat(Integer count, Long duration) {
            this.count = count;
            this.duration = duration;
        }
    }

    // Обработчик сообщений о сборке мусора
    private static NotificationListener gcHandler = new NotificationListener() {
        @Override
        public void handleNotification(Notification notification, Object handback) {
            if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                GarbageCollectionNotificationInfo notifInfo = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                GcInfo gcInfo = notifInfo.getGcInfo();
                String gcName = notifInfo.getGcName();
                System.out.println("GC " + gcName + ", Action: " + notifInfo.getGcAction() + ", Duration: " + gcInfo.getDuration());
//                System.out.println("Duration: " + gcInfo.getDuration());
                gcStats.get(gcName).duration += gcInfo.getDuration();
                gcStats.get(gcName).count++;

                gcDuration.put(gcName, gcDuration.get(gcName) + gcInfo.getDuration());
                for (String key : gcDuration.keySet()){
                    System.out.printf("Duration: %s %d\n", key, gcDuration.get(key));
                }
                gcCount.put(gcName, gcCount.get(gcName) + 1);
                for (String key : gcCount.keySet()){
                    System.out.printf("Run times: %s, %d\n", key, gcCount.get(key));
                }
                for (String key : gcStats.keySet()){
                    System.out.printf("GC: %s, \t Count: %d, \t Duration: %d\n", key, gcStats.get(key).count, gcStats.get(key).duration);
                }
            }
        }
    };

    /**
     * Запускает процесс мониторинга сборок мусора.
     */
    public static void startGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
            System.out.println("GC bean: " + mBean.getName());
            gcStats.put(mBean.getName(), new GcStat(0, 0L));
            gcCount.put(mBean.getName(), 0);
            gcDuration.put(mBean.getName(), 0L);
        }
    }

    /**
     * Останавливает процесс мониторинга сборок мусора.
     */
    public static void stopGCMonitor() {
        for(GarbageCollectorMXBean mBean: ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) mBean).removeNotificationListener(gcHandler);
            } catch(ListenerNotFoundException e) {
            }
        }
    }
}