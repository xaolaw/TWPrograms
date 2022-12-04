package lab6;

import java.util.Random;

public class Producer extends Thread{
    private Proxy proxy;

    private int time;
    private long additionalWork;
    private long normalWork;
    private long MAX_ITERATIONS;
    private Random generator;
    public Producer(Proxy proxy_,int time_){
        this.additionalWork=0;
        this.normalWork=0;
        this.proxy=proxy_;
        generator = new Random(0);
        MAX_ITERATIONS=50;
        this.time=time_;
    }
    public int getRandomNumber(int min, int max) {
        return (this.generator.nextInt(max - min) + min);
    }
    public long getAdditionalWork() {
        return additionalWork;
    }

    public long getNormalWork() {
        return normalWork;
    }

    @Override
    public void run() {
        long start,end;
        start=System.currentTimeMillis();
        boolean running=true;
        while(running){
            try {
                myFuture object= this.proxy.produce(getRandomNumber(1,proxy.getLimit()/2));
                //asynchronous work
                double sum = 0;

                while(!object.isDone()){
                    long start_=System.currentTimeMillis();
                    long end_=System.currentTimeMillis();
                    while((end_-start_)<=time){
                        for (int i=0;i<MAX_ITERATIONS;i++){
                            sum+=Math.sin(1.22568917);
                        }
                        additionalWork++;
                        end_=System.currentTimeMillis();
                    }
                }
                normalWork++;
                //System.out.println("Producer dodatkowa praca: "+additionalWork);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            /*end = System.currentTimeMillis();
            //after 60 sec we end the program
            long took=((end - start) / 1000);
            if(took>=60){
                //System.out.println("Producent mój id: "+currentThread().getId()+" dodatkowa praca ilośc pętli: "+additionalWork+ " zwykłej pracy: "+normalWork);
                running=false;
            }*/
        }
    }
}
