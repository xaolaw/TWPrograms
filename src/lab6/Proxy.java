package lab6;

public class Proxy extends Thread{
    private Servant servant;
    private Scheduler scheduler;
    private int limit;
    private Thread loop;

    public Proxy(int limit_,int time_){
        this.scheduler=new Scheduler(limit_,time_);
        this.servant=new Servant(limit_);
        this.limit=limit_;
        this.loop = new Thread("smth"){
            public void run(){
                try {
                    scheduler.dispatch();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        loop.start();
    }

    public Thread getLoop() {
        return loop;
    }

    public myFuture produce(int value) throws InterruptedException {
        myFuture promise = new myFuture();
        MethodRequest requestProduce = new Produce(servant,value,promise);
        scheduler.enqueue(requestProduce);
        return promise;
    }
    public myFuture consume(int value) throws InterruptedException {
        myFuture promise = new myFuture();
        MethodRequest requestConsume = new Consume(servant,value,promise);
        scheduler.enqueue(requestConsume);
        return promise;
    }
    public int getLimit() {
        return limit;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
