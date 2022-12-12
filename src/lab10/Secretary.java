package lab10;

import org.jcsp.lang.*;

public class Secretary implements CSProcess {

    //channel skierowany do następnego sekretarza
    private One2OneChannel nextSecretary;
    //channel skierowany do P/K w celu odebrania paracy
    private One2OneChannel fromJob;
    //channel potrzebny aby proisć poprzedniego sekretarza o token
    private One2OneChannel previousSecretary;
    private int bufferSize;
    private static int  currentBufferState;
    boolean tokenPossesion;
    private int id;

    public Secretary(One2OneChannel nextSecretary_,One2OneChannel previousSecretary_ ,One2OneChannel fromJob_,int bufferSize_,boolean hasToken,int id_){
        this.nextSecretary=nextSecretary_;
        this.previousSecretary=previousSecretary_;
        this.fromJob=fromJob_;
        this.bufferSize=bufferSize_;
        currentBufferState=0;
        tokenPossesion=hasToken;
        this.id=id_;
    }

    @Override
    public void run() {
        Guard[] guard = {fromJob.in()};
        Alternative alt = new Alternative(guard);
        while(true){
            int index=alt.select();
            if(tokenPossesion){
                fulfillOrder();
            }
            else{
                Message tokenMessage= (Message) previousSecretary.in().read();
                /*while(tokenMessage==null){
                    tokenMessage= (Message) previousSecretary.in().read();
                }*/
                if(tokenMessage.getType()==MessageType.TOKEN){
                    tokenPossesion=true;
                    fulfillOrder();
                }
            }
            passToken();
        }

    }
    void fulfillOrder(){
        Message request = (Message) fromJob.in().read();
        switch(request.getType()){
            case PRODUCE_REQUEST -> {
                if(isAbleToProduce(request.getLoad())){
                    //produkuj
                    produce(request);
                }
                else{
                    Message response = new Message(MessageType.PRODUCE_RESPONSE,-1,request.getResponse());
                    request.getResponse().out().write(response);
                }
            }
            case CONSUME_REQUEST -> {
                if(isAbleToConsume(request.getLoad())){
                    //konsumuj
                    consume(request);
                }
                else{
                    Message response = new Message(MessageType.CONSUME_RESPONSE,-request.getLoad(),request.getResponse());
                    request.getResponse().out().write(response);
                }
            }
        }
    }
    void produce(Message request){
        System.out.println("[Bufor "+ id + " ] produkuje: "+ request.getLoad()+ " stan przed: " + currentBufferState);
        currentBufferState+=request.getLoad();
        //wyslij odpowiedz o sukcesie produkcji 0 w load = sukces
        Message response = new Message(MessageType.PRODUCE_RESPONSE,0,request.getResponse());
        request.getResponse().out().write(response);
    }
    boolean isAbleToProduce(int toProduce){
        return currentBufferState+toProduce<=bufferSize;
    }
     void consume(Message request){
         System.out.println("[Bufor "+ id + " ] pobieranie: "+ request.getLoad()+ " stan przed: " + currentBufferState);
         currentBufferState-=request.getLoad();
         //wyslij odpowiedz o sukcesie konsumcji 0 w load = sukces
         Message response = new Message(MessageType.CONSUME_RESPONSE,0,request.getResponse());
         request.getResponse().out().write(response);
     }
     boolean isAbleToConsume(int toConsume){
        return  currentBufferState-toConsume>=0;
     }
     void passToken(){
        Message token = new Message(MessageType.TOKEN);
        System.out.println("[Bufor "+ id + " ] podaję token dalej stan aktualny bufora: " + currentBufferState);
        nextSecretary.out().write(token);
        this.tokenPossesion=false;
     }

    public One2OneChannel getNextSecretary() {
        return nextSecretary;
    }

    public One2OneChannel getPreviousSecretary() {
        return previousSecretary;
    }
}
