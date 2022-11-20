package lab6;

abstract class MethodRequest {
    private Servant servant;
    abstract boolean guard();
    abstract void execute();


}
