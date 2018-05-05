package com.xin.java.concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共享变量：static ConcurrentHashMap
 * 同步：ConcurrentHashMap
 * <p>
 * 大多数时候运行结正确
 */
public class StaticConcurrentHashMap implements Runnable {

    private String[] sentences = new String[]{"my", "storm", "word", "count", "Supervisor", "summary", "Topology", "Name"};
    private Random random = new Random();

    private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new StaticConcurrentHashMap(), "线程" + i).start();
        }
    }

    @Override
    public void run() {

        int i = random.nextInt(sentences.length);
        String word = sentences[i];
//        synchronized (ConcurrentHashMap.class) {

            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
            System.out.println(map);
//        }

    }
}

/*


{count=1}
{count=1, my=1}
{storm=1, count=1, my=1}
{storm=1, count=2, my=1}
{storm=1, count=2, my=1, word=1}
{storm=1, count=2, Topology=1, my=1, word=1}
{storm=1, count=2, Topology=2, my=1, word=1}
{storm=1, count=2, Topology=3, my=1, word=1}
{storm=1, count=2, Topology=3, my=1, word=2}
{storm=1, count=2, Topology=3, my=1, word=2, Name=1}


{Name=1}
{storm=1, Name=1}
{storm=2, my=1, Name=1}
{summary=1, storm=2, my=1, Name=1}
{storm=1, my=1, Name=1}
{summary=1, storm=2, my=2, Name=1}
{summary=1, storm=2, my=3, Name=1}
{summary=1, Supervisor=1, storm=2, Topology=1, my=3, Name=1}
{summary=1, Supervisor=1, storm=2, Topology=1, my=3, Name=1}
{summary=1, Supervisor=1, storm=2, Topology=1, my=4, Name=1}

 */