package com.xin.java.concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonRunnable {


    private static String[] sentences = new String[]{"my", "storm", "word", "count", "Supervisor", "summary", "Topology", "Name"};
    private static Random random = new Random();

    public static void operateMap(ConcurrentHashMap<String, Integer> map) {

        int i = random.nextInt(sentences.length);
        String word = sentences[i];

        if (map.containsKey(word)) {
            map.put(word, map.get(word) + 1);
        } else {
            map.put(word, 1);
        }
        System.out.println(map);
    }
}

class MyThread implements Runnable {

    private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(new MyThread()).start();
        }
    }

    @Override
    public void run() {
        SingletonRunnable.operateMap(map);
    }
}
