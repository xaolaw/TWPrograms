package lab6;

public class Proxy extends Thread{
    private Servant servant;
    private Scheduler scheduler;
    private int limit;

    public Proxy(int limit_){
        this.scheduler=new Scheduler(limit_);
        this.servant=new Servant(limit_);
        this.limit=limit_;
        new Thread("smth"){
            public void run(){
                try {
                    scheduler.dispatch();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
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
