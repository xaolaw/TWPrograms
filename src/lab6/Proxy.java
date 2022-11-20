package lab6;

public class Proxy extends Thread{
    private Servant servant;
    private Scheduler scheduler;
    private int limit;

    public Proxy(int limit_){
        this.scheduler=new Scheduler(limit_);
        this.servant=new Servant(limit_);
        this.limit=limit_;
    }
    public myFuture produce(int value) throws InterruptedException {
        myFuture promise= new myFuture();
        MethodRequest requestProduce = new Produce(servant,value,promise);
        scheduler.enqueue(requestProduce);
        return promise;
    }
    public void consume(int value,int loop) throws InterruptedException {
        MethodRequest requestConsume = new Consume(servant,value,loop);
        scheduler.enqueue(requestConsume);
    }
    public int getLimit() {
        return limit;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
