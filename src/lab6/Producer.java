package lab6;

import java.util.Random;

public class Producer extends Thread{
    private Proxy proxy;

    private long additionalWork,normalWork,MAX_ITERATIONS;
    private Random generator;
    public Producer(Proxy proxy_){
        this.additionalWork=0;
        this.normalWork=0;
        this.proxy=proxy_;
        generator = new Random(0);
        MAX_ITERATIONS=50;
    }
    public int getRandomNumber(int min, int max) {
        return (this.generator.nextInt(max - min) + min);
    }

    @Override
    public void run() {
        long start,end;
        start=System.currentTimeMillis();
        while(true){
            try {
                myFuture object= this.proxy.produce(getRandomNumber(1,proxy.getLimit()/2));
                //asynchronous work
                double sum = 0;
                while(!object.isDone()){
                    for (int i=0;i<MAX_ITERATIONS;i++){
                        sum+=Math.sin(getRandomNumber(1,10));
                    }
                    additionalWork++;
                }
                normalWork++;
                //System.out.println("Producer dodatkowa praca: "+additionalWork);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            end = System.currentTimeMillis();
            //every 5 second raport
            long took=((end - start) / 1000);
            if(took>=5){
                System.out.println("Mój id: "+currentThread().getId()+" dodatkowa praca ilośc pętli: "+additionalWork+ " zwykłej pracy: "+normalWork);
                start=System.currentTimeMillis();
            }
        }
    }
}
