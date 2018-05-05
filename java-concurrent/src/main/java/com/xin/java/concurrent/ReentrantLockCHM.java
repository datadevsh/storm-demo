package com.xin.java.concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockCHM implements Runnable {

    private String[] sentences = new String[]{"my", "storm", "word", "count", "Supervisor", "summary", "Topology", "Name"};
    private Random random = new Random();
    private static Lock lock = new ReentrantLock();

    private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new ReentrantLockCHM(), "线程" + i).start();
        }
    }

    @Override
    public void run() {

        int i = random.nextInt(sentences.length);
        String word = sentences[i];

        lock.lock();
        if (map.containsKey(word)) {
            map.put(word, map.get(word) + 1);
        } else {
            map.put(word, 1);
        }
        System.out.println(map);
        lock.unlock();
    }
}
