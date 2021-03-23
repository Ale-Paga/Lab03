package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	private Dictionary dizionario ;
	private long startTime=0;
	private long estimatedTime=0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLingua;
    
    @FXML
    private ComboBox<String> boxMode;

    @FXML
    private TextArea txtTesto;

    @FXML
    private TextArea txtErrori;

    @FXML
    private Label txtNumErrori;

    @FXML
    private Label txtTempo;

    @FXML
    void handleClearText(ActionEvent event) {
    	this.txtTesto.clear();
    	this.txtNumErrori.setText("");
    	this.txtErrori.clear();
    	this.txtTempo.setText("");

    }

    @FXML
    void handleSpellCheck(ActionEvent event) {
    	this.startTime=System.nanoTime();
    	String lingua = this.boxLingua.getValue();
    	this.dizionario.loadDictionary(lingua);
    	String frase = this.txtTesto.getText();
    	frase = frase.replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	frase = frase.toLowerCase();
    	List<String> fraseList= new ArrayList<>();
    	String[] st = frase.split(" ");
    	for(String f: st) {
    		fraseList.add(f);
    	}
    	
    	List<RichWord> ris = null; 
    	String mode = this.boxMode.getValue();
    	if(mode.equals("Classic")) {
    		ris = this.dizionario.spellCheckText(fraseList);
    	}else if(mode.equals("Linear")) {
    		ris = this.dizionario.spellCheckTextLinear(fraseList);
    	}else if(mode.equals("Dichotomic")) {
    		ris = this.dizionario.spellCheckTextDichotomic(fraseList);
    	}
    	
    	String risTxt="Le parole errate sono: \n";
    	int count=0;
    	for(int i=0; i<ris.size(); i++) {
    		if(ris.get(i).isCorretta()==false) {
    			count++;
    			risTxt= risTxt+ris.get(i).getParola()+"\n";
    		}
    	}
    	this.estimatedTime=(System.nanoTime()-startTime);
    	
    	this.txtNumErrori.setText("The text contains "+count+" errors");
    	
    	this.txtTempo.setText("Spell check completed in "+this.estimatedTime/1000000000+"."+this.estimatedTime%1000000000+" seconds");
    	this.txtErrori.setText(risTxt);
    	
    }
    
    public void setModel(Dictionary m) {
    	this.dizionario = m ;
    	this.boxLingua.getItems().addAll("English","Italiano");
    	this.boxMode.getItems().addAll("Classic","Linear","Dichotomic");
    	
    }

    @FXML
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumErrori != null : "fx:id=\"txtNumErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTempo != null : "fx:id=\"txtTempo\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
