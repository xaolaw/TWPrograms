package lab6;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler extends Thread{
    private int limit;
    private LinkedBlockingDeque<MethodRequest> activeQueue;
    private LinkedBlockingQueue<MethodRequest> waitngQueue;

    public Scheduler(int limit_){
        this.activeQueue= new LinkedBlockingDeque<>();
        this.waitngQueue= new LinkedBlockingQueue<>();
        this.limit=limit_;

    }
    public void enqueue(MethodRequest methodRequest) {
        this.activeQueue.add(methodRequest);
    }
    public void dispatch() throws InterruptedException {
        while(true){
            //chceck if smth is waiting and its guard is met
            if(!waitngQueue.isEmpty() && waitngQueue.peek().guard()){
                MethodRequest methodRequest=waitngQueue.take();
                methodRequest.execute();
            }
            MethodRequest methodRequest=activeQueue.take();
            if(methodRequest.guard()){
                methodRequest.execute();
            }
            //cant do wait
            else{
                waitngQueue.put(methodRequest);
            }

        }
    }
}
