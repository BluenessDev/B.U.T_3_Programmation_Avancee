import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame 
{
    UnMobile sonMobile;
    private final int LARG=400, HAUT=250;
    
    public UneFenetre() {
	    super("Mobile");
        Container telConteneur = getContentPane();
        sonMobile = new UnMobile(LARG, HAUT);
        JButton sonBouton = new JButton("Start/Stop");
        telConteneur.add(sonBouton, BorderLayout.SOUTH);
        telConteneur.add(sonMobile);
        Thread telThread = new Thread(sonMobile);
        telThread.start();
        setSize(LARG, HAUT);
        setVisible(true);
    }
}
