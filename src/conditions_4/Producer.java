package conditions_4;

public class Producer extends Thread{

    Bufor bufor;
    int time,MAX_ITERATIONS;
    long additionalWork;

    Producer(Bufor b,int time_){
        additionalWork=0;
        this.bufor=b;
        MAX_ITERATIONS=50;
        this.time=time_;
    }
    public long getAdditionalWork() {
        return additionalWork;
    }

    public void run() {
        long start = System.currentTimeMillis();
        boolean running=true;
        while (running){
            bufor.addProduct();
            long start_=System.currentTimeMillis();
            long end_=System.currentTimeMillis();
            while((end_-start_)<=time){
                int sum=0;
                for (int i=0;i<MAX_ITERATIONS;i++){
                    sum+=Math.sin(1.22568917);
                }
                additionalWork++;
                end_=System.currentTimeMillis();
            }
            long end=System.currentTimeMillis();
            long took=((end - start) / 1000);
            if(took>=60){
                running=false;
            }
        }
    }

}
