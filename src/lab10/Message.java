package lab10;

import org.jcsp.lang.One2OneChannel;

public class Message {
    private MessageType type;
    private int load;
    private One2OneChannel response;

    public Message(MessageType type_, int load_, One2OneChannel response_){
        this.type=type_;
        this.load=load_;
        this.response=response_;
    }

    public Message(MessageType type) {
        this.type = type;
    }

    public int getLoad() {
        return load;
    }

    public MessageType getType() {
        return type;
    }

    public One2OneChannel getResponse() {
        return response;
    }
}
