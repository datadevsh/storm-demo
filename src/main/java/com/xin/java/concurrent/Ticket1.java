package com.xin.java.concurrent;

public class Ticket1 {
    public static void main(String[] args) {
        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        Ticket t3 = new Ticket();
        t1.start();
        t2.start();
        t3.start();
    }
}

class Ticket extends Thread {
    private static int ticket = 100;

    public void run() {
        while (true) {
            if (ticket > 0)
                System.out.println(Thread.currentThread().getName() + ":" + ticket--);
        }
    }
}