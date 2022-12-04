package lab6;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] time={1,5,25,75};
        int[] Btime={1,5,25,75};
        int[] numberOfThreads = {3,9,27,81};
        int buforTime=5;
        //for (int j:numberOfThreads){
            for(int j:numberOfThreads){
                for (int k : time) {
                    long meanSumAdditional=0,meanSumNormal=0,meanBuforAdditional=0;
                    for( int n=0;n<3;n++){


                        Proxy bufor = new Proxy(10,buforTime);

                        Producer[] producerList = new Producer[j];
                        Consumer[] clientList = new Consumer[j];
                        for (int i = 0; i < j; i++) {
                            producerList[i] = new Producer(bufor, k);
                            clientList[i] = new Consumer(bufor, k);
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
                        bufor.getLoop().stop();
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
                        meanBuforAdditional+=bufor.getScheduler().getAdditionalWork()/3;
                        //System.out.println("Czas na dodatkowe zadanie " + k + " ms wykonano dodatkowej pracy: " + sum+ " wykonano normalnej pracy: "+sumNormal + " ilośc dodatkowej pracy schedulera: " + bufor.getScheduler().getAdditionalWork());

                    }
                    System.out.println("Czas schedulera: " + buforTime + " Czas K/P: " + k + " normalna praca: "+ meanSumNormal + " dodatkowa praca schedulera " + meanBuforAdditional + " dodatdkowa praca K/P: " + meanSumAdditional + " ilość wątków:" +j);
                    File file = new File("src/data/AO.txt");
                    try {
                        FileWriter outputfile = new FileWriter(file,true);

                        CSVWriter writer = new CSVWriter(outputfile);

                        String[] header = {Integer.toString(buforTime),Integer.toString(k),Long.toString(meanSumAdditional),Long.toString(meanSumNormal),Long.toString(meanBuforAdditional),Integer.toString(j)};
                        writer.writeNext(header);
                        writer.flush();
                        outputfile.close();
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        //}
        System.exit(0);
    }
}
