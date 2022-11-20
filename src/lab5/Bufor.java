package lab5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor extends Thread{
    int productCounter=0;
    int limit;
    ReentrantLock lock = new ReentrantLock();
    ReentrantLock lockProducer = new ReentrantLock();
    ReentrantLock lockConsumer = new ReentrantLock();
    Condition condition = lock.newCondition();

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    Bufor(int _limit){
        limit=2*_limit;
    }

     void addProduct() {
        try{
            int toAdd=getRandomNumber(1,limit/2);
            lockProducer.lock();
            lock.lock();
            while(productCounter+toAdd>limit){
                //System.out.println("Czekam sam: " + getId()); hasWaiters zakleszczanie

                condition.await();
            }
            productCounter+=toAdd;
            //System.out.println("Produkuje: " + getId()); hasWaiters zakleszczanie
            // System.out.println("Wyprodukowane: dalem " +toAdd+ " jest: " + productCounter);
            condition.signal();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
            lockProducer.unlock();
        }
    }

     void getProduct(boolean mode){
        try{
            int toGet=getRandomNumber(1,limit/2);
            if (mode){
                toGet=limit/2;
            }
            lockConsumer.lock();
            lock.lock();
            while (productCounter-toGet<0){
                //System.out.println("Czekam sam (Consumer): " + getId()); hasWaiters zakleszczanie

                condition.await();
            }
            productCounter-=toGet;
            //System.out.println("Pobieram (Consumer): " + getId()); hasWaiters zakleszczanie
            // System.out.println("Pobrane: pobrałem: "+toGet +" zostalo: "+ productCounter);
            condition.signal();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lockConsumer.unlock();
            lock.unlock();
        }
    }


}
