package com.xin.java.concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步：ConcurrentHashMap
 *
 * 结果是乱的
 */
public class OnlyConcurrentHashMap implements Runnable {

    private String[] sentences = new String[]{"my", "storm", "word", "count", "Supervisor", "summary", "Topology", "Name"};
    private Random random = new Random();

    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new OnlyConcurrentHashMap(), "线程" + i).start();
        }
    }

    @Override
    public void run() {

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
/*

结果是乱的

{storm=1, count=1}
{summary=1, storm=1, count=1}
{storm=1, count=1}
{summary=2, storm=1, count=1}
{summary=3, Supervisor=2, storm=1, count=1}
{summary=2, Supervisor=2, storm=1, count=1}
{summary=3, Supervisor=2, storm=1, count=1}
{summary=3, Supervisor=2, storm=1, count=1, Name=1}
{summary=3, Supervisor=2, storm=1, count=1, Name=2}
{summary=4, Supervisor=2, storm=1, count=1, Name=2}


 */