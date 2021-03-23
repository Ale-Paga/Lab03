package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	
	List<String> dizionario;
	List<String> dizionario2;
	
	
	
	
	public Dictionary() {
		super();
		this.dizionario = new ArrayList<>();
		this.dizionario2 = new LinkedList<>();

	}




	public void loadDictionary(String language) {
		
		if(language.equals("English")) {
			try {
				FileReader fr = new FileReader("src/main/resources/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					this.dizionario.add(word);
					this.dizionario2.add(word);
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
					this.dizionario2.add(word);
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
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> risultato= new ArrayList<>();
		
		for(int i=0; i<inputTextList.size(); i++) {
			risultato.add(new RichWord(inputTextList.get(i), false));
			for(int j=0; j<this.dizionario.size();j++) {
				if(inputTextList.get(i).equals(this.dizionario.get(j))) {
					risultato.get(i).setCorretta(true);
					break;
				}
			}
		}
		
		return risultato;
		
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList){
		List<RichWord> risultato= new ArrayList<>();
		int indice= dizionario.size()/2;
		int max=dizionario.size();
		int min=0;
		System.out.println("TEST 1");
		
		for(int i=0; i<inputTextList.size(); i++) {
			System.out.println("TEST 2");
			risultato.add(new RichWord(inputTextList.get(i), false));
			while(min!=max) {				
				if(inputTextList.get(i).compareTo(dizionario.get(indice))==0) {
					risultato.get(i).setCorretta(true);
					System.out.println("TEST 3");
					break;
				}else if(inputTextList.get(i).compareTo(dizionario.get(indice))>0){
					min=indice;
					indice=(min+max)/2;
					System.out.println("TEST 4");
				}else if(inputTextList.get(i).compareTo(dizionario.get(indice))<0) {
					max=indice;
					indice=(min+max)/2;
					System.out.println("TEST 5");
				}
			}
			
		}
		return risultato;
		
	}

}
