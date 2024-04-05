package ru.javawebinar.basejava;

public class MainDeadlock implements Runnable {
    private static class Resource {
    }

    private final Resource resource1 = new Resource();
    private final Resource resource2 = new Resource();

    public void doFirst() {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName()
                    + " take resource1");
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName()
                        + " take resource2");
            }
        }
    }

    public void doSecond() {
        synchronized (resource2) {
            System.out.println(Thread.currentThread().getName()
                    + " take resource2");
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName()
                        + " take resource1");
            }
        }
    }

    public void run() {
        doFirst();
        doSecond();
    }

    public static void main(String[] args) {
        MainDeadlock mainDeadlock = new MainDeadlock();
        Thread thread1 = new Thread(mainDeadlock, "Thread1");
        Thread thread2 = new Thread(mainDeadlock, "Thread2");
        thread1.start();
        thread2.start();
    }
}
