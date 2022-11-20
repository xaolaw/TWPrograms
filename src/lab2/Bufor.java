package lab2;

public class Bufor extends Thread{

    int products;
    int maxProducts;
    Bufor(int maxProducts){
        this.products=0;
        this.maxProducts=maxProducts;
    }
    synchronized void produce() {
        if(products<maxProducts){
            products+=1;
            System.out.println("Wyprodukowano, ilość pozostałych produktów: "+products+" /Numer wątka "+Thread.currentThread().getId());
            notifyAll();
        }
        if (products==maxProducts){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
    synchronized void buy() {
        if(products>0){
            products-=1;
            System.out.println("Zakupiono, ilość pozostałych produktów: "+products+" /Numer wątka "+Thread.currentThread().getId());
            notifyAll();
        }
        if (products==0){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
