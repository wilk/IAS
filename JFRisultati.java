/*
 * Finestra dei risultati.
 * Viene visualizzata la top-ten dei punteggi migliori fatti nelle varie partite da giocatori differenti.
 */

import java.awt.BorderLayout;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.NoSuchElementException;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTable;


/**
 * Classe della finestra dei risultati
 * @author Ferrari Vincenzo e Iadarola Barbara
 */
public class JFRisultati extends JFrame {
    
    /**
     * Costruttore della finestra risultati
     * @param punteggi : classe Punteggi
     * @throws java.util.NoSuchElementException
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public JFRisultati(Punteggi punteggi) throws NoSuchElementException, FileNotFoundException, IOException{
        
        // imposto la finestra dei risultati
        setTitle("Risultati");
	setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        jcPunteggi = punteggi;
        
        
        
    }
    
    /**
     * Metodo che aggiorna il file Risultati
     */
    public void updateRis() {
        
        // l'intestazione della tabella
        String[] indiceColonne = {
            
            "Nome", "Cognome", "Punteggio"
            
        };
        
        // la matrice dei risultati impostata a valore iniziale
        String[][] tabRisultati = jcPunteggi.data;
        
        try 
		{ 	
                        
                        // carico i risultati
			tabRisultati = jcPunteggi.getRis();
                        
		}
		catch (NoSuchElementException e)
		{
                        // se il file Risultati è danneggiato
			JOptionPane.showMessageDialog(null, "Attenzione! Il file risultati è danneggiato. Tento di reimpostarlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                        // cerco di ripristinarlo
                        jcPunteggi.delRis();
                        
		}
		catch (FileNotFoundException e)
		{
                        // se il file Risultati non è presente
			JOptionPane.showMessageDialog(null, "Attenzione! Il file dei risultati non è stato trovato. Tento di crearlo.", "Errore", JOptionPane.ERROR_MESSAGE);
                        // cerco di crearlo
                        jcPunteggi.delRis();
                        
                }
		catch (IOException e)
		{
                        // se ci sono errori di I/O gravi, lo faccio presente
			JOptionPane.showMessageDialog(null, "Errore in lettura/scrittura del file dei punteggi", "Errore", JOptionPane.ERROR_MESSAGE);
			System.err.println("Erroe di Input/Output");
			e.printStackTrace();
                        
		}
		finally
		{
                    
                    // creo la tabella
                    JTable table = new JTable(tabRisultati, indiceColonne);
                    
                    // la inserisco nella finestra
                    getContentPane().add(table.getTableHeader(), BorderLayout.PAGE_START);
                    getContentPane().add(table, BorderLayout.CENTER);
                    table.setEnabled(false);
                        
                }
        
    }
    
    private Punteggi jcPunteggi; // classe Punteggi

}
