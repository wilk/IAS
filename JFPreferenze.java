/**
 * JFPreferenze è la finestra delle impostazioni del velivolo, della raffica dei proiettili
 * e del giocatore.
 * Tramite essa è possibile impostare quanti colpi sparare, l'intervallo di velocità e di angolatura tra un
 * colpo e l'altro, la forma del velivolo e il nome e cognome del giocatore
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.NoSuchElementException;

import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * Classe delle preferenze di gioco.
 * @author Ferrari Vincenzo e Iadarola Barbara.
 */
public class JFPreferenze extends JFrame {
    
    /**
     * Costruttore che crea la finestra di gioco
     */
    public JFPreferenze() throws NoSuchElementException, FileNotFoundException, IOException{
         
         // imposto il frame Preferenze
	 setTitle("Preferenze");
	 setResizable(false);
         getContentPane().setLayout(new GridLayout(6,1));
         setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
         
         // inizializzazione delle variabili di partenza delle opzioni
         sliderColpi = SLIDERCOLPI;
         sliderAngolatura = SLIDERANGOLATURA;
         sliderVelocita = SLIDERVELOCITA;
         textNome = TEXTNOME;
         textCognome = TEXTCOGNOME;
         formaVelivolo = SIMBOLICA;
         
         // creo il corpo della finestra
         creaSlider();
         creaPannelli();
         
         // creo la finestra di gioco, non agibile per il momento
         jfGioco = new JFGioco(this);
         jfGioco.setBounds(X_JFGIOCO, Y_JFGIOCO, LARGHEZZA_JFGIOCO, ALTEZZA_JFGIOCO);
         jfGioco.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
         jfGioco.setVisible(true);
         jfGioco.setEnabled(false);
		
    }
    
    /**
     * Metodo che reimposta tutte le variabili al valore iniziale
     */
    public void azzera() {
        
        // re-inizializzazione delle variabili
        iniziaVariabili();
        textNome = TEXTNOME;
        textCognome = TEXTCOGNOME;
        
        // re-impostazione dei valori grafici delle slider e delle textbox
        iniziaOggetti();
        jrbSimbolica.setSelected(true);
        jtNome.setText(textNome);
        jtCognome.setText(textCognome);
        jtNome.setEditable(true);
        jtCognome.setEditable(true);
        
    }
     
    /**
    * Metodo che crea le slider per:
    * raffica di colpi
    * velocità di intervallo delle raffiche
    * angolatura delle raffiche
    */
    private void creaSlider() {

         // creo lo slider della raffica dei colpi
         jsColpi = new JSlider(VAL_MIN_COLPI,VAL_MAX_COLPI,sliderColpi);     
         jsColpi.setMajorTickSpacing(1); // spaziatura di 1 fra un valore e l'altro
         jsColpi.setPaintLabels(true); // valori disegnati
         jsColpi.setPaintTicks(true); // paletti disegnati
         jsColpi.setSnapToTicks(true); // passa automaticamente da un valore all'altro, senza intermezzi
         jsColpi.setBorder(BorderFactory.createTitledBorder("Raffiche di Colpi"));
         jsColpi.setName("Colpi");

         // creo lo slider dell'intervallo della velocità tra le raffiche dei colpi
         jsVelocita = new JSlider(VAL_MIN_VELOCITA,VAL_MAX_VELOCITA,sliderVelocita);
         jsVelocita.setMajorTickSpacing(1);
         jsVelocita.setPaintLabels(true);
         jsVelocita.setPaintTicks(true);
         jsVelocita.setSnapToTicks(true);
         jsVelocita.setBorder(BorderFactory.createTitledBorder("Intervallo Velocità Raffiche ( in % )"));
         jsVelocita.setName("Velocita");
        
         // creo lo slider dell'angolatura delle raffiche
         jsAngolatura = new JSlider(VAL_MIN_ANGOLATURA,VAL_MAX_ANGOLATURA,sliderAngolatura);
         jsAngolatura.setMajorTickSpacing(1);
         jsAngolatura.setPaintLabels(true);
         jsAngolatura.setPaintTicks(true);
         jsAngolatura.setSnapToTicks(true);
         jsAngolatura.setBorder(BorderFactory.createTitledBorder("Angolatura delle Raffiche ( in decimi di gradi )"));
         jsAngolatura.setName("Angolatura");
         
         // inserisco i vari slider nel corpo del frame
         getContentPane().add(jsColpi);
         getContentPane().add(jsVelocita);
         getContentPane().add(jsAngolatura);
         
     }
    
     
     /**
     * Metodo che crea i pannelli per il gioco e per i comandi
     * I pannelli creati sono: jpForma, jpGiocatore, jpBottoni
     * jpForma : pannello per la forma del velivolo
     * jpGiocatore : pannello per le informazioni sul giocatore 
     * jpBottoni : pannello contenente i bottoni del menu Preferenze
     */
     private void creaPannelli() {
                           
         // imposto il pannello per la forma del velivolo
         jpForma = new JPanel(null);
         jpForma.setBorder(BorderFactory.createTitledBorder("Forma del Velivolo"));
         jpForma.setName("Forma del Velivolo");
         
         // creo il radio bottone per la forma simbolica
         jrbSimbolica = new JRadioButton(SIMBOLICA);
         jrbSimbolica.setName(SIMBOLICA);
         jrbSimbolica.setSelected(true);
         jrbSimbolica.setBounds(X_JRBSIMBOLICA,Y_RADIOBOTTONI,LUNGHEZZA_RADIOBOTTONI,ALTEZZA_RADIOBOTTONI);
         jrbSimbolica.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il radio bottone, parte l'evento cliccato
                 jrbFormaCliccato(evt);
                 
             }
             
         });
        
         // creo il radio bottone per la forma statica
         jrbStatica = new JRadioButton(STATICA);
         jrbStatica.setName(STATICA);
         jrbStatica.setBounds(X_JRBSTATICA,Y_RADIOBOTTONI,LUNGHEZZA_RADIOBOTTONI,ALTEZZA_RADIOBOTTONI);
         jrbStatica.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il radio bottone, parte l'evento cliccato
                 jrbFormaCliccato(evt);
                 
             }
             
         });
        
         // creo il radio bottone per la forma dinamica
         jrbDinamica = new JRadioButton(DINAMICA);
         jrbDinamica.setName(DINAMICA);
         jrbDinamica.setBounds(X_JRBDINAMICA,Y_RADIOBOTTONI,LUNGHEZZA_RADIOBOTTONI,ALTEZZA_RADIOBOTTONI);
         jrbDinamica.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il radio bottone, parte l'evento cliccato
                 jrbFormaCliccato(evt);
                 
             }
             
         });
         
         // creo il gruppo per i radio bottoni e li inserisco al suo interno
         // in questo modo posso abbinarli, eliminando la scelta multipla
         bgBottoni = new ButtonGroup();
         bgBottoni.add(jrbSimbolica);
         bgBottoni.add(jrbStatica);
         bgBottoni.add(jrbDinamica);
                
         // inserisco i bottoni all'interno del pannello della Forma
         jpForma.add(jrbSimbolica);
         jpForma.add(jrbStatica);
         jpForma.add(jrbDinamica);
        
         // creo il pannello per le caratteristiche del giocatore
         jpGiocatore = new JPanel(null);
         jpGiocatore.setBorder(BorderFactory.createTitledBorder("Giocatore"));
         jpGiocatore.setName("Giocatore");
        
         // creo l'etichetta del nome del giocatore
         jlNome = new JLabel("Nome :");
         jlNome.setName("Nome");
         jlNome.setBounds(X_LNOME,Y_NOME,LUNGHEZZA_ETICHETTE,ALTEZZA_TESTO);
        
         // creo l'etichetta del cognome del giocatore
         jlCognome = new JLabel("Cognome :");
         jlCognome.setName("Cognome");
         jlCognome.setBounds(X_LCOGNOME,Y_COGNOME,LUNGHEZZA_ETICHETTE,ALTEZZA_TESTO);
        
         // creo il textbox per immettere il nome del giocatore
         jtNome = new JTextField(textNome);
         jtNome.setName("Nome");
         jtNome.setBounds(X_TNOME,Y_NOME,LUNGHEZZA_TESTO,ALTEZZA_TESTO);
        
         // creo il textbox per immettere il cognome del giocatore
         jtCognome = new JTextField(textCognome);
         jtCognome.setName("Cognome");
         jtCognome.setBounds(X_TCOGNOME,Y_COGNOME,LUNGHEZZA_TESTO,ALTEZZA_TESTO);
               
         // inserisco etichette e textbox nel pannello del giocatore
         jpGiocatore.add(jlNome);
         jpGiocatore.add(jtNome);
         jpGiocatore.add(jlCognome);
         jpGiocatore.add(jtCognome);
         
         // creo il pannello dei bottoni
         jpBottoni = new JPanel(null);
         jpBottoni.setName("Bottoni");
         
         // creo il bottone per applicare le modifiche apportate
         jbApplica = new JButton("Applica");
         jbApplica.setName("Applica");
         jbApplica.setBounds(X_JBAPPLICA,Y_BOTTONI,LUNGHEZZA_BOTTONI,ALTEZZA_BOTTONI);
         jbApplica.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il bottone, parte l'evento cliccato
                 jbApplicaCliccato(evt);
                 
             }
             
         });
        
         // creo il bottone per chiudere la finestra
         jbChiudi = new JButton("Chiudi");
         jbChiudi.setName("Chiudi");
         jbChiudi.setBounds(X_JBCHIUDI,Y_BOTTONI,LUNGHEZZA_BOTTONI,ALTEZZA_BOTTONI);
         jbChiudi.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il bottone, parte l'evento cliccato
                 jbChiudiCliccato(evt);
                 
             }
             
         });
        
         // creo il bottone per reimpostare tutte le variabili e gli oggetti dei pannelli al valore iniziale
         jbReset = new JButton("Reset");
         jbReset.setName("Reset");
         jbReset.setBounds(X_JBRESET,Y_BOTTONI,LUNGHEZZA_BOTTONI,ALTEZZA_BOTTONI);
         jbReset.addActionListener(new ActionListener() {
             
             public void actionPerformed(ActionEvent evt) {
                 
                 // una volta cliccato il bottone, parte l'evento cliccato
                 jbResetCliccato(evt);
                 
             }
             
         });
         
         // inserisco i bottoni all'interno del pannello dei bottoni
         jpBottoni.add(jbReset);
         jpBottoni.add(jbApplica);
         jpBottoni.add(jbChiudi);
         
         // inserisco i 3 pannelli nella finestra delle Preferenze
         getContentPane().add(jpForma);
         getContentPane().add(jpGiocatore);
         getContentPane().add(jpBottoni);
            
    }
     
    /**
     * Metodo che permette di scegliere la forma simbolica, come forma del velivolo
     * @param evt : quando il radio-button viene selezionato, viene impostata la selezione
     */
    private void jrbFormaCliccato(ActionEvent evt){
        
        // in formaVelivolo inserisco la scelta fatta nei radio bottoni
        formaVelivolo = evt.getActionCommand();
        
        // aggiorno l'immagine nel moto del velivolo nella finestra di Gioco
        jfGioco.jpGiocoUpdate(formaVelivolo);
        
    }
    
    /**
    * Metodo che permette di salvare i valori delle opzioni
    * @param evt : quando il bottone viene cliccato, vengono salvati i valori delle opzioni
    */
    private void jbApplicaCliccato(ActionEvent evt){
        
         sliderColpi = jsColpi.getValue();
         sliderAngolatura = jsAngolatura.getValue();
         sliderVelocita = jsVelocita.getValue();
             
         }
     
    
    /**
    * Metodo che permette di chiudere il frame Preferenze
    * @param evt : quando il bottone viene cliccato il frame viene chiuso
    */
    private void jbChiudiCliccato(ActionEvent evt){
        
        // controllo che le textbox del nome e del cognome non siano vuote
        if ((jtNome.getText().isEmpty()) || (jtCognome.getText().isEmpty()))
            
             JOptionPane.showMessageDialog(null, "Inserisci il tuo nome e cognome!", "Attenzione", JOptionPane.ERROR_MESSAGE);
        
        else
            
        {
            
            textNome = jtNome.getText();
            textCognome = jtCognome.getText();
            jtNome.setEditable(false); // il nome e il cognome non possono essere più editati
            jtCognome.setEditable(false); // finché non si inizia una nuova partita
            iniziaOggetti(); // aggiorno gli oggetti con i valori salvati
            jtNome.setText(textNome);
            jtCognome.setText(textCognome);
            setVisible(false);
            jfGioco.setEnabled(true); // rendo agibile la finestra di gioco
                        
        }
        
    }
 
    
    /**
    * Metodo che permette di re-impostare i campi al valore iniziale
    * @param evt : quando il bottone viene cliccato i vari campi vengono
    * reimpostati al valore iniziale
    */
    public void jbResetCliccato(ActionEvent evt){
        
        // aggiorno gli oggetti e le variabili ai valori iniziali
        iniziaVariabili();        
        iniziaOggetti();
        
        // aggiorno la forma iniziale del velivolo
        jrbSimbolica.setSelected(true);
        jfGioco.jpGiocoUpdate(formaVelivolo);
        
    }
    
    /**
     * Metodo che consente di inizializzare gli oggetti all'interno dei vari pannelli ai valori salvati
     */
    private void iniziaOggetti(){
        
        jsColpi.setValue(sliderColpi);
        jsAngolatura.setValue(sliderAngolatura);
        jsVelocita.setValue(sliderVelocita);
        
    }
    
    /**
     * Metodo che inizializza le variabili al loro valore originale
     */    
    private void iniziaVariabili(){
        
        sliderColpi = SLIDERCOLPI;
        sliderAngolatura = SLIDERANGOLATURA;
        sliderVelocita = SLIDERVELOCITA;
        formaVelivolo = SIMBOLICA;
        
    }
          
    private ButtonGroup bgBottoni; // gruppo radio bottoni
    private JButton jbApplica; // bottone Applica
    private JButton jbChiudi; // bottone Chiudi
    private JButton jbReset; // bottone Reset
    private JLabel jlCognome; // etichetta del cognome
    private JLabel jlNome; // etichetta del nome
    private JPanel jpForma; // pannello della forma del velivolo
    private JPanel jpGiocatore; // pannello delle impostazioni del giocatore
    private JPanel jpBottoni; // pannello dei bottoni
    private JSlider jsAngolatura; // slider dell'angolatura dei colpi
    private JSlider jsColpi; // slider dei colpi
    private JSlider jsVelocita; // slider della velocità dei colpi
    private JTextField jtCognome; // textbox del cognome
    private JTextField jtNome; // textbox del nome
    
    public JFGioco jfGioco; // finestra di gioco
    public JRadioButton jrbSimbolica; // radio bottone per la forma simbolica del velivolo
    public JRadioButton jrbStatica; // radio bottone per la forma statica del velivolo
    public JRadioButton jrbDinamica; // radio bottone per la forma dinamica del velivolo
    
    private String formaVelivolo; // variabile che viene aggiornata quando si cambia la forma del velivolo
    
    public String textCognome; // variabile per il cognome del giocatore
    public String textNome; // variabile per il nome del giocatore
    
    public int sliderColpi; // valore attuale dello slider dei colpi
    public int sliderAngolatura; // valore attuale dello slider dell'angolatura dei colpi
    public int sliderVelocita; // valore attuale dello slider della velocità dei colpi
    
    // valori massimi e minimi delle slider dei colpi, dell'angolatura e della velocità
    private static final int VAL_MIN_COLPI = 1;
    private static final int VAL_MAX_COLPI = 4;
    private static final int VAL_MIN_VELOCITA = -10;
    private static final int VAL_MAX_VELOCITA = 10; 
    private static final int VAL_MIN_ANGOLATURA = -5;
    private static final int VAL_MAX_ANGOLATURA = 5;
    
    // i bottoni hanno tutti altezza, lunghezza e coordinata y uguale
    private static final int ALTEZZA_BOTTONI = 25;
    private static final int LUNGHEZZA_BOTTONI = 120;
    private static final int Y_BOTTONI = 40;
   
    private static final int X_JBRESET = 5;    
    private static final int X_JBAPPLICA = 340;    
    private static final int X_JBCHIUDI = 470;
    
    // i radiobottoni hanno tutti altezza, lunghezza e coordinata y uguale
    private static final int ALTEZZA_RADIOBOTTONI = 40;
    private static final int LUNGHEZZA_RADIOBOTTONI = 160;
    private static final int Y_RADIOBOTTONI = 20;
    
    private static final int X_JRBSIMBOLICA = 10;
    private static final int X_JRBSTATICA = 260;
    private static final int X_JRBDINAMICA = 430;
    
    // i campi di testo hanno tutti l'altezza e la lunghezza uguale
    // le etichette hanno tutte la stessa lunghezza
    private static final int ALTEZZA_TESTO = 20;
    private static final int LUNGHEZZA_TESTO = 140;
    private static final int LUNGHEZZA_ETICHETTE = 100;
    private static final int Y_NOME = 28;
    private static final int Y_COGNOME = 30;
    
    private static final int X_LNOME = 30;
    private static final int X_TNOME = 80;
    private static final int X_LCOGNOME = 300;
    private static final int X_TCOGNOME = 375;
    
    // valori iniziali delle slider e dei textbox
    private static final int SLIDERCOLPI = 1;
    private static final int SLIDERANGOLATURA = 0;
    private static final int SLIDERVELOCITA = 0;
    private static final String TEXTNOME = "";
    private static final String TEXTCOGNOME = "";
    
    // valori iniziali delle forme del velivolo
    private static final String SIMBOLICA = "Simbolica";
    private static final String STATICA = "Statica";
    private static final String DINAMICA = "Dinamica";
    
    // dimensioni e coordinate della finestra di gioco
    private static final int ALTEZZA_JFGIOCO = 600;
    private static final int LARGHEZZA_JFGIOCO = 800;
    private static final int X_JFGIOCO = 100;
    private static final int Y_JFGIOCO = 50;
    
            
}