package com.xin.java.concurrent;

import java.util.HashMap;
import java.util.Random;

/**
 * 共享变量：static HashMap
 * 同步：synchronized (map)
 *
 * 可能会发生并发异常
 */
public class HashMapSync implements Runnable {

    private String[] sentences = new String[]{"my", "storm", "word", "count", "Supervisor", "summary", "Topology", "Name"};
    private Random random = new Random();

    private static HashMap<String, Integer> map = new HashMap<String, Integer>();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(new HashMapSync(), "线程" + i).start();
        }
    }

    public void run() {

        int i = random.nextInt(sentences.length);
        String word = sentences[i];
        synchronized (HashMapSync.class) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }
        System.out.println(map);

    }
}
/*

可能会发生并发异常

{summary=1, Supervisor=1}
Exception in thread "线程5" java.util.ConcurrentModificationException
{summary=1, Supervisor=1}
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1437)
{summary=1, Supervisor=1, storm=1, my=1}
	at java.util.HashMap$EntryIterator.next(HashMap.java:1471)
{summary=1, Supervisor=1, storm=2, my=1}
	at java.util.HashMap$EntryIterator.next(HashMap.java:1469)
{summary=1, Supervisor=1, storm=4, my=1}
	at java.util.AbstractMap.toString(AbstractMap.java:554)
{summary=1, Supervisor=1, storm=4, my=1}
	at java.lang.String.valueOf(String.java:2994)
{summary=1, Supervisor=2, storm=4, my=2}
	at java.io.PrintStream.println(PrintStream.java:821)
{summary=1, Supervisor=1, storm=4, my=2}
	at com.xin.java.concurrent.CHMDemo.run(CHMDemo.java:36)
{summary=1, Supervisor=2, storm=4, Topology=1, my=2}
	at java.lang.Thread.run(Thread.java:748)
 */