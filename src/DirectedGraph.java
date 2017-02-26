
import java.util.*;

public class DirectedGraph<E extends Edge> {


	public DirectedGraph(int noOfNodes) {
		//TODO make new pool of nodes
	}

	public void addEdge(E e) {
		//TODO add edge
	}

	public Iterator<E> shortestPath(int from, int to) {
		//TODO Dikstra cycle through connected nodes and build ways, see wich one is shortest with returnval (just do recursive return?)
		return null;

		/*

		pseudocode dijkstra(G: graph (V,E))
			ssf : array(1..n) of shortest paths so far
			v, w, u: nodes
			S = {1} // the start node
			while S ≠ V loop
				choose the edge (u, w) with
					minimum cost s.t. u∈S and w∈V-S
				add (w) to S (and remove from V-S)
				for all v on EL(w) loop
					if it is shorter to go by way of v
						update ssf(v) and p(v)
			end loop
		end dijkstra


		//variant en till en
		lägg (startnod, 0, tom väg) i en p-kö
		while kön inte är tom
			(nod, cost, path) = första elementet i p-kön
			if nod ej är besökt
				if nod är slutpunkt returnera path
				else
				markera nod besökt
				for every v on EL(nod)
					if v ej är besökt
					lägg in nytt köelement
						för v i p-kön
		 */
	}
		
	public Iterator<E> minimumSpanningTree() {
		//TODO use kruskal

		/*
		(Pseudocoden från ”Refined attempt two”)
0 			skapa ett fält cc som för varje nod
				innehåller en egen tom lista (som skall
				innehålla bågar så småningom)
				(dvs varje nod är i en egen komponent)
1 			Lägg in alla bågar i en prioritetskö
2 			Så länge pq, ej är tom && |cc| > 1
3 				hämta e = (from, to, weight) från kön
5 				om from och to inte refererar till
					samma lista i cc
6 				flytta över alla elementen från den
					kortare listan till den andra och se till
					att alla berörda noder i cc refererar
					till den påfyllda listan
8 				lägg slutligen e i den påfyllda listan
		 */


		return null;
	}

}
  
