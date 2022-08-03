package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            char[] process = {'|', '/', '-', '\\'};
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[i % process.length]);
                i++;
                Thread.sleep(500);
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread progress = new Thread(new ConsoleProgress());
            progress.start();
            Thread.sleep(5000);
            progress.interrupt();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
