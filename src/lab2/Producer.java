package lab2;

public class Producer extends Thread{
    Bufor bufor;

    Producer(Bufor b){
        this.bufor=b;
    }
    public void run(){
        while(true){
            bufor.produce();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
