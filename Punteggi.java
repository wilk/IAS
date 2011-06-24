/*
 * Classe che permette di scrivere e leggere sul file Risultati
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Classe che permette l'interazione col file Risultati
 * @author Ferrari Vincenzo e Iadarola Barbara.
 */
public class Punteggi {
    
    /**
     * Costruttore della classe punteggi
     * @param dirFile : il percorso del file Risultati.txt
     */
    public Punteggi (String dirFile) {
        
        percorsoRis = dirFile;
        
    }
    
    /**
     * Metodo che permette l'inserimento di un punteggio nella top ten dei risultati
     * @param nGiocatore : nome del giocatore
     * @param cGiocatore : cognome del giocatore
     * @param pGiocatore : punteggio del giocatore
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.util.NoSuchElementException
     * @throws java.lang.NumberFormatException
     */
    public void addRis(String nGiocatore, String cGiocatore, int pGiocatore) throws IOException, FileNotFoundException, NoSuchElementException, NumberFormatException {
        
        try {
            
            // istanzio lo scanner
            fileReader = new FileReader(percorsoRis);
            fileScanner = new Scanner(fileReader);
            
            // creo il vettore dei Giocatori
            // ossia la lista di supporto per controllare se è possibile inserire l'ultimo punteggio
            // nella top ten
            ArrayList<Giocatore> listaGiocatori = new ArrayList<Giocatore>();
            
            // inserisco i risultati attuali del file nella lista da confrontare
            for (int i=0; i < RIGHE_TOP_TEN; i++) {
                
                String nome = fileScanner.next();
                String cognome = fileScanner.next();
                int risultato = Integer.parseInt(fileScanner.next());
                listaGiocatori.add(new Giocatore(nome, cognome, risultato));
                
            }
            
            int j = 0;
            sentinella = false;
            
            // scorro la lista e controllo se il punteggio dell'ultima partita può essere collocato
            // nella top ten
            while ((j < listaGiocatori.size()) && (!sentinella)) {
                
                // se vedo che rientra nella topten
                if (listaGiocatori.get(j).getPunteggio() < pGiocatore) {
                    
                    // lo inserisco al posto giusto
                    listaGiocatori.add(j, new Giocatore (nGiocatore, cGiocatore, pGiocatore));
                    
                    // ed esco dal ciclo
                    sentinella = true;
                    
                }
                
                j++;
                
            }
            
            // istanzio il writer
            fileWriter = new PrintWriter(percorsoRis);
            
            // scrivo i dati sul file, solo i primi 9
            for (int i=0; i < RIGHE_TOP_NINE; i++) {
                
                fileWriter.println(listaGiocatori.get(i).getNome()+" "+listaGiocatori.get(i).getCognome()+" "+listaGiocatori.get(i).getPunteggio());
                                
            }
            
            // scrivo l'ultimo senza andare a capo
            fileWriter.print(listaGiocatori.get(RIGHE_TOP_TEN).getNome()+" "+listaGiocatori.get(RIGHE_TOP_TEN).getCognome()+" "+listaGiocatori.get(RIGHE_TOP_TEN).getPunteggio());
            
        }
        
        finally {
            
            // chiudo il fileReader, il fileWriter e il fileScanner se sono ancora aperti
            if (fileReader != null)
                fileReader.close();
            if (fileWriter != null)
                fileWriter.close();
            if (fileScanner != null)
                fileScanner.close();
            
        }
        
    }
    
    /**
     * Metodo che ritorna i risultati contenuti nel file Risultati.txt
     * @return : ritorna una matrice di Stringhe contenente i risultati
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.util.NoSuchElementException
     */
    public String[][] getRis() throws IOException, FileNotFoundException, NoSuchElementException {
        
        try {
            
            // istanzio lo scanner
            fileReader = new FileReader(percorsoRis);
            fileScanner = new Scanner(fileReader);
            
            // creo la matrice 10*3 che conterrà i vari giocatori del file
            arrRisultati = new String[RIGHE_TOP_TEN][COLONNE_TOP_TEN];
            
            // inserisco i vari giocatori nella matrice
            for (int i=0; i < RIGHE_TOP_TEN; i++) 
                
                for (int j=0; j < COLONNE_TOP_TEN; j++)
                    
                    arrRisultati[i][j] = fileScanner.next();                
            
            // ritorno la matrice
            return arrRisultati;
            
        }
        
        finally {
            
            // chiudo il fileReader e il fileScanner se sono ancora aperti
            if (fileReader != null)
                fileReader.close();
            if (fileScanner != null)
                fileScanner.close();
            
        }
        
    }
    
    /**
     * Metodo che reimposta il file Risultati.txt con i valori iniziali
     */
    public void delRis() {
        
        try {
            
            // istanzio il fileWriter
            fileWriter = new PrintWriter(percorsoRis);
            
            // riscrivo sul file la tabella iniziale dei giocatori
            for (int i=0; i < RIGHE_TOP_NINE; i++)  {
                
                String nome = data[i][0];
                String cognome = data[i][1];
                String punteggio = data[i][2];
                fileWriter.println(nome+" "+cognome+" "+punteggio);
                
            }
            
            // scrivo l'ultima riga senza andare a capo
            
            String nome = data[RIGHE_TOP_NINE][0];
            String cognome = data[RIGHE_TOP_NINE][1];
            String punteggio = data[RIGHE_TOP_NINE][2];
            fileWriter.println(nome+" "+cognome+" "+punteggio);
            
        }
        
        // se non trovo nessun file
        catch (FileNotFoundException e) {
            
            JOptionPane.showMessageDialog(null, "File dei risultati non trovato. Prova a crearlo manualmente nella cartella del gioco.", "Errore", JOptionPane.ERROR_MESSAGE);
            
        }
        
        finally {
            
            // chiudo il fileWriter se è ancora aperto
            if (fileWriter != null)
                fileWriter.close();
            
        }
        
    }

private String percorsoRis; // l'indirizzo del file dei risultati
private FileReader fileReader;
private Scanner fileScanner;
private PrintWriter fileWriter;
private String[][] arrRisultati; // matrice contenente la top ten
private boolean sentinella; // sentinella per uscire dal ciclo di controllo (vedi sopra)

// valori della matrice
public static final int RIGHE_TOP_TEN = 10;
public static final int RIGHE_TOP_NINE = 9;
public static final int COLONNE_TOP_TEN = 3;

public static final // matrice iniziale del file Risultati.txt
String[][] data =   {
						{"Mario", "Rossi", "1000"},
						{"Giovanni", "Bianchi", "900"},
						{"Luigi", "Neri", "800"},
						{"Claudio", "Rossi", "700"},
						{"Vincenzo", "Rossi", "600"},
						{"Marco", "Bianchi", "500"},
						{"Luca", "Bianchi", "400"},
						{"Giorgio", "Rossi", "300"},
						{"Fausto", "Neri", "200"},
						{"Julian", "Neri", "100"}
					};

}
