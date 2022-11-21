package lab6;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Proxy bufor = new Proxy(10);

        int numberOfThreads = 3;

        Producer[] producerList = new Producer[numberOfThreads];
        Consumer[] clientList = new Consumer[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            producerList[i] = new Producer(bufor);
            clientList[i] = new Consumer(bufor);
        }
        for (int i = 0; i < numberOfThreads; i++) {
            producerList[i].start();
            clientList[i].start();
        }
    }
}
