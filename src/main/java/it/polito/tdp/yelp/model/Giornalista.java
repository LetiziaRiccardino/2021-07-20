package it.polito.tdp.yelp.model;

public class Giornalista {
	private int id;
	private int numeroIntervistati;
	
	
	public Giornalista(int id) {
		super();
		this.id = id;
		this.numeroIntervistati=0;
	}


	public int getId() {
		return id;
	}
	public int getNumeroIntervistati() {
		return numeroIntervistati;
	}
	
	public void incrementaNumeroIntervistati() {
		this.numeroIntervistati++;
	}
	
	
	
	
}
