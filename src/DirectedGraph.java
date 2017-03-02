
import sun.awt.image.ImageWatched;

import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<LinkedList<E>> nodes;
	private int noOfNodes;

	public DirectedGraph(int noOfNodes) {
		this.noOfNodes = noOfNodes;
		nodes = new ArrayList<>(noOfNodes);

		for (int i = 0;i < noOfNodes; i++ ) {
			nodes.add(new LinkedList<>());
		}
	}

	public void addEdge(E e) {

		nodes.get(e.from).add(e);
	}

	public Iterator<E> shortestPath(int from, int to) {

		//lista över vilka noder som har blivit besökta
		boolean[] nodeVisited = new boolean[noOfNodes];
		//nodeVisited[from] = true;

		PriorityQueue<DijkstraQueueElement> dijkstraPQ = new PriorityQueue<DijkstraQueueElement>(new CompDijkstraPath());

		//custom konstruktor för första elementet, pga de andra vill lägga till en edge i path
		dijkstraPQ.add(new DijkstraQueueElement(from)); //lägg (startnod, 0, tom väg) i en p-kö

		DijkstraQueueElement element;

		while(dijkstraPQ.size() > 0){

			element = dijkstraPQ.poll(); //(nod, cost, path) = första elementet i p-kön
			int node = element.getNode();
			if(!nodeVisited[node]){
				if(node == to){
					//nodeVisited[node] = true;
					return element.getPath().iterator();

				} else {
					nodeVisited[node] = true;
					//for every neighbour of our node:
					for (E e:nodes.get(node)) {

						if (!nodeVisited[e.to]){

							dijkstraPQ.add(new DijkstraQueueElement(e,
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
		private ArrayList<E> path;

		public DijkstraQueueElement(E edge, int cost, ArrayList<E> path){
			this.node = edge.to;
			this.cost = cost;
			this.path = new ArrayList<E>(path); //create new path for each element
			this.path.add(edge);
		}

		//custom konstruktor för första elementet
		public DijkstraQueueElement(int node){
			this.node = node;
			cost = 0;
			path = new ArrayList<>(noOfNodes);
		}

		public int getNode(){
			return node;
		}

		public int getCost() {
			return cost;
		}

		public ArrayList<E> getPath(){
			return path;
		}
	}

	private class CompDijkstraPath implements Comparator<DijkstraQueueElement> {
		@Override
		public int compare(DijkstraQueueElement o1, DijkstraQueueElement o2) {
			return  (int) Math.signum((o1.getCost() - o2.getCost()));
		}
	}

	private class CompKruskalEdge implements Comparator<E>{
		@Override
		public int compare(E o1, E o2) {
			return (int) Math.signum(o1.getWeight() - o2.getWeight());

		}
	}



	public Iterator<E> minimumSpanningTree() {

		if (noOfNodes == 0){
			return null;
		}

		ArrayList<LinkedList<E>> cc = new ArrayList<>(noOfNodes);

		for(int i = 0; i < noOfNodes; i++){
			cc.add(new LinkedList<>());
		}

		PriorityQueue<E> pq = new PriorityQueue<>(new CompKruskalEdge());

		for (LinkedList<E> ll : nodes){
			pq.addAll(ll);
		}

		int noOfListsLeft = noOfNodes;

		while (!pq.isEmpty() && (noOfListsLeft > 1)){
			E e = pq.poll();

			if (!(cc.get(e.from) == cc.get(e.to))){

				if((cc.get(e.from).size() > cc.get(e.to).size())){
					for(E edge : cc.get(e.to) ) {
						cc.get(e.from).add(edge);
					}
					for (int i = 0; i < cc.size(); i++){
						if ((cc.get(i) == cc.get(e.to)) &&(i!=e.to)){
							cc.set(i, cc.get(e.from));
						}
					}
					cc.set(e.to,cc.get(e.from));

				} else {
					for(E edge : cc.get(e.from) ) {
						cc.get(e.to).add(edge);
					}
					for (int i = 0; i < cc.size(); i++){
						if ((cc.get(i) == cc.get(e.from)) && (i!=e.from)){
							cc.set(i, cc.get(e.to));
						}
					}
					cc.set(e.from,cc.get(e.to));

				}
				noOfListsLeft--;
				cc.get(e.to).add(e);
			}
		}
		//Does not matter which index in cc, they all refer to the same, so just use the first
		return cc.get(0).iterator();
	}

}
  
