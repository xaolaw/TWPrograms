package lab6;

public class myFuture {
    boolean isDone;
    int value;

    public myFuture() {
        isDone=false;
        value=0;
    }
    public void setValue(int value) {
        this.value = value;
        isDone=true;
    }
    public boolean isDone() {
        return isDone;
    }
    int get() {
        return value;
    }
}
