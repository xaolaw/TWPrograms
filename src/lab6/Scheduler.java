package lab6;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler extends Thread{
    private int limit,MAX_ITERATIONS,time;
    private LinkedBlockingDeque<MethodRequest> activeQueue;
    private LinkedBlockingQueue<MethodRequest> waitngQueue;
    private long additionalWork;


    public Scheduler(int limit_,int time_){
        this.activeQueue= new LinkedBlockingDeque<>();
        this.waitngQueue= new LinkedBlockingQueue<>();
        this.limit=limit_;
        MAX_ITERATIONS=50;
        additionalWork=0;
        this.time=time_;

    }
    public void enqueue(MethodRequest methodRequest) {
        this.activeQueue.add(methodRequest);
    }
    public long getAdditionalWork() {
        return additionalWork;
    }
    void compute(){
        double sum = 0;
        long start_=System.currentTimeMillis();
        long end_ = start_ + time;
        while(System.currentTimeMillis()<end_) {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                sum += Math.sin(1.22568917);
            }
            additionalWork++;
        }

    }
    public void dispatch() throws InterruptedException {
        while(true){
            //chceck if smth is waiting and its guard is met
            if(!waitngQueue.isEmpty() && waitngQueue.peek().guard()){
                MethodRequest methodRequest=waitngQueue.take();
                methodRequest.execute();
                compute();
            }
            //scheduler additional work
            MethodRequest methodRequest=activeQueue.take();
            if(methodRequest.guard()){
                methodRequest.execute();
                compute();
            }
            //cant do wait
            else{
                waitngQueue.put(methodRequest);
            }


        }
    }
}
