
import sun.awt.image.ImageWatched;

import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<LinkedList<Edge>> nodes;
	private int noOfNodes;

	public DirectedGraph(int noOfNodes) {
		this.noOfNodes = noOfNodes;
		nodes = new ArrayList<>(noOfNodes);

	}

	public void addEdge(E e) {

		nodes.get(e.from).add(e);
	}

	public Iterator<E> shortestPath(int from, int to) {
		//TODO Dikstra cycle through connected nodes and build ways, see wich one is shortest with returnval (just do recursive return?)





		//lista med alla besökta noder, kollas av vägarna så man inte korsar varandra

		boolean[] nodeVisited = new boolean[noOfNodes];


		PriorityQueue<DijkstraQueueElement> dijkstraPQ = new PriorityQueue<DijkstraQueueElement>();

		dijkstraPQ.add(new DijkstraQueueElement(from, 0, new ArrayList<>())); //lägg (startnod, 0, tom väg) i en p-kö

		DijkstraQueueElement element;
		while(dijkstraPQ.size() > 0){
			element = dijkstraPQ.poll(); //(nod, cost, path) = första elementet i p-kön
			int node = element.getNode();
			if(!nodeVisited[node]){
				if(node == to){
					return null; //return path
				} else {
					nodeVisited[node] = true;
					//for every granne
					for (Edge e:nodes.get(node)) {
						if (!nodeVisited[e.to]){
							dijkstraPQ.add(new DijkstraQueueElement(e.to,
									element.getCost() + (int) e.getWeight(),
									element.getPath()));
						}
					}
				}
			}

		}

		/*
		//variant en till en

		while kön inte är tom

			if nod ej är besökt
				if nod är slutpunkt returnera path
				else
				markera nod besökt
				for every v on EL(nod)
					if v ej är besökt
					lägg in nytt köelement för v i p-kön


		 */

		return null;
	}


	private class DijkstraQueueElement {

		private int node;
		private int cost;
		private ArrayList<Integer> path;

		public DijkstraQueueElement(int node, int cost, ArrayList<Integer> path){
			this.node = node;
			this.cost = cost;
			this.path = path;

			path.add(node); //TODO vill vi göra detta här?
		}

		public int getNode(){
			return node;
		}

		public int getCost() {
			return cost;
		}

		public ArrayList<Integer> getPath(){
			return path;
		}
	}

		
	public Iterator<E> minimumSpanningTree() {

		ArrayList<LinkedList<Edge>> cc = new ArrayList<>(nodes.size());

		PriorityQueue<Edge> pq = new PriorityQueue<>(new CompKruskalEdge());

		for (LinkedList<Edge> ll : nodes){
			pq.addAll(ll);
		}

		int noOfListsLeft = noOfNodes;

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
  
