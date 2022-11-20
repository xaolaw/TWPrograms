package lab6;

public class Main {
    public static void main(String[] args){
        Proxy bufor = new Proxy(10);

        int numberOfThreads=1;

        Thread[] producerList= new Thread[numberOfThreads];
        Thread[] clientList= new Thread[numberOfThreads];

        new Thread("smth"){
            public void run(){
                try {
                    bufor.getScheduler().dispatch();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        for (int i=0;i<numberOfThreads;i++){
            producerList[i]=new Producer(bufor);
            clientList[i]=new Consumer(bufor);
        }
        for (int i=0;i<numberOfThreads;i++){
            producerList[i].start();
            clientList[i].start();
        }
    }
}
