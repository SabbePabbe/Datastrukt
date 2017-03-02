
import sun.awt.image.ImageWatched;

import java.util.*;

public class DirectedGraph<E extends Edge> {

	private ArrayList<LinkedList<E>> nodes;
	private int noOfNodes;



	/**
	 * Intializes a graph with noOfNodes nodes and 0 edges.
	 * Implementation uses a LinkedList using an ArrayList (don't use to big of a graph!)
	 * @param noOfNodes
	 */
	public DirectedGraph(int noOfNodes) {
		this.noOfNodes = noOfNodes;
		nodes = new ArrayList<>(noOfNodes);

		for (int i = 0;i < noOfNodes; i++ ) {
			nodes.add(new LinkedList<>());
		}
	}

	/**
	 * Adds the edge to the (list) start node (e.from) index in the LinkedList
	 * @param e
	 */
	public void addEdge(E e) {
		nodes.get(e.from).add(e);
	}

	/**
	 * Finds the shortest path starting in from and ending in to using an implementation of the Dijkstra Algorithm.
	 * @param from
	 * @param to
	 * @return The found path as an iterator (over an ArrayList)
	 */
	public Iterator<E> shortestPath(int from, int to) {

		//keeps track of which nodes have been visited
		boolean[] nodeVisited = new boolean[noOfNodes];

		//initialize PQ with Comparator (CompDijkstraPath)
		PriorityQueue<DijkstraQueueElement> dijkstraPQ = new PriorityQueue<DijkstraQueueElement>(new CompDijkstraPath());

		//custom constructor for first element
		dijkstraPQ.add(new DijkstraQueueElement(from));

		DijkstraQueueElement element;

		while(dijkstraPQ.size() > 0){

			element = dijkstraPQ.poll(); //(node, cost, path) = first element of pq
			int node = element.getNode();
			if(!nodeVisited[node]){
				if(node == to){
					//goal reached, return path
					return element.getPath().iterator();
				} else {
					nodeVisited[node] = true;
					//for every neighbour of our node:
					for (E e:nodes.get(node)) {
						if (!nodeVisited[e.to]){
							//add a new dijkstra element to the pq
							dijkstraPQ.add(new DijkstraQueueElement(e,
									element.getCost() + e.getWeight(),
									element.getPath()));
						}
					}
				}
			}
		}
		return null; //could not find a path, graph is not a connected graph
	}

	/**
	 * Our wrapper element containing a node, the cost of the traversed path so far, and the path itself
	 */
	private class DijkstraQueueElement {
		private int node;
		private double cost;
		private ArrayList<E> path;

		/**
		 * Standard constructor
		 * @param edge The edge we used to get here
		 * @param cost The cost of the total path so far (including that of edge)
		 * @param path The path so far (not including edge)
		 */
		public DijkstraQueueElement(E edge, double cost, ArrayList<E> path){
			this.node = edge.to;
			this.cost = cost;
			this.path = new ArrayList<E>(path); //create new path for each element
			this.path.add(edge);
		}

		/**
		 * Custom constructor for first element, does not have cost or path yet
		 * @param node Start node
		 */
		public DijkstraQueueElement(int node){
			this.node = node;
			cost = 0;
			path = new ArrayList<>(noOfNodes);
		}

		public int getNode(){
			return node;
		}

		public double getCost() {
			return cost;
		}

		public ArrayList<E> getPath(){
			return path;
		}
	}

	/**
	 * We chose to implement a comparator for paths to be used in a priority queue for Dijkstra's algorithm.
	 */
	private class CompDijkstraPath implements Comparator<DijkstraQueueElement> {
		@Override
		public int compare(DijkstraQueueElement o1, DijkstraQueueElement o2) {
			return  (int) Math.signum((o1.getCost() - o2.getCost()));
		}
	}

	/**
	 * We chose to implement a comparator for edges to be used in a priority queue for Kruskal's algorithm.
	 */
	private class CompKruskalEdge implements Comparator<E>{
		@Override
		public int compare(E o1, E o2) {
			return (int) Math.signum(o1.getWeight() - o2.getWeight());

		}
	}


	/**
	 * Our implementation of a Kruskal MST
	 * @return Iterator over a LinkedList containing all edges in the MST
	 */
	public Iterator<E> minimumSpanningTree() {

		if (noOfNodes == 0){
			return null;
		}

		ArrayList<LinkedList<E>> cc = new ArrayList<>(noOfNodes);

		//initialize the list of lists
		for(int i = 0; i < noOfNodes; i++){
			cc.add(new LinkedList<>());
		}

		PriorityQueue<E> pq = new PriorityQueue<>(new CompKruskalEdge());

		//add all edges to the priority queue
		for (LinkedList<E> ll : nodes){
			pq.addAll(ll);
		}

		int noOfListsLeft = noOfNodes;

		//while there are edges left and the MST is incomplete
		while (!pq.isEmpty() && (noOfListsLeft > 1)){
			E e = pq.poll();

			//if not same list
			if (!(cc.get(e.from) == cc.get(e.to))){
				if((cc.get(e.from).size() > cc.get(e.to).size())){
					//move every edge from smaller to bigger list
					for(E edge : cc.get(e.to) ) {
						cc.get(e.from).add(edge);
						//iterate over edges and move pointers
						cc.set(edge.to, cc.get(e.from));
						cc.set(edge.from, cc.get(e.from));
					}
					cc.set(e.to,cc.get(e.from));

				} else {
					//same as above except for when the other list is bigger (or they are same size)
					for(E edge : cc.get(e.from) ) {
						cc.get(e.to).add(edge);
						//iterate over edges and move pointers
						cc.set(edge.to, cc.get(e.to));
						cc.set(edge.from, cc.get(e.to));

					}
					cc.set(e.from,cc.get(e.to));

				}
				//decrease counter of lists (unconnected nodes)
				noOfListsLeft--;
				//add current edge to the list
				cc.get(e.to).add(e);
			}
		}
		//Does not matter which index in cc, they all refer to the same, so just use the first
		return cc.get(0).iterator();
	}

}
  
