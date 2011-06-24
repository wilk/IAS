/**
 * JPGioco è il pannello di gioco dove sono gestite le animazioni del moto del proiettile
 * e del moto del velivolo.
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Classe del pannello di gioco del velivolo e del proiettile
 * @author Ferrari Vincenzo e Iadarola Barbara.
 *
 */

public class JPGioco extends JPanel implements ActionListener{
		
/**
 * Costruttore del Pannello di gioco
 * @param l_velivolo Larghezza del velivolo
 * @param h_velivolo Altezza del velivolo
 * @param millisecondiA Tempo di scansione dell'aereo
 * @param raggio_proiettile Raggio del proiettile
 * @param millisecondiP Tempo di scansione del proiettile
 */	
	
	public JPGioco (int l_velivolo, int h_velivolo,	int millisecondiA, double raggio_proiettile, int millisecondiP){

                // inizializzo le variabili del velivolo
                numVelivoli = 0;
                
                // inizializzo le due pause che dovrà fare il velivolo
                pausa1 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2);
                pausa2 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2) + LARGHEZZA_JPGIOCO/2;
                contatorePause = 0;
                pausaDx = false;
                pausaSx = false;
                
                // attivo la sentinella della forma simbolica
                simbolica = true;
                
                // disattivo la sentinella per lo sparo
                sentinellaFuoco = false;
                
                // imposto l'altezza e la larghezza del velivolo
		larVelivolo = l_velivolo;
		altVelivolo = h_velivolo;
		periodoVelivolo = millisecondiA;
                
                // imposto la direzione iniziale del velivolo, se destra o sinistra
                randomDirezione = genRandom.nextInt(2);
                impostaDir(randomDirezione);
                yVelivolo = genRandom.nextInt((ALTEZZA_JPGIOCO/2)/2);
                
                // inizializzo il timer del velivolo e lo faccio partire
		timerVelivolo = new Timer(periodoVelivolo, this);
		timerVelivolo.start();
		
                // inizializzo le variabili del proiettile
                numColpi = 0;
                velColpi = 0;
                angColpi = 0;
                contaColpi = 0;
                
                // imposto la raffica dei proiettili a -1 per il controllo sul contaColpi
                rafficaColpi = -1;
                
                // imposto lo step del proiettile e la sua costante di gravità
		step = 0.1;
		gConst = 9.8;
		raggio = raggio_proiettile;
		periodoProiettile = millisecondiP;
                inixProiettile = 0;
		xProiettile = inixProiettile;
		iniyProiettile = ALTEZZA_JPGIOCO - (2 * raggio);
		yProiettile = iniyProiettile;
		timerProiettile = new Timer(periodoProiettile, this);
		
                // attivo il double buffered
                this.setDoubleBuffered(true);	
                
        }
        
        /**
         * Metodo che permette di impostare la direzione del velivolo, casualmente
         * utilizzando genRandom come intero.
         * @param dirVel : direzione del velivolo, se sx o dx
         */
        public void impostaDir(int dirVel){
            
            // se è 0 parte da dx, se è 1 parte da sx
            if (dirVel == 0)
                        {
                            
                            // il velivolo non deve esserci al momento della partenza
                            xVelivolo = -(larVelivolo + (larVelivolo/2));
                            versoDestra = true;
                            
                        }
                        else
                        {
                            
                            xVelivolo = (ALTEZZA_JPGIOCO + larVelivolo);
                            versoDestra = false;
                            
                        }
            
        }
        
   /**
    * Metodo che permette di re-impostare tutti i valori allo stato originale
    */ 
   public void azzera(){
        
       // fermo l'animazione del proiettile
        timerProiettile.stop();
        
        // velivolo
        numVelivoli = 0;
        
        // reimposto le due pause del velivolo
        pausa1 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2);
        pausa2 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2) + LARGHEZZA_JPGIOCO/2;
        
        // la forma iniziale
        simbolica = true;
        contatorePause = 0;
        pausaDx = false;
        pausaSx = false;
        sentinellaFuoco = false;
        xVelivolo = -(larVelivolo + (larVelivolo/2));
        yVelivolo = genRandom.nextInt((ALTEZZA_JPGIOCO/2)/2);
        randomDirezione = genRandom.nextInt(2);
        impostaDir(randomDirezione);
        
        // proiettile
        numColpi = 0;
        velColpi = 0;
        angColpi = 0;
        contaColpi = 0;
        rafficaColpi = -1;
        xProiettile = inixProiettile;
        yProiettile = iniyProiettile;
        
    }
        
/**
 * Imposta la velocità del proiettile
 * @param v Velocità inserita dal giocatore
 */
    public void setVel(double v){
        
        velProiettile = v;
        
        // inserisco nella variabile di supporto la velocità del proiettile
        velPTemporanea = velProiettile;
	VyProiettile = -(velProiettile) * Math.sin(angProiettile);
        
    }
		
/**
 * Imposta l'angolo di alzo rispetto all'orizzonte
 * @param angolo Angolo di alzo
 */		 
    public void setAng(double angolo){
        
        // trasformo il valore dell'angolo in radianti
        angProiettile = Math.toRadians(angolo);
        
    }
    
    /**
     * Metodo con cui gestire le due animazioni: quella del velivolo e quella del moto
     * @param e : evento dell'animazione
     */		
    public void actionPerformed(ActionEvent e){
    
        // l'actionPerformed parte col Proiettile
        
             VxProiettile = velProiettile * Math.cos(angProiettile);
             
                // se non sono ancora stati sparati tutti i colpi
                if (contaColpi < rafficaColpi){
                
                // se il proiettile raggiunge le estremità
	        if ((xProiettile >= LARGHEZZA_JPGIOCO) || (xProiettile <= (-(raggio * 2))) || (yProiettile >= ALTEZZA_JPGIOCO))
                {
                    
                    // faccio partire il proiettile dall'angolo in basso a sinistra
                    xProiettile = inixProiettile;
                    yProiettile = iniyProiettile;
                    
                    
                    VyProiettile = -(velProiettile) * Math.sin(angProiettile);
                    
                    // aggiorno il contaColpi per vedere se ho finito di sparare
                    contaColpi++;
                    
                    // aggiungo il quantitativo (se ve n'è) alla velocità e all'angolatura
                    velProiettile = velProiettile + (velPTemporanea * velColpi / 100);
                    angProiettile = angProiettile + Math.toRadians(angColpi/10);
                    numColpi++;
                    
                }
                // altrimenti continua
                else
                {
                    
                    yProiettile = yProiettile + (step * VyProiettile);
                    VyProiettile = VyProiettile + (step * gConst);
                    xProiettile = xProiettile + (step * VxProiettile);
                    
                }
    } // altrimenti finisce
             
    else{
        
        timerProiettile.stop();
        contaColpi = 0;
        rafficaColpi = -1;
        sentinellaFuoco = false;
        
    }
             
             // E prosegue col Velivolo
    
      // se sto andando verso destra
    if (versoDestra)
    { 
        
        // se incontro la prima pausa da dx
        if (pausaDx){
            
            // fermo il velivolo e disattivo la prima sentinella
            timerVelivolo.setDelay(periodoVelivolo);
            pausaDx = false;
            
        }
        switch (contatorePause)
        {
            
            case 0 : { // pausa nella prima metà
                
                if (xVelivolo == pausa1)
                {
                    
                    timerVelivolo.stop();
                    timerVelivolo.setDelay(5000);
                    pausaDx = true;
                    timerVelivolo.restart();
                    contatorePause++;
                    
                }
                
            }
            case 1 : { // pausa nella seconda metà
                
                if (xVelivolo == pausa2)
                {

                    timerVelivolo.stop();
                    timerVelivolo.setDelay(5000);
                    pausaDx = true;
                    timerVelivolo.restart();
                    contatorePause++;
                    
                }
                
            }
                            
        }
    	// controlla se xPos > bordo destro (compresa dimensione sfera)
    	if (xVelivolo > (ALTEZZA_JPGIOCO + (altVelivolo/2))) 
    	{ 
    		// annulla direzione e decrementa coordinata x
    		versoDestra = false;
                pausa1 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2) + LARGHEZZA_JPGIOCO/2;
                pausa2 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2); 
                yVelivolo = genRandom.nextInt((ALTEZZA_JPGIOCO/2)/2);
    		xVelivolo--;
    	}
    	// in caso contrario incrementa coordinata x
    	else xVelivolo++;
    }
    // se sto andando verso sinistra
    else
    {
        if (pausaSx){
            timerVelivolo.setDelay(periodoVelivolo);
            pausaSx = false;
        }
        switch (contatorePause) 
        {
            
            case 2 : { // pausa nella seconda metà
                
                if (xVelivolo == pausa1)
                {

                    timerVelivolo.stop();
                    timerVelivolo.setDelay(5000);
                    pausaSx = true;
                    timerVelivolo.restart();
                    contatorePause++;
                    
                }
                
            }
            case 3 : { // pausa nella prima metà
                
                if (xVelivolo == pausa2)
                {

                    timerVelivolo.stop();
                    timerVelivolo.setDelay(5000);
                    pausaSx = true;
                    timerVelivolo.restart();
                    contatorePause = 0;
                    
                }
                
            }
            
        }
    	// se ho passato bordo sinistro
    	if (xVelivolo < (-(larVelivolo))) 
    	{  
    		// ritorna verso destra e incrementa coordinata x
    		versoDestra = true;
                pausa1 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2);
                pausa2 = genRandom.nextInt(LARGHEZZA_JPGIOCO/2) + LARGHEZZA_JPGIOCO/2;
                yVelivolo = genRandom.nextInt((ALTEZZA_JPGIOCO/2)/2);
    		xVelivolo++;
    	}
    	// se invece sono nella finestra decrementa coordinata x
    	else xVelivolo--;
    }
		    
	    repaint();

    }

    /**
     * Metodo che consente di disegnare le due animazioni e che controlla l'intersezione
     * delle stesse
     * @param gra : oggetto grafico del pannello
     */
	public void paintComponent(Graphics gra){
            
                //Si disegna il velivolo
		Graphics2D gra2D = (Graphics2D) gra;
	  	super.paintComponent(gra2D);
                
                // se la forma è simbolica
                if (simbolica)
                {
                    
                    // la costruisco
                    gra2D.setColor(Color.red);
                    gra2D.fillRect(xVelivolo, yVelivolo, larVelivolo, altVelivolo);
                    
                }
                else
                    
                    // altrimenti vi inserisco l'immagine
                    gra2D.drawImage(immagine, xVelivolo, yVelivolo, this);

                //Si disegna il proiettile
	  	gra2D.setColor(Color.yellow);
  		forma = new Ellipse2D.Double(xProiettile, yProiettile, 2*raggio, 2*raggio);
		gra2D.fill(forma);
		this.area = new Area(forma);
                
                // se il velivolo e il proiettile si intersecano
                if (area.intersects(xVelivolo, yVelivolo, larVelivolo, altVelivolo)){
                    
                        // posiziono il proiettile nell'angolo in basso a sinistra
                        xProiettile = inixProiettile;
                        yProiettile = iniyProiettile;
                        
                        // fermo la sua animazione
                        timerProiettile.stop();
                        
                        // reimposto la partenza del velivolo (se dx o sx)
                        randomDirezione = genRandom.nextInt(2);
                        impostaDir(randomDirezione);
                        yVelivolo = genRandom.nextInt((ALTEZZA_JPGIOCO/2)/2);
                        
                        // aggiorno i vari contatori e sentinelle
                        sentinellaFuoco = false;
                        contaColpi = rafficaColpi;
                        numColpi++;
                        numVelivoli++;
                    
                }
                    
    }
                
        private Random genRandom = new Random(); // generatore di numeri casuali per le coordinate x e y del velivolo
        
        public Area area;	//calcola l'area del proiettile
        public Image immagine; // immagine del velivolo
        public Shape forma; //passa la forma del proiettile
        public String formaVelivolo; // forma del velivolo
        public Timer timerVelivolo, timerProiettile;	//timer di aereo e proiettile
        
        private boolean versoDestra;	//decide il movimento dell'aereo
        private boolean pausaDx, pausaSx; // sentinelle delle 2 pause
        private double step;	//step del proiettile
	private double gConst;	//costante di gravità
	private double raggio;	//raggio del proiettile
	private double xProiettile, yProiettile, iniyProiettile, inixProiettile, VxProiettile, VyProiettile;	//coordinate e velocità del proiettile
	private double angProiettile, velProiettile; //angolo di alzo impostato dall'utente e velocità impostata dall'utente
        private double velPTemporanea; // variabile di supporto per la velocità temporanea del proiettile
        private int periodoVelivolo, periodoProiettile;	//tempo di scansione di aereo e proiettile
	private int xVelivolo, yVelivolo;	//coordinata x dell'aereo
        private int contatorePause; // contatore delle pause
        private int pausa1, pausa2; // valori "x" delle pauseù
        private int randomDirezione; // scelta casuale da dove partirà il velivolo, se dx o sx
        
        public boolean simbolica; // sentinella della forma simbolica
        public boolean sentinellaFuoco; // sentinella se si sta sparando
        public int altVelivolo,larVelivolo;	//altezza e larghezza forma simbolica
        public int rafficaColpi; // numero dei colpi in una raffica
        public int contaColpi; // contatore dei colpi della raffica dei colpi (1, 2, 3, 4)
        public int velColpi; // velocità dei colpi data dal valore dello slider nelle Preferenze
        public double angColpi; // angolatura dei colpi data dal valore dello slider nelle Preferenze
        public int numColpi, numVelivoli; // numero dei colpi sparati e numero dei velivoli abbattuti, utili per il punteggio
        
        // dimensioni pannello di gioco
        private static final int ALTEZZA_JPGIOCO = 550;
        private static final int LARGHEZZA_JPGIOCO = 600;
}
