/**
 * Italian Air Strike è un gioco progettato da Ferrari Vincenzo, Iadarola Barbara e Cavatorti Mattia.
 * Consiste nell'abbattimento di un velivolo, di forma variabile, con l'ausilio di un cannone.
 * E' possibile impostare l'angolatura, la velocità e i vari intervalli per il lancio dei proiettili.
 * Si possono visualizzare i punteggi effettuati nel corso della partita, stilati in una top-ten raggiungibile
 * tramite un menu a tendina.
 */

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.NoSuchElementException;

import javax.swing.JFrame;

/**
 * Classe che avvia il gioco.
 * @author Ferrari Vincenzo e Iadarola Barbara
 */
public class ItalianAirStrike {

	/**
	 * Metodo main per l'avvio del gioco
         * Viene richiamato il frame JFPreferenze, col quale è possibile impostare le varie opzioni
	 */
	public static void main(String[] args) throws NoSuchElementException, FileNotFoundException, IOException {
            
            // creo il frame delle preferenze con cui verranno impostate le varie opzioni di gioco
            JFrame jfPreferenze = new JFPreferenze();
            jfPreferenze.setBounds(X_JFPREFERENZE, Y_JFPREFERENZE, LARGHEZZA_JFPREFERENZE, ALTEZZA_JFPREFERENZE);
	    jfPreferenze.setVisible(true);
            jfPreferenze.setFocusableWindowState(true);

	}
        
        //private JFGioco jfGioco; (da togliere)
        
        // dimensioni e coordinate del frame JFPreferenze
        private static final int ALTEZZA_JFPREFERENZE = 450;
        private static final int LARGHEZZA_JFPREFERENZE = 600;
        private static final int X_JFPREFERENZE = 200;
        private static final int Y_JFPREFERENZE = 100;
        
}
