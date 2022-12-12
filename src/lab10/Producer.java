package lab10;

import org.jcsp.lang.*;

import java.util.Random;

public class Producer implements CSProcess {

    private One2OneChannel channelRequest;
    private One2OneChannel channelResponse;
    int maxToProduce;
    private Random generator;
    private int lastProduce;
    private int id;

    public Producer(One2OneChannel channelRequest_,One2OneChannel channelResponse_,int max,int id_){
        this.channelRequest=channelRequest_;
        this.channelResponse=channelResponse_;
        this.maxToProduce=max;
        //generator liczb
        this.generator=new Random(0);
        lastProduce =0;
        id=id_;
    }
    //
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    @Override
    public void run() {
        while(true){
            int toProduce = getRandomNumber(1,maxToProduce);
            if(lastProduce !=0){
                toProduce= lastProduce;
            }
            Message request = new Message(MessageType.PRODUCE_REQUEST,toProduce,channelResponse);
            System.out.println("[Producer " +id+ " ] chce wyprodukować: " + toProduce);
            channelRequest.out().write(request);
            Message response = (Message) channelResponse.in().read();
            if(response.getLoad()==0){
                System.out.println("[Producer " +id+ " ] wyprodukowałem:");
            } else if (response.getLoad()<0) {
                System.out.println("[Producer " +id+ " ] oddstawiam zadanie na później");
                lastProduce =-response.getLoad();
            }
        }
    }

    public One2OneChannel getChannelRequest() {
        return channelRequest;
    }
}
