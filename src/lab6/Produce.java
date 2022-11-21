package lab6;

public class Produce extends MethodRequest{
    private Servant servant;
    private int toAdd;
    myFuture futureBufor;
    public Produce(Servant servant_, int toAdd_,myFuture futureBufor_){
        this.servant=servant_;
        this.toAdd=toAdd_;
        this.futureBufor=futureBufor_;
    }

    @Override
    boolean guard() {
        return servant.getLimit()-servant.getProductCounter()>=toAdd;
    }

    @Override
    void execute() {
        servant.produce(toAdd);
        futureBufor.setValue(toAdd);

    }
}
