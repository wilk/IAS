/*
 * In questa finestra è possibile visualizzare ulteriori informazioni sul gioco in questione.
 * Ad esempio potrete sapere a quale tipo di versione è il gioco, chi sono i suoi creatori e tanto altro ancora.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Classe contenente informazioni extra sul gioco
 * @author Ferrari Vincenzo e Iadarola Barbara.
 */
public class JFAbout extends JFrame {
    
    /**
     * Costruttore che permette di creare la finestra About
     */
    public JFAbout() {
        
        // imposto la finestra About
        setTitle("About");
	setResizable(false);
        getContentPane().setLayout(null);
        
        // creo lo sfondo con un immagine
        jlSfondo = new JLabel(new ImageIcon("./Immagini/bgAbout.png"));
        jlSfondo.setBounds(XY_JLSFONDO,XY_JLSFONDO,LARALT_JLSFONDO,LARALT_JLSFONDO);
        
        // creo il bottone per chiudere la finestra
        jbChiudi = new JButton("Chiudi");
        jbChiudi.setBounds(X_JBCHIUDI,Y_JBCHIUDI,LAR_JBCHIUDI,ALT_JBCHIUDI);
        jbChiudi.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // quando viene cliccato, parte l'evento cliccato
                 jbChiudiCliccato(evt);
                 
             }
             
        });
        
        // inserisco il background e il bottone nella finestra
        getContentPane().add(jlSfondo);
        getContentPane().add(jbChiudi);
        
    }
    
    /**
     * Metodo che chiudi la finestra
     * @param evt
     */
    private void jbChiudiCliccato(ActionEvent evt) {
        
        setVisible(false);
        
    }
    
    private JButton jbChiudi; // bottone di chiusura della finestra
    private JLabel jlSfondo; // immagine di sfondo
    
    // dimensioni e coordinate immagine di sfondo
    private static final int XY_JLSFONDO = 0;
    private static final int LARALT_JLSFONDO = 400;
    
    // dimensioni e coordinate del bottone chiudi
    private static final int X_JBCHIUDI = 165;
    private static final int Y_JBCHIUDI = 420;
    private static final int LAR_JBCHIUDI = 80;
    private static final int ALT_JBCHIUDI = 20;

}
