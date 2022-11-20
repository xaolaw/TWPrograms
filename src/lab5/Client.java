package lab5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

        long elapsedTimeCpu = cpuTime(this);
        long convertMiliCpu = TimeUnit.MILLISECONDS.convert(elapsedTimeCpu,TimeUnit.NANOSECONDS);

        File file = new File("src/3locksv2.txt");
        try {
            FileWriter outputfile = new FileWriter(file,true);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "3locks", Long.toString(convertMiliRt),Long.toString(convertMiliCpu) ,Long.toString(maxLoop),Integer.toString(numberOfThreads)};
            writer.writeNext(header);
            writer.close();
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
