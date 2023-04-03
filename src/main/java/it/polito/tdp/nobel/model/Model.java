package it.polito.tdp.nobel.model;

import java.util.*;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> allEsami;
	private Set<Esame> migliore;
	private double mediaMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.allEsami = dao.getTuttiEsami();
	}
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		migliore = new HashSet<>();
		mediaMigliore = 0.0;
		
		Set<Esame> parziale = new HashSet<>();
		cercaMeglio(parziale, 0, numeroCrediti);
		
		return migliore;	
	}

	private void cercaMeglio(Set<Esame> parziale, int L, int numeroCrediti) {
		int sommaCrediti = sommaCrediti(parziale);
		
		if (sommaCrediti>numeroCrediti) {
			return;
		}
		if (sommaCrediti==numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if (mediaVoti>mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);
			}
			return;
		}
		if (L==allEsami.size()) {
			return;
		}
		
		// provo ad aggiungere il prossimo elemento
		parziale.add(allEsami.get(L));
		cercaMeglio(parziale,L+1,numeroCrediti);
		parziale.remove(allEsami.get(L));
		
		// provo a non aggiungere il prossimo elemento
		cercaMeglio(parziale,L+1,numeroCrediti);
		
	}
	
	private void cerca(Set<Esame> parziale, int L, int numeroCrediti) {
		int sommaCrediti = sommaCrediti(parziale);
		
		if (sommaCrediti>numeroCrediti) {
			return;
		}
		if (sommaCrediti==numeroCrediti) {
			double mediaVoti = calcolaMedia(parziale);
			if (mediaVoti>mediaMigliore) {
				mediaMigliore = mediaVoti;
				migliore = new HashSet<>(parziale);
			}
			return;
		}
		if (L==allEsami.size()) {
			return;
		}
		for (Esame e : allEsami) {
			if (!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale,L+1,numeroCrediti);
				parziale.remove(e);
			}
		}
	}

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
