package com.jessica.graph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.exception.GraphExceptionMsg;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

@Component
public class GraphManagementImpl implements GraphManagement {

	private Graph graph;
	private transient ShortestPathHelper shortestPathHelper;
	private final String CLASSNAME = this.getClass().getSimpleName();

	@Autowired
	public void setShortestPathHelper(ShortestPathHelper shortestPathHelper) {
		this.shortestPathHelper = shortestPathHelper;
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Graph getGraph() {
		if (graph == null) {
			graph = new Graph();
		}
		return graph;
	}

	@Override
	public List<Vertex> depthFirstSeach(Vertex vertex) throws GraphException {
		List<Vertex> vistedList = new ArrayList<Vertex>();

		depthFirstSearch(vertex, vistedList);

		return vistedList;
	}

	/**
	 * Performs a depth first search of the graph, given a starting vertex and a visited list of vertices.
	 * 
	 * @param vertex
	 *            The starting vertex for the search
	 * @param visitedList
	 *            The vertices that were visited
	 */
	private void depthFirstSearch(Vertex vertex, List<Vertex> visitedList) throws GraphException {

		// Add the vertex to the list of visited vertices
		visitedList.add(vertex);

		// Traverse the graph by getting the next vertex that this vertex can
		// get to, and if it hasn't been visited
		// add it to the list and perform the search on that next vertex
		List<Vertex> nextVertices = getNextVertices(vertex);
		if (nextVertices != null && !nextVertices.isEmpty()) {
			for (Vertex nextVertex : getNextVertices(vertex)) {
				if (!visitedList.contains(nextVertex)) {
					depthFirstSearch(nextVertex, visitedList);
				}
			}
		}
	}

	@Override
	public void saveGraphToFile(String fileName) throws GraphException {
		// Validate that the graph is not empty
		if (getGraph().getVertices() == null || getGraph().getVertices().isEmpty()) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.EMPTY_GRAPH);
		}

		// Validate the file name
		if (fileName == null || fileName.isEmpty()) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_FILENAME);
		}

		// Write the graph to the specified file
		try (ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
			objectOutStream.writeObject(graph);
			objectOutStream.flush();
		} catch (FileNotFoundException exception) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.FILE_NOT_FOUND, exception);
		} catch (IOException exception) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.FILE_SAVE_ERROR, exception);
		}
	}

	@Override
	public Graph readGraphFromFile(String fileName) throws GraphException {
		// Validate the file name is not null
		if (fileName == null || fileName.isEmpty()) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_FILENAME);
		}

		// Read the graph from the file
		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
			Object objectFromFile = objectInputStream.readObject();

			if (objectFromFile == null) {
				throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_GRAPH);
			} else if (!(objectFromFile instanceof Graph)) {
				throw new GraphException(CLASSNAME, GraphExceptionMsg.UNEXPECTED_FILE_CONTENTS);
			} else {
				return (Graph) objectFromFile;
			}
		} catch (Exception exception) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.FILE_READ_ERROR, exception);
		}
	}

	@Override
	public List<Vertex> getNextVertices(Vertex vertex) throws GraphException {
		// Validate that the vertex is non-null
		if (vertex == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_VERTEX);
		}

		// Validate that the graph contains this vertex
		if (!getGraph().getVertices().containsKey(vertex.getLabel())) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.INVALID_VERTEX);
		}

		// If the vertex has no outgoing edges, return null
		if (vertex.getOutgoingEdges() == null || vertex.getOutgoingEdges().isEmpty()) {
			return null;
		}

		// Find all adjacent vertices using the outgoing edges for the vertex
		List<Vertex> adjacentVertices = new ArrayList<Vertex>();
		for (Edge edge : vertex.getOutgoingEdges()) {
			adjacentVertices.add(edge.getToVertex());
		}

		return adjacentVertices;
	}

	@Override
	public void removeVertex(Vertex vertex) throws GraphException {
		// Validate that the vertex is non-null
		if (vertex == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_VERTEX);
		}

		// Validate that the vertex doesn't already exist in the graph
		if (!getGraph().getVertices().containsValue(vertex)) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.INVALID_VERTEX);
		}

		// Remove the vertex from the graph
		getGraph().getVertices().remove(vertex.getLabel());

		// Remove all edges associated with this vertex
		List<Edge> allEdges = vertex.getAllEdges();
		if (allEdges != null && !allEdges.isEmpty()) {
			while (allEdges.size() > 0) {
				removeEdge(allEdges.remove(0));
			}
		}
	}

	@Override
	public void addVertex(Vertex vertex) throws GraphException {
		// Validate that the vertex is non-null
		if (vertex == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_VERTEX);
		}

		// Validate that the vertex doesn't already exist in the graph
		if (getGraph().getVertices().containsValue(vertex)) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.PREEXISTING_VERTEX);
		}

		// Add the vertex to the graph
		getGraph().getVertices().put(vertex.getLabel(), vertex);
	}

	@Override
	public void removeEdge(Edge edge) throws GraphException {
		// Validate that the edge is non-null
		if (edge == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_EDGE);
		}

		// Remove the edge from the from and to vertices
		edge.getFromVertex().removeEdge(edge);
		edge.getToVertex().removeEdge(edge);

		// Remove the edge from the graph
		getGraph().getEdges().remove(edge.getLabel());
	}

	@Override
	public void addEdge(Edge edge) throws GraphException {
		// Validate that the edge is non-null
		if (edge == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_EDGE);
		}

		// Validate that the edge has both a from and to vertex
		if (edge.getFromVertex() == null || edge.getToVertex() == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.EDGE_MISSING_VERTEX);
		}

		// Validate that the edge doesn't already exist in the graph
		if (getGraph().getEdges().containsValue(edge)) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.PREEXISTING_EDGE);
		}

		// Get the from and to vertices from the edge
		Vertex fromVertex = edge.getFromVertex();
		Vertex toVertex = edge.getToVertex();

		// If the from vertex does not yet exist, add it
		if (!getGraph().getVertices().containsKey(fromVertex.getLabel())) {
			addVertex(fromVertex);
		}

		// If the to vertex does not yet exist, add it
		if (!getGraph().getVertices().containsKey(toVertex.getLabel())) {
			addVertex(toVertex);
		}

		// Add the edge to the appropriate list for each of the vertices
		fromVertex.addOutgoingEdge(edge);
		toVertex.addIncomingEdge(edge);

		// Add the edge to the map of edges
		getGraph().getEdges().put(edge.getLabel(), edge);
	}

	@Override
	public void clear() {
		graph = null;
	}

	@Override
	public List<Vertex> getShortestPath(Vertex source, Vertex destination) throws GraphException {
		return shortestPathHelper.getShortestPath(getGraph(), source, destination);
	}

	@Override
	public Integer getShortestDistance(Vertex source, Vertex destination) throws GraphException {
		return shortestPathHelper.getShortestDistance(getGraph(), source, destination);
	}

}
