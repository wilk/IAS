/**
 * Finestra di gioco.
 * In questa finestra vengono richiamati le animazioni del moto del velivolo e del moto del proiettile.
 * E' costituito da due pannelli: uno in cui avviene il gioco vero e proprio, l'altro nel quale è possibile
 * impostare l'angolatura e la velocità del proiettile, potendo sparare con un apposito bottone.
 * Vi è una barra dei menu da cui è possibile uscire dal gioco, iniziare una nuova partita, cambiare la forma
 * del velivolo, guarda la top-ten dei risultati e entrare nella finestra delle Preferenze.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Toolkit;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;

/**
 * Classe delle animazioni del moto del proiettile e del moto del velivolo.
 * @author Ferrari Vincenzo e Iadarola Barbara.
 */
public class JFGioco extends JFrame {

/**
 * Costruttore che crea la finestra di gioco
 * @param jfP finestra delle Preferenze
 */
	public JFGioco(JFPreferenze jfP) throws NoSuchElementException, FileNotFoundException, IOException {
		
            // imposto la finestra di Gioco
            setTitle("Italian Air Strike - Versione 1.1.0");
            setResizable(false);
                
            // creo la barra dei menu
            setJMenuBar(creaMenu());
            
            // creo i pannelli
            creaPannelli();
                
            // inizializzo le variabili di gioco
            punteggio = 0;
            jfPreferenze = jfP;
            //elenco = new Giocatore[10];
            //inizializzo l'array di oggetti Giocatore
            //for (int i=0; i<10; i++){
            //elenco[i]=new Giocatore();
            //}
                
            // creo la classe Punteggi
            jcRisultati = new Punteggi("./Risultati.txt");
                
            // creo la finestra dei Risultati
            jfRisultati = new JFRisultati(jcRisultati);
            jfRisultati.setBounds(X_JFRISULTATI, Y_JFRISULTATI, LARGHEZZA_JFRISULTATI, ALTEZZA_JFRISULTATI);
            jfRisultati.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                
            // creo la finestra About
            jfAbout = new JFAbout();
            jfAbout.setBounds(300,100,400,480);
            jfAbout.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                
	}
	
	/**
	 * Metodo che crea la Barra dei Menu e i suoi Menu
	 * Come menu, crea: Gioco, Opzioni e Forma Velivolo
         * @return jmbMenu : restituisce il la Barra dei Menu
	 */
	private JMenuBar creaMenu(){
		
            // imposto la barra dei menu
            jmbMenu = new JMenuBar();
            jmbMenu.setName("Barra dei Menu");
            jmbMenu.setBorder(BorderFactory.createTitledBorder(""));
		
            // creo il menu di gioco
            jmGioco = new JMenu("Gioco");
            jmGioco.setName("Gioco");
                
            // creo il sottomenu Nuova Partita, per cominciare una nuova partita
            jmiNuovaPartita = new JMenuItem("Nuova Partita");
            jmiNuovaPartita.setName("Nuova Partita");
            jmiNuovaPartita.addActionListener(new ActionListener(){ // creo l'ascoltatore sul item menu
                    
                public void actionPerformed(ActionEvent evt) { // gestisco l'evento del menu item
                        
                    try {
                        
                        // una volta cliccato il sottomenu, parte l'evento cliccato
                        jmiNuovaPartitaCliccato(evt);
                        
                        } 
                
                    catch (IOException ex) { // se vi è un errore, segnala in console il suo log
                        
                        Logger.getLogger(JFGioco.class.getName()).log(Level.SEVERE, null, ex);
                            
                    }
                        
                }
                    
            });
                
            // creo il sottomenu Esci, per uscire dal gioco
            jmiEsci = new JMenuItem("Esci");
            jmiEsci.setName("Esci");
            jmiEsci.addActionListener(new ActionListener() {
                   
                public void actionPerformed(ActionEvent evt) {
                        
                    try {
                        // una volta cliccato il sottomenu, parte l'evento cliccato
                        jmiEsciCliccato(evt);
                            
                    } 
                        
                    catch (IOException ex) { // se vi è un errore, segnala in consolo il suo log
                            
                        Logger.getLogger(JFGioco.class.getName()).log(Level.SEVERE, null, ex);
                            
                    }
                        
                }
                
            });
                
            // inserisco nel menu Gioco, i sottomenu NuovaPartita ed Esci
            jmGioco.add(jmiNuovaPartita);
            jmGioco.add(jmiEsci);
                
            // creo il menu Forma del Velivolo, da cui è possibile cambiare la forma del velivolo
            jmForma = new JMenu("Forma Velivolo");
            jmForma.setName("Forma Velivolo");
                
            // creo il sottomenu radio bottone Simbolica
            jrbmiSimbolica = new JRadioButtonMenuItem("Simbolica");
            jrbmiSimbolica.setName("Simbolica");
            jrbmiSimbolica.setSelected(true);
            jrbmiSimbolica.addActionListener(new ActionListener() {
                    
                public void actionPerformed(ActionEvent evt) {
                 
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jrbmiFormaCliccato(evt);
                 
                }
             
            });
             
            // creo il sottomenu radio bottone Statica
            jrbmiStatica = new JRadioButtonMenuItem("Statica");
            jrbmiStatica.setName("Statica");
            jrbmiStatica.addActionListener(new ActionListener() {
                    
                public void actionPerformed(ActionEvent evt) {
                 
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jrbmiFormaCliccato(evt);
                 
                }
             
            });
             
            // creo il sottomenu radio bottone Dinamica
            jrbmiDinamica = new JRadioButtonMenuItem("Dinamica");
            jrbmiDinamica.setName("Dinamica");
            jrbmiDinamica.addActionListener(new ActionListener() {
                  
                public void actionPerformed(ActionEvent evt) {
                 
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jrbmiFormaCliccato(evt);
                 
                }
             
            });
                
            // creo il gruppo dei radio bottoni per escludere la scelta multipla                
            bgForma = new ButtonGroup(); 
            
            // li inserisco al suo interno
            bgForma.add(jrbmiSimbolica);
            bgForma.add(jrbmiStatica);
            bgForma.add(jrbmiDinamica);
                
            // inserisco i sottomenu radio bottoni nel menu Forma
            jmForma.add(jrbmiSimbolica);
            jmForma.add(jrbmiStatica);
            jmForma.add(jrbmiDinamica);
	
            // creo il menu Opzioni, per visualizzare i risultati e per impostare le opzioni di gioco
            jmOpzioni = new JMenu("Opzioni");
            jmOpzioni.setName("Opzioni");
		
            // creo il sottomenu Risultati, per visualizzare la top ten dei migliori giocatori
            jmiRisultati = new JMenuItem("Risultati");
            jmiRisultati.setName("Risultati");
            jmiRisultati.addActionListener(new ActionListener() {
                    
                public void actionPerformed(ActionEvent evt) {
                    
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jmiRisultatiCliccato(evt);
                        
                }
                    
            });
		
            // creo il sottomenu Preferenze, da cui è possibile impostare le opzioni di gioco
            jmiPreferenze = new JMenuItem("Preferenze");
            jmiPreferenze.setName("Preferenze");
            jmiPreferenze.addActionListener(new ActionListener() {
                   
                public void actionPerformed(ActionEvent evt) {
                
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jmiPreferenzeCliccato(evt);
                        
                }
                
            });
                
            // inserisco i sottomenu Risultati e Preferenze nel menu Opzioni
            jmOpzioni.add(jmiRisultati);
            jmOpzioni.add(jmiPreferenze);
                
            // creo il menu Aiuto, per visualizzare chi ha creato il gioco
            jmAiuto = new JMenu("Aiuto");
            jmAiuto.setName("Aiuto");
               
            // creo il sottomenu About, dove sono contenute informazioni inerenti al gioco
            jmiAbout = new JMenuItem("About");
            jmiAbout.setName("About");
            jmiAbout.addActionListener(new ActionListener() {
                   
                public void actionPerformed(ActionEvent evt) {
                
                    // una volta cliccato il sottomenu, parte l'evento cliccato
                    jmiAboutCliccato(evt);
                        
                }
                
            });
                
            // inserisco nel menu Aiuto, il sottomenu About
            jmAiuto.add(jmiAbout);
		
            // inserimento dei menu nella barra dei menu
            jmbMenu.add(jmGioco);
            jmbMenu.add(jmForma);
            jmbMenu.add(jmOpzioni);
            jmbMenu.add(jmAiuto);
                
            // ritorno la barra dei menu
            return jmbMenu;
		
	}
        
        /**
         * Metodo che crea i pannelli per il gioco e per i comandi
         */
        private void creaPannelli(){
            
            // imposto un layout nullo
            getContentPane().setLayout(null);
                        
            // creo il pannello dei comandi
            jpComandi = new JPanel(null);
            jpComandi.setBorder(BorderFactory.createTitledBorder("Comandi"));
            jpComandi.setName("Comandi");
            jpComandi.setBounds(X_JPCOMANDI,Y_JPCOMANDI,LARGHEZZA_JPCOMANDI,ALTEZZA_JPCOMANDI);
            
            // creo la textbox per l'inserimento della velocità del proiettile
            jtVelocita = new JTextField("100");
            jtVelocita.setBorder(BorderFactory.createTitledBorder(null,"Velocità del Proiettile", TitledBorder.CENTER, TitledBorder.TOP));
            jtVelocita.setName("Velocita");
            jtVelocita.setBounds(X_TESTO,Y_JTVELOCITA,LARGHEZZA_TESTO,ALTEZZA_TESTO);
            
            // creo la textbox per l'inserimento dell'angolatura del proiettile
            jtAngolatura = new JTextField("45");
            jtAngolatura.setBorder(BorderFactory.createTitledBorder(null,"Angolatura del Cannone", TitledBorder.CENTER, TitledBorder.TOP));
            jtAngolatura.setName("Angolatura");
            jtAngolatura.setBounds(X_TESTO,Y_JTANGOLATURA,LARGHEZZA_TESTO,ALTEZZA_TESTO);
            
            // creo il bottone Spara, per poter fare fuoco col cannone
            jbSpara = new JButton("Fuoco!");
            jbSpara.setBounds(X_JBSPARA,Y_JBSPARA,LARGHEZZA_JBSPARA,ALTEZZA_JBSPARA);
            jbSpara.addActionListener(new ActionListener() {
                   
                    public void actionPerformed(ActionEvent evt) {
                        
                        // una volta cliccato il bottone, parte l'evento cliccato
                        jbSparaCliccato(evt);
                        
                    }
                
            });
            
            // inserisco i vari oggetti nel pannello dei Comandi
            jpComandi.add(jtVelocita);
            jpComandi.add(jtAngolatura);
            jpComandi.add(jbSpara);
            
            // creo il pannello di Gioco
            jpGioco = new JPGioco(LAR_VELIVOLO, ALT_VELIVOLO, MS_VELIVOLO, RAGGIO_PROIETTILE, MS_PROIETTILE);
            jpGioco.setName("Gioco");
            jpGioco.setBounds(X_JPGIOCO,Y_JPGIOCO,LARGHEZZA_JPGIOCO,ALTEZZA_JPGIOCO);
            jpGioco.setOpaque(false);
            
            // creo l'immagine di sfondo
            jlSfondo = new JLabel(new ImageIcon("./Immagini/bg.gif"));
            jlSfondo.setBounds(X_JPGIOCO,Y_JPGIOCO,LARGHEZZA_JPGIOCO,ALTEZZA_JPGIOCO);
            
            // creo l'immagine del cannone
            jlCannone = new JLabel(new ImageIcon("./Immagini/cannone.gif"));
            jlCannone.setBounds(X_CANNONE,Y_CANNONE,LAR_CANNONE,ALT_CANNONE);
            
            // inserisco i vari pannelli e i vari oggetti nella finestra di Gioco
            getContentPane().add(jlCannone);
            getContentPane().add(jpGioco);
            getContentPane().add(jpComandi);
            getContentPane().add(jlSfondo);
            
        }
        
        /**
         * Metodo che consente di visualizzare i risultati in un'apposita finestra
         * @param evt : evento del menu Risultati
         */
        private void jmiRisultatiCliccato(ActionEvent evt){
            
            // aggiorno i risultati nel file Risultati prima di renderlo visibile
            // in maniera tale da poter aggiornare la tabella nella finestra dei Risultati
            jfRisultati.updateRis();
            jfRisultati.setVisible(true);
            jfRisultati.setFocusableWindowState(true);
            
        }
        
        /**
         * Consente di iniziare una nuova partita, salvando le attuali statistiche e re-inizializzando le
         * varie impostazioni
         * @param evt : evento del menu Nuova Partita
         */
        private void jmiNuovaPartitaCliccato(ActionEvent evt) throws IOException{
            
            // inizializzo la variabile di scelta
            nuovaPartita = 0;
            
            // chiedo che si voglia iniziare una nuova partita
            nuovaPartita = JOptionPane.showConfirmDialog(null, "Vuoi veramente iniziare una nuova partita?", "Nuova Partita", JOptionPane.YES_NO_OPTION);
            if (nuovaPartita == 0){ // se si vuole cominciare una nuova partita
                
                // salvo i risultati
                salvaRis();
                
                // e reimposto come da principio
                jpGioco.azzera();
                jfPreferenze.azzera();
                setEnabled(false);
                jfPreferenze.setVisible(true);
                
            }
            
        }
        
        /**
         * Metodo che permette di uscire dal gioco
         * @param evt : quando il mouse clicca l'oggetto jmiPreferenze del menu jmOpzioni
         */
        private void jmiEsciCliccato(ActionEvent evt) throws IOException{
            
            // quando chiudo, salvo i risultati della partita
            salvaRis();
            System.exit(0);
            
        }
        
        /**
         * Metodo che permette di cambiare la forma del velivolo
         * @param evt : evento del menu Forma
         */
        private void jrbmiFormaCliccato(ActionEvent evt){
            
            // in formaVelivolo inserisco ciò che si è scelto nei radio bottoni
            formaVelivolo = evt.getActionCommand();
            
            // aggiorno la forma del velivolo nel pannello di Gioco
            jpGiocoUpdate(formaVelivolo);
            
        }
        
        /**
         * Metodo che permette di aggiornare l'animazione del velivolo
         * @param formaVel : forma del velivolo
         */        
        public void jpGiocoUpdate(String formaVel){
            
            // se la forma scelta è quella simbolica
            if (formaVel.equals("Simbolica")){

                // attivo la sentinella che segnala che la forma iniziale è stata scelta
                jpGioco.simbolica = true;
                
                // aggiorno le dimensioni della nuova forma del velivolo
                jpGioco.altVelivolo = ALT_VELIVOLO;
                jpGioco.larVelivolo = LAR_VELIVOLO;
                
                // aggiorno i radio bottoni della finestra di Gioco e della finestra delle Preferenze
                jrbmiSimbolica.setSelected(true);
                jfPreferenze.jrbSimbolica.setSelected(true);
                
            }
            else{ // se invece non è stata scelta la forma simbolica
                
                // disattivo la sentinella della forma simbolica
                jpGioco.simbolica = false;
                
                // salvo l'immagine della forma che è stata scelta
                jiImmagine = Toolkit.getDefaultToolkit().getImage("./Immagini/"+formaVel+".gif");
                
                // aggiorno l'immagine del velivolo nel pannello di Gioco
                jpGioco.immagine = jiImmagine;
                
                // se la forma scelta è quella Statica
                if (formaVel.equals("Statica")){
                    
                    // allora aggiorno le dimensioni del velivolo
                    jpGioco.altVelivolo = jiImmagine.getWidth(jpGioco);
                    jpGioco.larVelivolo = jiImmagine.getHeight(jpGioco);
                    
                    // se i valori della dimensione del velivolo venissero impostati a -1
                    if ((jpGioco.altVelivolo == -1) || (jpGioco.larVelivolo == -1))
                        
                        // lo forzo in maniera ricorsiva
                        jpGiocoUpdate(formaVel);
                    
                    // aggiorno i radio bottoni delle finestre di Gioco e delle Preferenze
                    jrbmiStatica.setSelected(true);
                    jfPreferenze.jrbStatica.setSelected(true);
                                        
                }
                else{
                    
                    jpGioco.altVelivolo = jiImmagine.getWidth(jpGioco);
                    jpGioco.larVelivolo = jiImmagine.getHeight(jpGioco);
                    
                    if ((jpGioco.altVelivolo == -1) || (jpGioco.larVelivolo == -1))
                        jpGiocoUpdate(formaVel);
                    
                    jrbmiDinamica.setSelected(true);
                    jfPreferenze.jrbDinamica.setSelected(true);
                    
                }
            }
            
            // aggiorno l'animazione del velivolo
            jpGioco.update(jpGioco.getGraphics());
            
        }
        
        /**
         * Metodo che permette di visualizzare il frame Preferenze
         * @param evt : quando il mouse clicca l'oggetto jmiPreferenze del menu jmOpzioni
         */
        private void jmiPreferenzeCliccato(ActionEvent evt){
            
            jfPreferenze.setVisible(true);
            jfPreferenze.setFocusableWindowState(true);
            setEnabled(false);
            
        }
        
        /**
         * Metodo che permette di visualizzare ulteriori informazioni sul gioco in una finestra a parte
         * @param evt : evento del click del mouse sul menu Guida
         */
        private void jmiAboutCliccato(ActionEvent evt) {
            
            jfAbout.setVisible(true);
            jfAbout.setFocusableWindowState(true);
            
        }
        
        /**
	 * Ritorna l'angolo iniziale dei colpi
	 * @return angolatura del cannone
	 */
	private double checkAng (){
            
		try{
			iAngolatura = Double.parseDouble(jtAngolatura.getText());
                        
                        // se l'angolatura non è compresa nell'intervallo 0-90°
			if ((iAngolatura<0)||(iAngolatura>90)){
                            
                            JOptionPane.showMessageDialog(null, "L'angolatura del cannone deve essere compresa tra 0 e 90 gradi!",
						"Attenzione", JOptionPane.ERROR_MESSAGE);
        
                            // imposto la variabile d'angolatura fuori dal suo intervallo
                            iAngolatura=-1;
                                
			}
                        
		}
                
                // se sono stati immessi dei caratteri al posto dei valori
                catch(Exception e){
                    
                    JOptionPane.showMessageDialog(null, "Il campo dell'angolatura del cannone accetta solo numeri!",
					"Attenzione", JOptionPane.ERROR_MESSAGE);
                    
                    iAngolatura = -1;
                    
		}
                
                // ritorno il valore dell'angolo
		return iAngolatura;
                
	}
        
        /**
	 * Ritorna la velocità del proiettile
	 * @return velocità del proiettile
	 */
	private double checkVel (){
            
		try{
                    
                    iVelocita= Double.parseDouble(jtVelocita.getText());
                    
		}
                
                // se sono stati immessi dei caratteri al posto dei valori
                catch(Exception e){
                    
                    JOptionPane.showMessageDialog(null, "Il campo della velocità del cannone accetta solo numeri!",
					"Attenzione", JOptionPane.ERROR_MESSAGE);
			
                    iVelocita = -1;
		}
                
                // ritorno la velocità
		return iVelocita;
                
	}
        
        /**
         * Metodo che permette di sparare il proiettile o la raffica di proiettili
         * @param evt : evento quando si clicca il bottone Spara!
         */        
        private void jbSparaCliccato(ActionEvent evt){
            
            // se le textbox sono vuote da errore
            if ((jtAngolatura.getText().isEmpty()) || (jtVelocita.getText().isEmpty()))
                
                JOptionPane.showMessageDialog(null, "Ti sei dimenticato di inserire o la velocità o l'angolatura del proiettile!", "Attenzione", JOptionPane.ERROR_MESSAGE);
            
            // se non si sta sparando, allora si procede al tiro
            else if (!(jpGioco.sentinellaFuoco)){
                
                // se l'input dei dati è giusto
                if ((checkAng() != -1) && (checkVel() != -1))
                {
                    
                    // passo alle variabili interne del pannello di Gioco la raffica, la velocità e l'angolatura dei colpi
                    jpGioco.rafficaColpi = jfPreferenze.sliderColpi; 
                    jpGioco.velColpi = jfPreferenze.sliderVelocita; 
                    jpGioco.angColpi = (double) jfPreferenze.sliderAngolatura;
                    
                    // imposto l'angolatura e la velocità
                    jpGioco.setAng(iAngolatura);
                    jpGioco.setVel(iVelocita);
                    
                    // parto con l'animazione del proiettile
                    jpGioco.timerProiettile.start();
                    
                    // attivo la sentinella che indica che si sta sparando
                    jpGioco.sentinellaFuoco = true;
                    
                }
                
            }
            
            else // altrimenti da errore
                
                JOptionPane.showMessageDialog(null, "Non puoi ancora sparare!", "Attenzione", JOptionPane.ERROR_MESSAGE);
            
        }
        
        /**
         * Metodo che permette di salvari i risultati nel file Risultati
         * @throws java.io.IOException
         */
        
        private void salvaRis() throws IOException {
            
            // se non sono stati sparati colpi
            if (jpGioco.numColpi == 0)
                
                // allora imposto la variabile dei colpi a 0
                milleColpi = 0;
            
            else
                
                // altrimenti calcolo la seconda parte della funzione per il calcolo dei punteggi
                milleColpi = (1000 / jpGioco.numColpi);
                
            // in questo modo posso controllare se il punteggio è 0 o diverso da 0
            punteggio = jpGioco.numVelivoli * milleColpi;
                
            // se ho sparato colpi
            if (punteggio != 0)
                {
         
                    // controllo se è possibile inserire il giocatore nella top ten
                    jcRisultati.addRis(jfPreferenze.textNome, jfPreferenze.textCognome, punteggio);

                }
            
        }
              
        private ButtonGroup bgForma; // il gruppo dei radio bottoni
        private Image jiImmagine; // l'immagine del velivolo
        private JMenu jmGioco; // il menu di gioco
        private JMenu jmForma; // il menu della forma del velivolo
	private JMenu jmOpzioni; // il menu delle opzioni
        private JMenu jmAiuto; // menu d'aiuto
	private JMenuBar jmbMenu; // la barra dei menu
	private JMenuItem jmiEsci; // il sottomenu per uscire
        private JMenuItem jmiNuovaPartita; // il sottomenu per iniziare una nuova partita
	private JMenuItem jmiRisultati; // il sottomenu per visualizzare i risultati
	private JMenuItem jmiPreferenze; // il sottomenu per impostare le opzioni
        private JMenuItem jmiAbout; // il sottomenu per visualizzare chi ha creato il gioco
        private JLabel jlSfondo; // lo sfondo del gioco
        private JLabel jlCannone; // il cannone
        private JFAbout jfAbout; // la finestra about
        private JFPreferenze jfPreferenze; // la finestra delle preferenze
        private JFRisultati jfRisultati; // finestra dei risultati
        private JPanel jpComandi; // il pannello dei comandi
        private JPGioco jpGioco; // il pannello di Gioco
        private JTextField jtVelocita; // la textbox per la velocità del proiettile
        private JTextField jtAngolatura; // la textbox per l'angolatura del proiettile
        private Punteggi jcRisultati; // la classe dei Punteggi
        
        public JButton jbSpara; // il bottone per fare fuoco
        public JRadioButtonMenuItem jrbmiSimbolica; // il radio bottone per la forma simbolica
        public JRadioButtonMenuItem jrbmiStatica; // statica
        public JRadioButtonMenuItem jrbmiDinamica; // e dinamica
        
        private double iAngolatura; // la variabile per l'angolo del proiettile
        private double iVelocita; // e per la velocità
        private int punteggio; // punteggio dell'attuale giocatore
        private int nuovaPartita; // variabile per controllare se si è scelto di iniziare o meno una nuova partita
        private int milleColpi; // variabile per controllare se non si sono sparati colpi
        
        public String formaVelivolo; // variabile per la forma del velivolo
        
        // dimensioni e coordinate del pannello JPGioco
        private static final int ALTEZZA_JPGIOCO = 550;
        private static final int LARGHEZZA_JPGIOCO = 600;
        private static final int X_JPGIOCO = 0;
        private static final int Y_JPGIOCO = 0;
        
        // dimensioni e coordinate del pannello JPComandi
        private static final int ALTEZZA_JPCOMANDI = 550;
        private static final int LARGHEZZA_JPCOMANDI = 195;
	private static final int X_JPCOMANDI = 600;
        private static final int Y_JPCOMANDI = 0;
        
        // dimensioni e coordinate dei textbox Angolatura e Velocita
        private static final int X_TESTO = 7;
        private static final int LARGHEZZA_TESTO = 180;
        private static final int ALTEZZA_TESTO = 40;
        private static final int Y_JTANGOLATURA = 280;
        private static final int Y_JTVELOCITA = 220;
        
        // dimensioni e coordinate del bottone JBSpara
        private static final int X_JBSPARA = 50;
        private static final int Y_JBSPARA = 370;
        private static final int LARGHEZZA_JBSPARA = 80;
        private static final int ALTEZZA_JBSPARA = 25;
        
        // dimensioni e millisecondi del velivolo e del proiettile
        private static final int LAR_VELIVOLO = 80;
        private static final int ALT_VELIVOLO = 80;
        private static final int MS_VELIVOLO = 20;
        private static final int MS_PROIETTILE = 20;
        private static final double RAGGIO_PROIETTILE = 5;
        
        // dimensioni e coordinate del cannone
        private static final int X_CANNONE = -100;
        private static final int Y_CANNONE = 350;
        private static final int LAR_CANNONE = 271;
        private static final int ALT_CANNONE = 200;
        
        // dimensioni e coordinate del frame JFRisultati
        private static final int ALTEZZA_JFRISULTATI = 250;
        private static final int LARGHEZZA_JFRISULTATI = 550;
        private static final int X_JFRISULTATI = 200;
        private static final int Y_JFRISULTATI = 200;
        
}