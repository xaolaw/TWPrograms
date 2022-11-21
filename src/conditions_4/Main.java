package conditions_4;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int[] time={1,5,25,50,100};

        for (int k : time) {
            Bufor bufor = new Bufor(10);

            int numberOfThreads = 3;
            int numberOfLoops = 500000;

            Producer[] producerList = new Producer[numberOfThreads];
            Client[] clientList = new Client[numberOfThreads];
            for (int i = 0; i < numberOfThreads; i++) {
                producerList[i] = new Producer(bufor,k);
                clientList[i] = new Client(bufor, false, numberOfThreads, numberOfLoops,k);
            }
            for (int i = 0; i < numberOfThreads; i++) {
                producerList[i].start();
                clientList[i].start();
            }
            for (int i = 0; i < numberOfThreads; i++) {
                producerList[i].join();
                clientList[i].join();
            }
            long sum = 0;
            for (int i = 0; i < numberOfThreads; i++) {
                sum += producerList[i].getAdditionalWork();
                sum += clientList[i].getAdditionalWork();
            }
            System.out.println("Czas na dodatkowe zadanie " + k + " ms wykonano dodatkowej pracy: " + sum);
        }
        System.exit(0);

    }
}
