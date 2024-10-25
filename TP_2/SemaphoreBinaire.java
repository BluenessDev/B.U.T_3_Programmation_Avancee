package TP_2;

public final class SemaphoreBinaire extends Semaphore {
    public SemaphoreBinaire(int valeurInitiale){
        super((valeurInitiale != 0) ? 1:0);
        //System.out.print(valeurInitiale);
    }
    public final synchronized void syncSignal(){
        super.syncSignal();
        //System.out.print(valeur);
        if (value>1) value = 1;
    }
}


