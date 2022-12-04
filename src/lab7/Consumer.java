package lab7;
import org.jcsp.lang.*;
public class Consumer implements CSProcess{


    /** Consumer class: reads ints from input channel, displays them,
     then
     * terminates when a negative value is read.
     */

    private One2OneChannelInt in;
        private One2OneChannelInt req;
        public Consumer (final One2OneChannelInt req, final One2OneChannelInt in)
        {
            this.req = req;
            this.in = in;
        }
        public void run ()
        {
            int item;
            while (true)
            {
                req.out().write(0);
                item = in.in().read();
                if (item < 0)
                    break;
                System.out.println(item);
            }
            System.out.println("Consumer ended.");
        }

}
