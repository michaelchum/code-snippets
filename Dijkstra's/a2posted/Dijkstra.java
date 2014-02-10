// Michael Ho 260532097

package a2posted;

import java.util.HashMap;
import java.util.HashSet;

public class Dijkstra {

	private IndexedHeap  pq;	
	private static int edgeCount = 0;               //  Use this to give names to the edges.										
	private HashMap<String,Edge>  edges = new HashMap<String,Edge>();

	private HashMap<String,String>   parent;
	private HashMap<String,Double>   dist;  //  This is variable "d" in lecture notes
	private String 					 startingVertex;	
	
	HashSet<String>  setS      ;
	HashSet<String>  setVminusS;

	public Dijkstra(){
		pq    		= new IndexedHeap()  ;		
		setS        = new HashSet<String>();
		setVminusS  = new HashSet<String>();		
		parent  = new HashMap<String,String>();
		dist 	= new HashMap<String,Double>();
	}
	
	/*
	 * Run Dijkstra's algorithm from a vertex whose name is given by the string s.
	 */
	
	public void dijkstraVertices(Graph graph, String s){
		
		//  temporary variables
		
		String u;	
		double  distToU,
				costUV;		
		
		HashMap<String,Double>    uAdjList;		
		initialize(graph,s);
		
		parent.put( s, null );
		pq.add(s, 0.0);   // shortest path from s to s is 0.
		this.startingVertex = s;

		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		while (!pq.isEmpty()){
			u = pq.nameOfMin(); // Get the closest vertex to S in pq
			setS.add(u); // Add the u to the set S
			for (String v : graph.getAdjList().get(u).keySet()) { // For every vertex v neighbor to vertex u
				if (setVminusS.contains(v) && !setS.contains(v)){ // Check if vertex v is in the set V / s and not S
					if(!pq.contains(v)) pq.add(v); // Check if pq contains v if not, add it to pq
					costUV = graph.getAdjList().get(u).get(v); // Get the cost from u to v
					distToU = dist.get(u); // Get the the minimum distance from s to u
					double distToV = distToU + costUV; // Compute the distance from s to v
					if (distToV < dist.get(v)){ // If the newly computed distance from s to v is smaller than the one in the hashmap
						parent.put(v,u); // Update the parent of v
						dist.put(v, distToV); // Update the new minimum distance from s to v
						pq.changePriority(v, distToV); // Change the minimum distance from s to v in pq
					}
				}
			}
			setVminusS.remove(u); // Remove u from set V / S
			setS.add(u); // Add u to set S if not already added
			pq.removeMin(); // Remove u from pq
		}
		//  --------- END:  ADD YOUR CODE HERE  -----------------------
	}
	
	
	public void dijkstraEdges(Graph graph, String startingVertex){

		//  Makes sets of the names of vertices,  rather than vertices themselves.
		//  (Could have done it either way.)
		
		//  temporary variables
		
		Edge e;
		String u,v;
		double tmpDistToV;
		
		initialize(graph, startingVertex);
		
		//  --------- BEGIN: ADD YOUR CODE HERE  -----------------------
		u = startingVertex; // Get the starting vertex
		setS.add(u); // Add the starting vertex to the set S
		pqAddEdgesFrom(graph, u); // Add all the edges connected to starting vertex
		while (!pq.isEmpty()) {
			e = edges.get(pq.nameOfMin()); // Get the shortest edge out of pq
			v = e.getEndVertex(); // Get the end point vertex v connected to this minimum edge
			if (setVminusS.contains(v) && !setS.contains(v)){ // Check if the end point vertex v is in set V / S and not in set S
				parent.put(v,e.getStartVertex()); // Set the starting vertex of minimum edge as the parent of v
				tmpDistToV = dist.get(u) + pq.getMinPriority(); // Computer the distance from s to v
				if (tmpDistToV < dist.get(v)) dist.put(v,tmpDistToV); // Check if distance from s to v is smaller than previously set one
				setVminusS.remove(v); // Remove v from set V / S
				setS.add(v); // Add v to set S
				pqAddEdgesFrom(graph,v); // Add all edges connected to v to the pq
			}
			edges.remove(e); // Remove the edge from hashmap
			pq.removeMin(); // Remove the minimum edge
		}
		//  --------- END:  ADD YOUR CODE HERE  -----------------------

	}
	
	/*
	 *   This initialization code is common to both of the methods that you need to implement so
	 *   I just factored it out.
	 */

	private void initialize(Graph graph, String startingVertex){
		//  initialization of sets V and VminusS,  dist, parent variables
		//

		for (String v : graph.getVertices()){
			setVminusS.add( v );
			dist.put(v, Double.POSITIVE_INFINITY);
			parent.put(v, null);
		}
		this.startingVertex = startingVertex;

		//   Transfer the starting vertex from VminusS to S and  

		setVminusS.remove(startingVertex);
		setS.add(startingVertex);
		dist.put(startingVertex, 0.0);
		parent.put(startingVertex, null);
	}

    /*  
	 *  helper method for dijkstraEdges:   Whenever we move a vertex u from V\S to S,  
	 *  add all edges (u,v) in E to the priority queue of edges.
	 *  
	 *  For each edge (u,v), if this edge gave a shorter total distance to v than any
	 *  previous paths that terminate at v,  then this edge will be removed from the priority
	 *  queue before these other vertices. 
	 *  
	 */
	
	public void pqAddEdgesFrom(Graph g, String u){
		double distToU = dist.get(u); 
		for (String v : g.getAdjList().get(u).keySet()  ){  //  all edges of form (u, v) 
			
			edgeCount++;
			Edge e = new Edge( edgeCount, u, v );
			edges.put( e.getName(), e );
			pq.add( e.getName() ,  distToU + g.getAdjList().get(u).get(v) );
		}
	}

	// -------------------------------------------------------------------------------------------
	
	public String toString(){
		String s = "";
		s += "\nRan Dijkstra from vertex " + startingVertex + "\n";
		for (String v : parent.keySet()){
			s += v + "'s parent is " +   parent.get(v) ;
			s += "   and pathlength is "  + dist.get(v) + "\n" ;
		}
		return s;
	}

	//  This class is used only to keep track of edges in the priority queue. 
	
	private class Edge{
		
		private String edgeName;
		private String u, v;
		
		Edge(int i, String u, String v){
			this.edgeName = "e_" + Integer.toString(i);
			this.u = u;
			this.v = v;
		}
		
		public String getName(){
			return edgeName;
		}
		
		String getStartVertex(){
			return u;
		}

		String getEndVertex(){
			return v;
		}
		
		public String toString(){
			return 	edgeName + " : " + u + " " + v;
		}
	}
	

}
