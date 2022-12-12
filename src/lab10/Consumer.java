package lab10;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannel;

import java.util.Random;

public class Consumer implements CSProcess {
    private One2OneChannel channelRequest;
    private One2OneChannel channelResponse;
    int maxToProduce;
    private Random generator;
    private int lastConsume;
    private int id;

    public Consumer(One2OneChannel channelRequest_,One2OneChannel channelResponse_,int max,int id_){
        this.channelRequest=channelRequest_;
        this.channelResponse=channelResponse_;
        this.maxToProduce=max;
        //generator liczb
        this.generator=new Random(0);
        lastConsume=0;
        this.id=id_;
    }
    //
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void run() {
        while(true){
            int toConsume = getRandomNumber(1,maxToProduce);
            if(lastConsume!=0){
                toConsume=lastConsume;
            }
            Message request = new Message(MessageType.CONSUME_REQUEST,toConsume,channelResponse);
            System.out.println("[Consumer " +id+ " ] chce pobrać: " + toConsume);
            channelRequest.out().write(request);
            Message response = (Message) channelResponse.in().read();
            if(response.getLoad()==0){
                System.out.println("[Consumer " +id+ " ] pobrałem:");
            } else if (response.getLoad()<0) {
                System.out.println("[Consumer " +id+ " ] oddstawiam zadanie na później");
                lastConsume=-response.getLoad();
            }
        }
    }

    public One2OneChannel getChannelRequest() {
        return channelRequest;
    }
}
