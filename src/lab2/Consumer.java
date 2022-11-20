package lab2;

public class Consumer extends Thread{
    Bufor bufor;

    Consumer(Bufor b){
        this.bufor=b;
    }
    public void run(){
        while(true){
            bufor.buy();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
