/*
 * Classe giocatore.
 * In questa classe vengono specificati i vari metodi per l'oggetto giocatore.
 */

/**
 * Classe dell'oggetto giocatore.
 * @author Ferrari Vincenzo e Iadarola Barbara.
 */
public class Giocatore
{

    /**
     * Costruttore per un giocatore
     */
    public Giocatore(){
        
        this("nome","cognome",0);
	
    }
	
    /**
     * Costruttore per un nuovo giocatore
     * @param nomeGiocatore : nome del giocatore
     * @param cognomeGiocatore : cognome del giocatore
     * @param punteggioGiocatore : punteggio del giocatore
     */
    public Giocatore(String nomeGiocatore, String cognomeGiocatore, int punteggioGiocatore) 
    {
        
    	nome=nomeGiocatore;
        cognome=cognomeGiocatore;
        punteggio=punteggioGiocatore;
        
    }
    
    /**
     * Metodo che ritorna il nome del giocatore
     * @return : il nome del giocatore
     */
    public String getNome( ) 
    {
        
      return nome;
      
    }
    
    /**
     * Metodo che ritorna il cognome del giocatore
     * @return : il cognome del giocatore
     */
    public String getCognome( ) 
    {
        
      return cognome;
      
    }
    
    /**
     * Metodo che ritorna il punteggio del giocatore
     * @return : il punteggio del giocatore
     */
    public int getPunteggio() 
    {
        
      return punteggio;
      
    }
       
    protected String nome; // nome del giocatore
    protected String cognome; // cognome del giocatore
    protected int punteggio; // punteggio del giocatore
    
 }
