package conditions_4;

public class Producer extends Thread{

    Bufor bufor;
    int time,MAX_ITERATIONS;
    long additionalWork,normalWork;

    Producer(Bufor b,int time_){
        additionalWork=0;
        normalWork=0;
        this.bufor=b;
        MAX_ITERATIONS=50;
        this.time=time_;
    }
    public long getAdditionalWork() {
        return additionalWork;
    }

    public long getNormalWork() {
        return normalWork;
    }

    public void run() {
        long start = System.currentTimeMillis();
        long end = start + 20*1000;
        while (System.currentTimeMillis()<=end){
            bufor.addProduct();
            long start_=System.currentTimeMillis();
            long end_=start_+time;
            while(System.currentTimeMillis()<=end_){
                int sum=0;
                for (int i=0;i<MAX_ITERATIONS;i++){
                    sum+=Math.sin(1.22568917);
                }
                additionalWork++;
            }
            normalWork++;
        }

    }

}
