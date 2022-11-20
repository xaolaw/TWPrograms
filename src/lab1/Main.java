package lab1;

public class Main {
    public static void main(String[] args) {
        Counter c= new Counter();

        int numberThreads=1000000;
        Thread_Class[] threads_plus=new Thread_Class[numberThreads];
        Thread_Class[] threads_minus=new Thread_Class[numberThreads];

        for (int i=0;i<numberThreads;i++){
            threads_plus[i]=new Thread_Class(c,1);
            threads_minus[i]=new Thread_Class(c,-1);
        }
        for (int i=0;i<numberThreads/2;i++){
            threads_plus[i].start();
        }
        for (int i=0;i<numberThreads/2;i++){
            threads_minus[i].start();
        }
        try{
            for (int i=0;i<numberThreads/2;i++){
                threads_plus[i].join();
            }
            for (int i=0;i<numberThreads/2;i++){
                threads_minus[i].join();
            }
            c.print();
            }catch (Exception e){
                System.out.println("Error in threads");
            }
        }

}
