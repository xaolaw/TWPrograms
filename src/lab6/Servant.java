package lab6;

public class Servant {

    private int productCounter=0;
    private int limit;

    public Servant(int limit_){
        this.limit=limit_;
    }
    public void produce(int toAdd){
        productCounter+=toAdd;
        //System.out.println("Dodałem: "+toAdd);
    }
    public void consume(int toConsume){
        productCounter-=toConsume;
        //System.out.println("Skonsumowałem: "+toConsume);
    }
    public int getLimit() {
        return limit;
    }
    public int getProductCounter() {
        return productCounter;
    }
}
