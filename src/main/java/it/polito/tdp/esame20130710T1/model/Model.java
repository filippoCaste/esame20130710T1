package it.polito.tdp.esame20130710T1.model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.esame20130710T1.dao.ParoleDAO;

public class Model {
	
	private Map<String, Parola> paroleMap;
	private ParoleDAO dao;
	private Graph<Parola, DefaultEdge> graph;
	
	public Model() {
		this.dao = new ParoleDAO();
		this.paroleMap = new HashMap<String, Parola>();
	}
	
	public void createGraph(int n) {
		this.dao.loadWordsOf(n, paroleMap);
		
		this.graph = new SimpleGraph<Parola, DefaultEdge>(DefaultEdge.class);
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.graph, this.paroleMap.values());
		
		// aggiunta archi: collego se cambia solo una lettera
		for(String k : this.paroleMap.keySet()) {
			Parola p1 = this.paroleMap.get(k);
			for(String i : this.paroleMap.keySet()) {
				Parola p2 = this.paroleMap.get(i);
				int count = countcharmatch(p1,p2);
				if(count == (p2.getNome().length()-1) && !p1.equals(p2) && !this.graph.containsEdge(p2, p1) && !this.graph.containsEdge(p1,p2)) {
					this.graph.addEdge(p1, p2);
				}
			}
		}
	}
	
	private int countcharmatch(Parola p1, Parola p2) {
		int count = 0;
		String n1 = p1.getNome();
		String n2 = p2.getNome();
		for(int i = 0; i<n1.length(); i++) {
			if(n1.charAt(i)==n2.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	public String printGraph() {
		String out = "Il grafo ha:\n\t- Vertici: " + this.graph.vertexSet().size() + ";\n\t- Archi: "
				+ this.graph.edgeSet().size();
		
		return out;
	}

	public boolean wordInDict(String p1, String p2) {
		if(this.paroleMap.get(p1)!= null && this.paroleMap.get(p2)!=null)
			return true;
		else
			return false;
	}
	
	public List<String> winTheMatch(String n1, String n2) {
		
		
		
		List<String> res = new ArrayList<>();
		Parola p1 = this.paroleMap.get(n1);
		Parola p2 = this.paroleMap.get(n2);
		BreadthFirstIterator<Parola, DefaultEdge> it = new BreadthFirstIterator<Parola, DefaultEdge>(this.graph, p1);
		
		BFSShortestPath<Parola, DefaultEdge> bfs = new BFSShortestPath<>(this.graph);
		System.out.println("\nImplementazione con BFSShortestPath:\n"+ bfs.getPaths(p1).getPath(p2));
		
		DijkstraShortestPath<Parola, DefaultEdge> dijk = new DijkstraShortestPath<>(graph);
		System.out.println("\nImplementazione con Dijkstra:\n" + dijk.getPath(p1, p2));
		
		// guardo in ampiezza se trovo che sono collegati
		boolean trovato = false;
		while(it.hasNext()) {
			Parola attr = it.next();
			if(attr.equals(p2)) {
				trovato = true;
				break;
			}
		}
		
		// se sono collegati allora risalgo al percorso
		if(trovato) { 
			res.add(n2);
			Parola step = it.getParent(p2);
			while(!step.equals(p1)) {
				res.add(0,step.getNome()); // aggiungo in testa
				step = it.getParent(step);
			}
			// aggiungo p1
			res.add(0,p1.getNome());
		} else {
			res.add("Percorso non trovato");
		}
		return res;
	}
}