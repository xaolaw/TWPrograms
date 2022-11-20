package lab6;

public class Consumer extends Thread{
    private Proxy proxy;
    int loop;
    public Consumer(Proxy proxy_){
        this.loop=0;
        this.proxy=proxy_;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void run() {
        while(true){
            loop++;
            try {
                this.proxy.consume(getRandomNumber(1,proxy.getLimit()/2),loop);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
