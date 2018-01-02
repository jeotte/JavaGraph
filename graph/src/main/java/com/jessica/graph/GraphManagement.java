package com.jessica.graph;

import java.util.List;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

public interface GraphManagement {

	/**
	 * Sets the in-memory graph
	 * 
	 * @param graph
	 *            The graph to set
	 */
	public void setGraph(Graph graph);

	/**
	 * Get the in-memory graph. If the graph is null, returns a new Graph instance
	 * 
	 * @returnThe in-memory graph
	 */
	public Graph getGraph();

	/**
	 * Sets the in-memory graph to null
	 */
	public void clear();

	/**
	 * Performs a depth first search of the graph, given a starting vertex.
	 * 
	 * @param vertex
	 *            The starting vertex for the search
	 * @return The vertices that were visited
	 */
	public List<Vertex> depthFirstSeach(Vertex vertex) throws GraphException;

	/**
	 * Saves the graph to a specified file.
	 * 
	 * @param fileName
	 *            The name of the file
	 */
	public void saveGraphToFile(String fileName) throws GraphException;

	/**
	 * Reads the specified file and returns the graph representation.
	 * 
	 * @param fileName
	 *            The file to read
	 * @return The graph that was stored in the file
	 */
	public Graph readGraphFromFile(String fileName) throws GraphException;

	/**
	 * Returns all vertices adjacent to the given vertex.
	 * 
	 * @param vertex
	 *            The vertex whose next vertices are desired
	 * @return All adjacent vertices to vertex
	 */
	public List<Vertex> getNextVertices(Vertex vertex) throws GraphException;

	/**
	 * Removes a vertex from the graph, and removes all connecting edges.
	 * 
	 * @param vertex
	 *            The vertex to remove from the graph
	 */
	public void removeVertex(Vertex vertex) throws GraphException;

	/**
	 * Adds a vertex to the graph.
	 * 
	 * @param vertex
	 *            The vertex to add to the graph
	 */
	public void addVertex(Vertex vertex) throws GraphException;

	/**
	 * Removes the edge from the graph, and removes the edge from its from and to vertices.
	 * 
	 * @param edge
	 *            The edge to remove from the graph
	 */
	public void removeEdge(Edge edge) throws GraphException;

	/**
	 * Adds an edge to the graph.
	 * 
	 * @param edge
	 *            The edge to add to the graph
	 */
	public void addEdge(Edge edge) throws GraphException;

	/**
	 * Gets the shortest path from the source vertex to the destination vertex.
	 * 
	 * @param source
	 *            The vertex to start the search from
	 * @param destination
	 *            The vertex to end the search at
	 * 
	 * @return The vertices that must be visited to make up the path from the source to the destination, in the order
	 *         that they must be visited
	 */
	public List<Vertex> getShortestPath(Vertex source, Vertex destination) throws GraphException;

	/**
	 * Gets the shortest distance from the source vertex to the destination vertex.
	 * 
	 * @param source
	 *            The vertex to start the search from
	 * @param destination
	 *            The vertex to end the search at
	 * @return The total distance along the shortest path from the source vertex to the destination vertex
	 */
	public Integer getShortestDistance(Vertex source, Vertex destination) throws GraphException;
}
