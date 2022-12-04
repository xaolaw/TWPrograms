package conditions_4;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int[] time={1,5,25,75};
        int[] Btime={1,5,25,75};
        int[] numberOfThreads = {3,27,81};
        int buforTime=5;
        for(int j:numberOfThreads) {
            for (int k : time) {
                long meanSumAdditional=0,meanSumNormal=0,meanBuforAdditional=0;
                for (int n = 0; n < 3; n++) {
                    Bufor bufor = new Bufor(10,buforTime);

                    int numberOfLoops = 500000;

                    Producer[] producerList = new Producer[j];
                    Client[] clientList = new Client[j];
                    for (int i = 0; i < j; i++) {
                        producerList[i] = new Producer(bufor,k);
                        clientList[i] = new Client(bufor, false, j, numberOfLoops,k);
                    }
                    for (int i = 0; i < j; i++) {
                        producerList[i].start();
                        clientList[i].start();
                    }
                    //we wait 20 seconds
                    Thread.sleep(20000);
                    for (int i=0;i<j;i++){
                        producerList[i].stop();
                        clientList[i].stop();
                    }
                    for (int i = 0; i < j; i++) {
                        producerList[i].join();
                        clientList[i].join();
                    }
                    long sum = 0,sumNormal=0;
                    for (int i = 0; i < j; i++) {
                        sum += producerList[i].getAdditionalWork();
                        sum += clientList[i].getAdditionalWork();
                        sumNormal += producerList[i].getNormalWork();
                        sumNormal += clientList[i].getNormalWork();
                    }
                    meanSumAdditional+=sum/3;
                    meanSumNormal+=sumNormal/3;
                    meanBuforAdditional+=bufor.getAdditionalWork()/3;
                }

                System.out.println("Czas na dodatkowe zadanie " + k + " ms wykonano dodatkowej pracy: " + meanSumAdditional+ " noramalna praca: "+meanSumNormal + " praca bufora "+meanBuforAdditional + " ilość wątków " + j);
                File file = new File("src/data/FC.txt");
                try {
                    FileWriter outputfile = new FileWriter(file,true);

                    CSVWriter writer = new CSVWriter(outputfile);

                    String[] header = {Integer.toString(buforTime),Integer.toString(k),Long.toString(meanSumAdditional),Long.toString(meanSumNormal),Long.toString(meanBuforAdditional)};
                    writer.writeNext(header);
                    writer.flush();
                    outputfile.close();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.exit(0);
    }
}
