package lab6;

import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler extends Thread{
    private int limit;
    private LinkedBlockingQueue<MethodRequest> activeQueue;
    private LinkedBlockingQueue<MethodRequest> waitngQueue;

    public Scheduler(int limit_){
        this.activeQueue= new LinkedBlockingQueue<MethodRequest>();
        this.waitngQueue= new LinkedBlockingQueue<MethodRequest>();
        this.limit=limit_;

    }
    public void enqueue(MethodRequest methodRequest) throws InterruptedException {
        this.activeQueue.put(methodRequest);
    }
    public void dispatch() throws InterruptedException {
        while(true){
            //chceck if smth is waiting and its guard
            if(!waitngQueue.isEmpty() && waitngQueue.peek().guard()){
                MethodRequest methodRequest=waitngQueue.take();
                methodRequest.execute();
            }
            //check if smth is to do
            else if (!activeQueue.isEmpty()) {
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
}
