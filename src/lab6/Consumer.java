package lab6;

import java.util.Random;

public class Consumer extends Thread{
    private Proxy proxy;
    private int additionalWork;
    private Random generator;
    public Consumer(Proxy proxy_){
        this.proxy=proxy_;
        this.additionalWork=0;
        generator=new Random(0);
    }

    public int getRandomNumber(int min, int max) {
        return (generator.nextInt(max - min) + min);
    }

    @Override
    public void run() {
        while(true){
            try {
                myFuture futureObject= this.proxy.consume(getRandomNumber(1,proxy.getLimit()/2));
                //asynchronous work
                double a = 45;
                while (!futureObject.isDone()){
                    double b = Math.toRadians(a);
                    a=Math.sin(b);
                    additionalWork++;
                }
                //System.out.println("Dodatkowa praca konsumer: "+additionalWork);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
