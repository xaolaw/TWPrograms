package lab3;

public class Producer extends Thread{

    Bufor bufor;

    Producer(Bufor b){
        this.bufor=b;
    }

    public void run() {
        while (true){
            bufor.addProduct();
        }
    }

}
