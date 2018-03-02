package ru.otus.hw15.msg.dbService;

import ru.otus.hw15.msg.app.FrontendService;
import ru.otus.hw15.msg.app.MsgToFrontend;
import ru.otus.hw15.msg.messageSystem.Address;

public class MsgGetCacheStatsAnswer extends MsgToFrontend {
    private final int hitCount;
    private final int missCount;

    public MsgGetCacheStatsAnswer(Address from, Address to, int hitCount, int missCount) {
        super(from, to);
        this.hitCount = hitCount;
        this.missCount = missCount;
        log.info("MsgGetCacheStats: hitCount - " + hitCount + ", missCount - " + missCount);
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.setCacheStats(hitCount, missCount);
        log.info("MsgGetCacheStats exec: hitCount - " + hitCount + ", missCount - " + missCount);
    }
}
