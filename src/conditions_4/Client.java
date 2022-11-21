package conditions_4;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import com.opencsv.CSVWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Client extends Thread{
    Bufor bufor;
    boolean mode;
    int loop,time,maxLoop,numberOfThreads,MAX_ITERATIONS;
    long additionalWork;

    Client(Bufor b, boolean mode,int numberOfThreads_,int maxLoop_,int time_){
        this.bufor=b;
        this.mode=mode;
        loop=0;
        numberOfThreads=numberOfThreads_;
        maxLoop=maxLoop_;
        MAX_ITERATIONS=50;
        additionalWork=0;
        this.time=time_;
    }
    public long getAdditionalWork() {
        return additionalWork;
    }
    public void run() {
        long start = System.currentTimeMillis();
        boolean running=true;
        while (running) {
            bufor.getProduct(mode);
            //System.out.println("Jestem konsument pętla: " + loop + (mode ? " Jestem dużym konsumentem" : " Jestem mały"));
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
        /*
        long endRt = System.nanoTime();
        long elapsedTimeRt=endRt - startRt;
        long convertMiliRt = TimeUnit.MILLISECONDS.convert(elapsedTimeRt, TimeUnit.NANOSECONDS);
        long convertSecRt = TimeUnit.SECONDS.convert(elapsedTimeRt, TimeUnit.NANOSECONDS);
        System.out.println("Czas potrzebny na wykonanie programu: "+convertSecRt + "s/ " + convertMiliRt + "ms");

        long elapsedTimeCpu = cpuTime(this);
        long convertMiliCpu = TimeUnit.MILLISECONDS.convert(elapsedTimeCpu,TimeUnit.NANOSECONDS);
        long convertSecCpu = TimeUnit.SECONDS.convert(elapsedTimeCpu,TimeUnit.NANOSECONDS);
        System.out.println("Czas potrzebny na wykonanie programu Cpu: "+convertSecCpu + "s/ " + convertMiliCpu + "ms");


        File file = new File("src/4condv2.txt");
        try {
            FileWriter outputfile = new FileWriter(file,true);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "4conditions", Long.toString(convertMiliRt), Long.toString(convertSecRt),Long.toString(convertMiliCpu), Long.toString(convertSecCpu) ,Long.toString(maxLoop),Integer.toString(numberOfThreads)};
            writer.writeNext(header);
            writer.flush();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    private static long cpuTime(Thread thr) {
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        try {
            return mxBean.getThreadCpuTime(thr.getId());
        } catch (UnsupportedOperationException e) {
            System.out.println(e.toString());
        }
        return 0;
    }

         */

}
