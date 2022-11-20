package lab3;

public class Main {

    public static void main(String[] args) {
        Bufor bufor=new Bufor(5);

        Thread[] producerList= new Thread[3];
        Thread[] clientList= new Thread[3];
        boolean[] modeList= {false, false, true};
        for (int i=0;i<3;i++){
            producerList[i]=new Producer(bufor);
            clientList[i]=new Client(bufor,modeList[i]);
        }
        for (int i=0;i<3;i++){
            producerList[i].start();
            clientList[i].start();
        }


    }
}
