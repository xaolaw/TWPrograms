package lab6;

import java.util.Random;

public class Consumer extends Thread{
    private Proxy proxy;
    private int time;
    private long additionalWork,normalWork,MAX_ITERATIONS;
    private Random generator;
    public Consumer(Proxy proxy_,int time_){
        this.proxy=proxy_;
        this.additionalWork=0;
        generator=new Random(0);
        this.time=time_;
        MAX_ITERATIONS=50;
    }
    public long getAdditionalWork() {
        return additionalWork;
    }

    public long getNormalWork() {
        return normalWork;
    }

    public int getRandomNumber(int min, int max) {
        return (generator.nextInt(max - min) + min);
    }

    @Override
    public void run() {
        long start,end;
        start=System.currentTimeMillis();
        boolean runnning=true;
        while(runnning){
            try {
                myFuture futureObject= this.proxy.consume(getRandomNumber(1,proxy.getLimit()/2));
                //asynchronous work
                double sum = 0;
                while(!futureObject.isDone()){
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
                //System.out.println("Konsument mój id: "+currentThread().getId()+" dodatkowa praca ilośc pętli: "+additionalWork+ " zwykłej pracy: "+normalWork);
                runnning=false;
            }*/
        }
    }
}
