package lab10;

import org.jcsp.lang.*;
import java.util.ArrayList;
import java.util.List;


public class Main  {
    public static void main (String[] args){
        int bufferSize=20;
        int clientsCount=3;
        List<CSProcess> processList = new ArrayList<>();

        for (int j=0;j<clientsCount;j++){
            //Producer
            final One2OneChannel[] prodChan = { Channel.one2one(), Channel.one2one() }; // Producers , request , response
            processList.add(new Producer(prodChan[0],prodChan[1],bufferSize/2,j));

            //Consumer
            final One2OneChannel[] consChan= { Channel.one2one(), Channel.one2one() }; // Consumer , request , response
            processList.add(new Consumer(consChan[0],consChan[1],bufferSize/2,j));
        }

        int end=processList.size();

        One2OneChannel nextSecretary = null;
        One2OneChannel previousSecretary = null;
        for (int i=0;i<end;i++){
            if(i==0){
                nextSecretary=Channel.one2one();
                previousSecretary=Channel.one2one();
            } else if (i>0 && i<end-1) {
                nextSecretary=Channel.one2one();
                previousSecretary=((Secretary) processList.get(end+i-1)).getNextSecretary();
            } else if (i==end - 1) {
                nextSecretary=((Secretary) processList.get(end)).getPreviousSecretary();
                previousSecretary=((Secretary) processList.get(end+i-1)).getNextSecretary();
            }
            var client = processList.get(i);
            if(client instanceof Producer){
                processList.add(new Secretary(nextSecretary,previousSecretary,((Producer) client).getChannelRequest(),bufferSize,i==0,i));
            }
            else{
                processList.add(new Secretary(nextSecretary,previousSecretary,((Consumer) client).getChannelRequest(),bufferSize,i==0,i));
            }

        }
        CSProcess[] convertedProcessList = new CSProcess[processList.size()];
        convertedProcessList = processList.toArray(convertedProcessList);

        Parallel par = new Parallel(convertedProcessList);
        par.run();

    }
}
