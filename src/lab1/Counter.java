package lab1;

public class Counter {
    int value;

    Counter(){
        value=0;
    }

    public void increase(){
        value++;
    }
    public void decrease(){
        value--;
    }
    public void print(){
        System.out.println(value);
    }
}
