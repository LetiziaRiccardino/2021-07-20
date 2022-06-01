package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.yelp.model.Event.EventType;

public class Simulatore {
	
	//Dati in ingresso
	private int x1;
	private int x2;
	
	//Dati in uscita
	
	//i giornalisti sono rappresentati da un numero compreso tra 0 e x1-1
	private List<Giornalista> giornalisti;
	private int numeroGiorni; 	

	//Modello del mondo
	private Set<User> intervistati;
	private Graph<User, DefaultWeightedEdge> grafo;
	
	//Coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulatore(Graph<User, DefaultWeightedEdge> grafo) {
		this.grafo=grafo;
		
	}
	
	public void init(int x1, int x2) { 
		this.x1=x1;
		this.x2=x2;
		
		this.intervistati=new HashSet<User>();
		
		this.numeroGiorni=0;
		
		this.giornalisti= new ArrayList<Giornalista>();
		for(int i=0;i<this.x1; i++) {
			this.giornalisti.add(new Giornalista(i));
		}
		
		//pre-carico la coda
		for(Giornalista g: this.giornalisti) {
			User intervistato= selezionaIntervistato(this.grafo.vertexSet()); 
			
			this.intervistati.add(intervistato);
			g.incrementaNumeroIntervistati();
			
			this.queue.add(new Event (
					1, //giorno 
					EventType.DA_INTERVISTARE,
					intervistato,
					g
					));
		}
	}
	
	public void run(){ //elaborazine della coda e terminazione dell'elaborazione
		while(!this.queue.isEmpty() && this.intervistati.size()<x2){
			Event e = this.queue.poll();//estraggo l'evento
			this.numeroGiorni= e.getGiorno();
			processEvent(e);
			
		}
		
	}

	
	
	private void processEvent(Event e) {
		switch(e.getType()) {
		case DA_INTERVISTARE:
			double caso= Math.random();
			if(caso<0.6) {
				//caso 1
				User vicino= selezionaAdiacente(e.getIntervistato());
				if(vicino== null)
					vicino= selezionaIntervistato(this.grafo.vertexSet());
				
				this.queue.add(new Event(
						e.getGiorno()+1,
						EventType.DA_INTERVISTARE,
						vicino,
						e.getGiornalista()
						));
				this.intervistati.add(vicino);
				e.getGiornalista().incrementaNumeroIntervistati();
				
			}else if(caso<0.8) {
				//caso 2: rimando a domani la scelta
				this.queue.add(new Event(
						e.getGiorno()+1,
						EventType.FERIE,
						e.getIntervistato(),
						e.getGiornalista()
						));
			}else {
				//caso 3: domani continuo con lo stesso utente
				this.queue.add(new Event(
					e.getGiorno()+1,
					EventType.DA_INTERVISTARE,
					e.getIntervistato(),
					e.getGiornalista()
					));
			}
			break;
		case FERIE:
			break;
		}
		
	}

	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getNumeroGiorni() {
		return numeroGiorni;
	}
	public Set<User> getIntervistati() {
		return intervistati;
	}
	
	/**
	 * selezione un intervitato dalla lista specificata, evitando 
	 * di selezionare coloro che sono già in this.intervistati
	 * @param lista
	 * @return
	 */
	
	private User selezionaIntervistato(Collection<User> lista ) { 
		Set<User> candidati= new HashSet<User>(lista);
		candidati.removeAll(this.intervistati);
		
		int scelto= (int)(Math.random()* candidati.size());
		
		return (new ArrayList<User> (candidati)).get(scelto);
	}
	private User selezionaAdiacente(User u) {
		List<User> vicini= Graphs.neighborListOf(this.grafo, u);
		vicini.removeAll(this.intervistati);
		
		if(vicini.size()==0) //caso di vertice isolaro oppure tutti adiacenti tutti intervistati
			return null;
		
		double max=0;
		for(User v: vicini) {
			double peso=this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso > max)
				max=peso;
		}
		
		List<User> migliori= new ArrayList<>();
		for(User v: vicini) {
			double peso=this.grafo.getEdgeWeight(this.grafo.getEdge(u, v));
			if(peso == max) {
				migliori.add(v);
			}
		}
		
		int scelto= (int)(Math.random()*migliori.size());
		return migliori.get(scelto);
	}
	
}
