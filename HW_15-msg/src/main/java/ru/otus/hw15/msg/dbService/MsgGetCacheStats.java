package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.MsgToDB;
import ru.otus.hw15.msg.messageSystem.Address;

public class MsgGetCacheStats extends MsgToDB {
    public MsgGetCacheStats(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DBService dbService) {
        int hitCount = dbService.getCache().getHitCount();
        int missCount = dbService.getCache().getMissCount();
        dbService.getMS().sendMessage(new MsgGetCacheStatsAnswer(getTo(), getFrom(), hitCount, missCount));
        log.info("MsgGetCacheStats exec: hitCount - " + hitCount + ", missCount - " + missCount);
    }
}
