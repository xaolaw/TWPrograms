package lab6;

public class Consume extends MethodRequest{
    private Servant servant;
    private int toConsume;
    myFuture futureBufor;
    public Consume(Servant servant_,int toConsume_,myFuture futureBufor_) {
        this.servant=servant_;
        this.toConsume=toConsume_;
        this.futureBufor=futureBufor_;
    }

    @Override
    boolean guard() {
        return servant.getProductCounter()>=toConsume;
    }

    @Override
    void execute() {
        servant.consume(toConsume);
        futureBufor.setValue(toConsume);
    }
}
