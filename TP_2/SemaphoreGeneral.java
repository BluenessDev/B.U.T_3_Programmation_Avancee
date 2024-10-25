package TP_2;

public final class SemaphoreGeneral extends Semaphore {
    public SemaphoreGeneral(int valeurInitiale){
        super(valeurInitiale);
    }
    public final synchronized void syncSignal(){
        super.syncSignal();
        if (value>1) value = 1;
    }
}
