package it.polito.tdp.esame20130710T1;

/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.esame20130710T1.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCarica"
    private Button btnCarica; // Value injected by FXMLLoader

    @FXML // fx:id="btnCerca"
    private Button btnCerca; // Value injected by FXMLLoader

    @FXML // fx:id="theRoot"
    private BorderPane theRoot; // Value injected by FXMLLoader

    @FXML // fx:id="txtLunghezza"
    private TextField txtLunghezza; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaFinale"
    private TextField txtParolaFinale; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaIniziale"
    private TextField txtParolaIniziale; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="x1"
    private Color x1; // Value injected by FXMLLoader
    
    /**
     * Carica le parole formate da tot lettere
     * @param event
     */
    @FXML
    void doUpload(ActionEvent event) {
    	this.txtResult.clear();
    	Integer n = null;
    	try {
    		n = Integer.parseInt(this.txtLunghezza.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valore numerico!");
    		return;
    	}
    	
    	// non controllo
    	this.model.createGraph(n);
    	this.txtResult.appendText(this.model.printGraph());
    }

    @FXML
    void soSearch(ActionEvent event) {
    	this.txtResult.appendText("\n\n");
    	
    	Integer n = null;
    	try {
    		n = Integer.parseInt(this.txtLunghezza.getText());
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Inserisci un valore numerico!");
    		return;
    	}
    	
    	String p1 = this.txtParolaIniziale.getText();
    	String p2 = this.txtParolaIniziale.getText();
    	
    	if(!model.wordInDict(p1,p2) && p1.length()==p2.length() && p1.length()==n) {
    		this.txtResult.setText("Le parole inserite non sono presenti nel dizionario o non hanno la lunghezza specificata");
    		return;
    	}
    	
    	for(String s : this.model.winTheMatch(p1, p2)) {
    		this.txtResult.appendText(s+"\n\t");
    	}
    	return;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCarica != null : "fx:id=\"btnCarica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtParolaFinale != null : "fx:id=\"txtParolaFinale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtParolaIniziale != null : "fx:id=\"txtParolaIniziale\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model m) {
    	this.model = m;
    }

}
