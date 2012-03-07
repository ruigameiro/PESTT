package domain.coverage.algorithms;

import java.util.Set;
import java.util.TreeSet;

import adt.graph.Edge;
import adt.graph.Graph;
import adt.graph.Node;
import adt.graph.Path;
import domain.graph.visitors.BreadthFirstGraphVisitor;

public class EdgeCoverage<V extends Comparable<V>> implements ICoverageAlgorithms<V> {
	
	private Graph<V> graph;
	
	public EdgeCoverage(Graph<V> graph) {
		this.graph = graph;
	}

	public Set<Path<V>> getTestRequirements() {
		EdgeCoverageVisitor ecv = new EdgeCoverageVisitor();
		ecv.visitGraph(graph);
		return ecv.getPaths();
	}

	private class EdgeCoverageVisitor extends BreadthFirstGraphVisitor<V> {
		
		private Set<Path<V>> paths;
		private Set<Node<V>> visitedNodes;
		
		public EdgeCoverageVisitor() {
			paths = new TreeSet<Path<V>>();
			visitedNodes = new TreeSet<Node<V>>();
		}
		
		public Set<Path<V>> getPaths() {
			return paths;
		}
		
		@Override
		public void endVisit(Node<V> node) {
			if(!visitedNodes.contains(node)) { // set the id to all paths in the list.
				visitedNodes.add(node); // add the node to be visited.
				Path<V> path = new Path<V>(node); // create a new path with the first node.
				paths.add(path); // add path to the list.
				for(Edge<V> edge : graph.getNodeEdges(node)) { // get the paths of length two.
					path = new Path<V>(node); // create a new path with the first node.
					path.addNode(edge.getEndNode()); //add the second node.
					paths.add(path); // add path to the list.
				}
			}
		}
	}
}