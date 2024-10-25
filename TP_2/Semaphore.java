package TP_2;

public abstract class Semaphore {

    protected int value=0;

    protected Semaphore(int valeurInitiale) {
        value = valeurInitiale > 0 ? valeurInitiale : 0;
    }

    public synchronized void syncWait() {
        try {
            while (value == 0) {
                wait();
            }
            value--;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void syncSignal() {
        if(++value > 0) {
            notifyAll();
        }
    }
}
