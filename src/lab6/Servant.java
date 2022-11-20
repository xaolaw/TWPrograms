package lab6;

public class Servant {

    private int productCounter=0;
    private int limit;

    public Servant(int limit_){
        this.limit=limit_;
    }
    public void produce(int toAdd){
        //int toAdd=getRandomNumber(1,limit/2);
        productCounter+=toAdd;
        System.out.println("Dodałem: "+toAdd);
    }
    public void consume(int toConsume,int loop){
        //int toConsume=getRandomNumber(1,limit/2);
        productCounter-=toConsume;
        System.out.println("Skonsumowałem: "+toConsume+ " Moja pętla: "+loop);
    }
    public int getLimit() {
        return limit;
    }
    public int getProductCounter() {
        return productCounter;
    }
}
