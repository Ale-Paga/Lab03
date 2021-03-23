package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	
	List<String> dizionario;
	
	
	
	
	public Dictionary() {
		super();
		this.dizionario = new ArrayList<>();
	}




	public void loadDictionary(String language) {
		
		if(language.equals("English")) {
			try {
				FileReader fr = new FileReader("src/main/resources/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					this.dizionario.add(word);
				}
				br.close();
				} catch (IOException e){
				System.out.println("Errore nella lettura del file");
				}
		}else if(language.equals("Italiano")) {
			try {
				FileReader fr = new FileReader("src/main/resources/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					this.dizionario.add(word);
				}
				br.close();
				} catch (IOException e){
				System.out.println("Errore nella lettura del file");
				}
		}
		
		
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){
		List<RichWord> risultato= new ArrayList<>();
		
		for(int i=0; i<inputTextList.size(); i++) {
			if(this.dizionario.contains(inputTextList.get(i))) {
				risultato.add(new RichWord(inputTextList.get(i), true));
			}else {
				risultato.add(new RichWord(inputTextList.get(i), false));
			}
		}
		
		return risultato;
		
	}

}
