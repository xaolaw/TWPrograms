package lab7;

import org.jcsp.lang.*;

public class Main {
    public static void main (String[] args)
    { // Create channel objects
        final One2OneChannelInt[] prodChan = { Channel.one2oneInt(), Channel.one2oneInt() }; // Producers
        final One2OneChannelInt[] consReq = { Channel.one2oneInt(), Channel.one2oneInt() }; // Consumer requests
        final One2OneChannelInt[] consChan = { Channel.one2oneInt(), Channel.one2oneInt() }; // Consumer data
        // Create parallel construct
        CSProcess[] procList = { new Producer(prodChan[0], 0),
                new Producer(prodChan[1], 100),
                new Buffer(prodChan, consReq, consChan),
                new Consumer(consReq[0], consChan[0]),
                new Consumer(consReq[1], consChan[1]) }; // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    }
}
