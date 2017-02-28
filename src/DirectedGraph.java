
import sun.awt.image.ImageWatched;

import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<LinkedList<Edge>> nodes;

	public DirectedGraph(int noOfNodes) {
		nodes = new ArrayList<>(noOfNodes);

	}

	public void addEdge(E e) {

		nodes.get(e.from).add(e);
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

		ArrayList<LinkedList<Edge>> cc = new ArrayList<>(nodes.size());

		PriorityQueue<Edge> pq = new PriorityQueue<>(new CompKruskalEdge()); //TODO make comparator and send it here

		for (LinkedList<Edge> ll : nodes){
			pq.addAll(ll);
		}

		int noOfListsLeft = nodes.size();

		while (!pq.isEmpty() && (noOfListsLeft > 1)){
			Edge e = pq.poll();
			if (!(cc.get(e.from) == cc.get(e.to))){
				if((cc.get(e.from).size() > cc.get(e.to).size())){
					for(Edge edge : cc.get(e.to) ) {
						cc.get(e.from).add(edge);
					}
					cc.set(e.to,cc.get(e.from));

				} else {
					for(Edge edge : cc.get(e.from) ) {
						cc.get(e.to).add(edge);
					}
					cc.set(e.from,cc.get(e.to));
				}
				noOfListsLeft--;
			}
		}

		//TODO return iterator over the edges??
		return null;
	}

}
  
