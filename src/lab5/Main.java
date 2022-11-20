package lab5;

public class Main {

    public static void main(String[] args) {
        Bufor bufor=new Bufor(5);

        int numberOfThreads=12;
        int maxLoop=1000000;

        Thread[] producerList= new Thread[numberOfThreads];
        Thread[] clientList= new Thread[numberOfThreads];
        //boolean[] modeList= {false, false, true};

        for (int i=0;i<numberOfThreads;i++){
            producerList[i]=new Producer(bufor);
            clientList[i]=new Client(bufor,false,numberOfThreads,maxLoop);
        }

        for (int i=0;i<numberOfThreads;i++){
            producerList[i].start();
            clientList[i].start();
        }


    }
}
