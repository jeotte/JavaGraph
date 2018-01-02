package com.jessica.main;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jessica.config.GraphConfig;
import com.jessica.graph.GraphManagement;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

public class MainDemo {

	private static final String SEPARATOR = "----------------------------------------------------------";

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GraphConfig.class);
		GraphManagement graphMgmt = context.getBean(GraphManagement.class);

		System.out.println(SEPARATOR);
		System.out.println("1. Starting with a blank Graph");
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("2. Adding vertex V1 and V2 to the graph");
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		graphMgmt.addVertex(vertex1);
		graphMgmt.addVertex(vertex2);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("3. Adding an edge between V1 and V2");
		Edge edge1to2 = new Edge(vertex1, vertex2);
		graphMgmt.addEdge(edge1to2);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("4. Adding an edge between V3 and V4 (which also adds those vertices)");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");
		Edge edge3to4 = new Edge(vertex3, vertex4, 5, "edge v3 to v4");
		graphMgmt.addEdge(edge3to4);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("5. Adding an edge from V2 to V3");
		Edge edge2to3 = new Edge(vertex2, vertex3, 4);
		graphMgmt.addEdge(edge2to3);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("6. Adding a few more edges...");

		Vertex vertex5 = new Vertex("V5");
		Vertex vertex6 = new Vertex("V6");
		Vertex vertex7 = new Vertex("V7");
		Vertex vertex8 = new Vertex("V8");
		Vertex vertex9 = new Vertex("V9");

		Edge edge4to1 = new Edge(vertex4, vertex1, 3, "edge v4 to v1");
		Edge edge1to3 = new Edge(vertex1, vertex3, 2, "edge v1 to v3");
		Edge edge2to5 = new Edge(vertex2, vertex5, 3, "edge v2 to v5");
		Edge edge5to6 = new Edge(vertex5, vertex6, 3, "edge v5 to v6");
		Edge edge5to7 = new Edge(vertex5, vertex7, 6, "edge v5 to v7");
		Edge edge7to5 = new Edge(vertex7, vertex5, 1, "edge v7 to v5");
		Edge edge8to7 = new Edge(vertex8, vertex7, 5, "edge v8 to v7");
		Edge edge3to7 = new Edge(vertex3, vertex7, 1, "edge v3 to v7");
		Edge edge2to7 = new Edge(vertex2, vertex7, 5, "edge v2 to v7");
		Edge edge7to9 = new Edge(vertex7, vertex9, 4, "edge v7 to v9");

		graphMgmt.addEdge(edge4to1);
		graphMgmt.addEdge(edge1to3);
		graphMgmt.addEdge(edge2to5);
		graphMgmt.addEdge(edge5to6);
		graphMgmt.addEdge(edge5to7);
		graphMgmt.addEdge(edge7to5);
		graphMgmt.addEdge(edge8to7);
		graphMgmt.addEdge(edge3to7);
		graphMgmt.addEdge(edge2to7);
		graphMgmt.addEdge(edge7to9);

		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("7. Removing the edge from V2 to V7");
		graphMgmt.removeEdge(edge2to7);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("8. Removing the vertex V9");
		graphMgmt.removeVertex(vertex9);
		System.out.println(graphMgmt.getGraph());

		System.out.println(SEPARATOR);
		System.out.println("9. Displaying the edges for vertex V3\n");
		List<Edge> v3Edges = graphMgmt.getGraph().getVertices().get(vertex3.getLabel()).getAllEdges();
		List<Edge> sortedEdges = v3Edges.stream().sorted((o1, o2) -> {
			int compareVal = o1.getFromVertex().getLabel().compareTo(o2.getFromVertex().getLabel());
			if (compareVal == 0) {
				compareVal = o1.getToVertex().getLabel().compareTo(o2.getToVertex().getLabel());
			}
			return compareVal;
		}).collect(Collectors.toList());
		for (Edge edge : sortedEdges) {
			System.out.println(edge);
		}

		System.out.println();
		System.out.println(SEPARATOR);
		System.out.println("10. Finding the shortest distance between V1 and V7\n");
		Integer distance = graphMgmt.getShortestDistance(vertex1, vertex7);
		System.out.println("Distance: " + distance);

		System.out.println();
		System.out.println(SEPARATOR);
		System.out.println("11. Finding the shortest path between V1 and V7\n");
		List<Vertex> path = graphMgmt.getShortestPath(vertex1, vertex7);
		System.out.println("Path: " + path);

		System.out.println();
		System.out.println(SEPARATOR);
		System.out.println("12. Saving the graph to file: Graph2File");
		System.out.println("\nThe graph before saving: ");
		System.out.println(graphMgmt.getGraph());
		graphMgmt.saveGraphToFile("Graph2File");

		System.out.println(SEPARATOR);
		System.out.println("13. Reading the graph from the file just created, Graph2File");
		Graph readGraph = graphMgmt.readGraphFromFile("Graph2File");
		System.out.println("\nThe graph from the file: ");
		System.out.println(readGraph);

	}
}
