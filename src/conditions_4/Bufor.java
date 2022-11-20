package conditions_4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Bufor extends Thread{
    int productCounter=0;
    int limit;
    ReentrantLock lock = new ReentrantLock();
    Condition firstConsumer = lock.newCondition();
    Condition firstProducer = lock.newCondition();
    Condition restConsumer = lock.newCondition();
    Condition restProducer = lock.newCondition();
    //if true then there is a consumer waiting
    boolean consumer=false;
    //if true then there is a producer waiting
    boolean producer=false;

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
            while (producer){
                restProducer.await();
            }
            /*
            while(lock.hasWaiters(firstProducer)){
                //System.out.println("Czekam z resztą: " + getId()); hasWaiters zakleszczanie
                restProducer.await();
            }*/
            while(productCounter+toAdd>limit){
                //System.out.println("Czekam sam: " + getId()); hasWaiters zakleszczanie
                producer=true;
                firstProducer.await();
            }

            producer=false;
            productCounter+=toAdd;
            //System.out.println("Produkuje: " + getId()); hasWaiters zakleszczanie
           // System.out.println("Wyprodukowane: dalem " +toAdd+ " jest: " + productCounter);
            restProducer.signal();
            firstConsumer.signal();
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

            while (consumer){
                //System.out.println("Czekam z resztą (Consumer): " + getId()); hasWaiters zakleszczanie
                restConsumer.await();
            }
            /*
            while(lock.hasWaiters(firstConsumer)){
                restConsumer.await();
            }*/
            while (productCounter-toGet<0){
                //System.out.println("Czekam sam (Consumer): " + getId()); hasWaiters zakleszczanie
                consumer=true;
                firstConsumer.await();
            }
            consumer=false;
            productCounter-=toGet;
            //System.out.println("Pobieram (Consumer): " + getId()); hasWaiters zakleszczanie
           // System.out.println("Pobrane: pobrałem: "+toGet +" zostalo: "+ productCounter);
            restConsumer.signal();
            firstProducer.signal();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }


}
