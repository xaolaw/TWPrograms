package lab2;

public class Main extends Thread{


    public static void main(String[] args) {

        int numberOfConsumers=6;
        int numberOfProducers=3;
        int maxProducts=6;

        Bufor bufor = new Bufor(maxProducts);

        Thread[] consumers=new Thread[numberOfConsumers];
        Thread[] producers=new Thread[numberOfProducers];

        for (int i=0;i<numberOfConsumers;i++){
            consumers[i]=new Consumer(bufor);
        }
        for (int j=0;j<numberOfProducers;j++){
            producers[j]=new Producer(bufor);
        }
        for (int i=0;i<numberOfConsumers;i++){
            consumers[i].start();
        }
        for (int j=0;j<numberOfProducers;j++){
            producers[j].start();
        }


    }
}
