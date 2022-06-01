package it.polito.tdp.yelp.model;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		DA_INTERVISTARE, 
		FERIE,
	}
	
	private int giorno;
	private EventType type;
	private User intervistato;
	private Giornalista giornalista;
	
	public Event(int giorno, EventType type, User intervistato, Giornalista giornalista) {
		super();
		this.giorno = giorno;
		this.type = type;
		this.intervistato = intervistato;
		this.giornalista = giornalista;
	}

	public int getGiorno() {
		return giorno;
	}
	public EventType getType() {
		return type;
	}
	public User getIntervistato() {
		return intervistato;
	}
	public Giornalista getGiornalista() {
		return giornalista;
	}
	

	@Override
	public int compareTo(Event o) {
		return this.giorno-o.giorno;
	}
	
	

}
