package com.suraj.gateway.ratelimit;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RateLimiter {

    private static final int LIMIT = 3;

    private static final long WINDOW_SIZE_MS = 10000; // 10 seconds

    private final Map<String, Queue<Long>>
            requestTimestamps =
            new ConcurrentHashMap<>();

    public boolean allowRequest(String ipAddress) {

        long now = System.currentTimeMillis();

        Queue<Long> requests =
                requestTimestamps.computeIfAbsent(
                        ipAddress,
                        key ->
                                new ConcurrentLinkedQueue<>()
                );

        while(!requests.isEmpty() && requests.peek() <= now - WINDOW_SIZE_MS) {
            requests.poll();
        }

        if(requests.size() >= LIMIT) {
            return false;
        }

        requests.offer(now);

        return true;
    }
}