package lab3;

public class Client extends Thread{
    Bufor bufor;
    boolean mode;
    int loop;

    Client(Bufor b,boolean mode){
        this.bufor=b;
        this.mode=mode;
        loop=0;
    }
    public void run()  {
        while (true){
            loop+=1;
            bufor.getProduct(mode);
            System.out.println("Jestem konsument pętla: " + loop + (mode ? " Jestem dużym konsumentem" : " Jestem mały"));
        }
    }


}
