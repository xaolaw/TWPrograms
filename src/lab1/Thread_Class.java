package lab1;

public class Thread_Class extends Thread{

    Counter c;
    int choice;

    Thread_Class(Counter c_,int choice_){
        this.c=c_;
        this.choice=choice_;
    }

    public void run(){
        switch (choice) {
            case 1 -> c.increase();
            case -1 -> c.decrease();
        }
    }
}
