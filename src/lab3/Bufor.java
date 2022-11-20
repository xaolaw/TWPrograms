package lab3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor extends Thread{

    int productCounter=0;

    int limit;
    ReentrantLock lock = new ReentrantLock();
    Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    Bufor(int _limit){
        limit=2*_limit;
    }

     void addProduct() {
        try{
            int toAdd=getRandomNumber(1,limit/2);
            lock.lock();
            while(productCounter+toAdd>limit){
                stackFullCondition.await();
            }
            productCounter+=toAdd;
           // System.out.println("Wyprodukowane: dalem " +toAdd+ " jest: " + productCounter);
            stackEmptyCondition.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

     void getProduct(boolean mode){
        try{
            int toGet=getRandomNumber(1,limit/2);
            if (mode){
                toGet=limit/2;
            }
            lock.lock();
            while (productCounter-toGet<0){
                stackEmptyCondition.await();
            }
            productCounter-=toGet;
           // System.out.println("Pobrane: pobrałem: "+toGet +" zostalo: "+ productCounter);
            stackFullCondition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }


}
