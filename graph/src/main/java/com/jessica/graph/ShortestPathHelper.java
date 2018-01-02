package com.jessica.graph;

import java.util.List;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

public interface ShortestPathHelper {

	/**
	 * Given a graph, gets the shortest path between two vertices.
	 * 
	 * @param graph
	 *            The directed graph to run the algorithm on
	 * @param source
	 *            The vertex to start at
	 * @param destination
	 *            The vertex to end at
	 * 
	 * @return A list of vertices, such that position 0 will contain the source vertex, and the last vertex in the list
	 *         will be the destination vertex
	 */
	public List<Vertex> getShortestPath(Graph graph, Vertex source, Vertex destination) throws GraphException;

	/**
	 * Given a graph, gets the shortest distance between two vertices.
	 * 
	 * @param graph
	 *            The graph to run the algorithm on
	 * @param source
	 *            The vertex to start at
	 * @param destination
	 *            The vertex to end at
	 * 
	 * @return The distance from source to destination
	 */
	public Integer getShortestDistance(Graph graph, Vertex source, Vertex destination) throws GraphException;

}
