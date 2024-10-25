package TP_2;

import javax.swing.*;
import java.awt.*;

class UnMobile extends JPanel implements Runnable {
	int saLargeur, saHauteur, sonDebDessin;
	final int sonPas = 10, sonTemps = 50, sonCote = 40;
	static SemaphoreGeneral sonSemaphore = new SemaphoreGeneral(1);

	UnMobile(int telleLargeur, int telleHauteur) {
		super();
		saLargeur = telleLargeur;
		saHauteur = telleHauteur;
		setSize(telleLargeur, telleHauteur);
	}

	public void run() {
		while (true) {
			for (int i = 0; i < 3; i++) {
				if (i == 1) {
					sonSemaphore.syncWait();
				}
				for (sonDebDessin = i * (saLargeur / 3); sonDebDessin < (i + 1) * (saLargeur / 3) - sonPas; sonDebDessin += sonPas) {
					repaint();
					try {
						Thread.sleep(sonTemps);
					} catch (InterruptedException telleExcp) {
						telleExcp.printStackTrace();
					}
				}
				if (i == 1) {
					sonSemaphore.syncSignal();
				}
			}

			for (int i = 0; i < 3; i++) {
				if (i == 1) {
					sonSemaphore.syncWait();
				}
				for (sonDebDessin = saLargeur - sonCote - i * (saLargeur / 3); sonDebDessin > saLargeur - sonCote - (i + 1) * (saLargeur / 3); sonDebDessin -= sonPas) {
					repaint();
					try {
						Thread.sleep(sonTemps);
					} catch (InterruptedException telleExcp) {
						telleExcp.printStackTrace();
					}
				}
				if (i == 1) {
					sonSemaphore.syncSignal();
				}
			}
		}
	}

	public void paintComponent(Graphics telCG) {
		super.paintComponent(telCG);
		telCG.fillRect(sonDebDessin, saHauteur / 2, sonCote, sonCote);
	}
}