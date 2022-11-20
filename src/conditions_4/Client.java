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
    int loop;
    int maxLoop;
    int numberOfThreads;

    Client(Bufor b, boolean mode,int numberOfThreads_,int maxLoop_){
        this.bufor=b;
        this.mode=mode;
        loop=0;
        numberOfThreads=numberOfThreads_;
        maxLoop=maxLoop_;
    }
    public void run()  {
        long startRt=System.nanoTime();
        while (true){
            loop+=1;
            bufor.getProduct(mode);
            System.out.println("Jestem konsument pętla: " + loop + (mode ? " Jestem dużym konsumentem" : " Jestem mały"));
            if (loop==maxLoop){
                break;
            }
        }
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

}
