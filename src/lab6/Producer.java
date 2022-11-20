package lab6;

public class Producer extends Thread{
    private Proxy proxy;

    public Producer(Proxy proxy_){
        this.proxy=proxy_;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void run() {
        while(true){
            try {
                myFuture object= this.proxy.produce(getRandomNumber(1,proxy.getLimit()/2));
                //object.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
