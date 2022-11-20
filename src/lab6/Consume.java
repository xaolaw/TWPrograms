package lab6;

public class Consume extends MethodRequest{
    private Servant servant;
    private int toConsume;
    private int loop;
    public Consume(Servant servant_,int toConsume_,int loop) {
        this.servant=servant_;
        this.toConsume=toConsume_;
        this.loop=loop;
    }

    @Override
    boolean guard() {
        return servant.getProductCounter()>=toConsume;
    }

    @Override
    void execute() {
        servant.consume(toConsume,loop);
    }
}
