package com.jessica.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.exception.GraphExceptionMsg;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

@Component
public class DijkstrasShortestPath implements ShortestPathHelper {

	private final String CLASSNAME = this.getClass().getSimpleName();
	private final String DISTANCE = "distance";
	private final String PREVIOUS_VERTEX = "previousVertex";

	@Override
	public List<Vertex> getShortestPath(Graph graph, Vertex source, Vertex destination) throws GraphException {
		// Validate the destination vertex (the graph and source are validated in findMinimumDistance
		if (destination == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_DESTINATION);
		}

		Map<Vertex, Map<String, Object>> distanceAndPath = findMinimumDistance(graph, source);

		List<Vertex> pathOfVertices = new ArrayList<Vertex>();

		// First, let's add the destination
		pathOfVertices.add(0, destination);

		// Get the previous vertex for the destination
		Vertex previousVertex = (Vertex) distanceAndPath.get(destination).get(PREVIOUS_VERTEX);

		// Work backward looking at each previous vertex from destination to source
		while (!previousVertex.equals(source)) {

			// Push the previous vertex onto the path
			// (This will insert the value at the front of the list, and shift the other values to the right)
			pathOfVertices.add(0, previousVertex);

			// Get the next previous vertex
			previousVertex = (Vertex) distanceAndPath.get(previousVertex).get(PREVIOUS_VERTEX);
		}

		// Make sure to push the source onto the path
		pathOfVertices.add(0, source);

		return pathOfVertices;
	}

	public Integer getShortestDistance(Graph graph, Vertex source, Vertex destination) throws GraphException {
		// Validate the destination vertex (the graph and source are validated in findMinimumDistance
		if (destination == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_DESTINATION);
		}

		Map<Vertex, Map<String, Object>> distanceAndPath = findMinimumDistance(graph, source);

		return getDistance(distanceAndPath, destination);
	}

	/**
	 * This is the implementation of Dijkstra's Algorithm. It takes in the directed graph and the source vertex, and
	 * returns a map that contains each vertex (other than the source) and the minimum distance from the source vertex
	 * to that vertex. If the graph or source vertex is null, an error is printed out and null is returned.
	 * 
	 * @param graph
	 *            The directed graph on which to execute this algorithm
	 * @param source
	 *            The vertex that is the source / starting point of the search
	 * @return A map containing each vertex (other than the source), and its distance from the source vertex.
	 */
	private Map<Vertex, Map<String, Object>> findMinimumDistance(Graph graph, Vertex source) throws GraphException {
		HashMap<Vertex, Map<String, Object>> distanceAndPath = new HashMap<>();

		// Validate that the source and graph are non-null
		if (graph == null || source == null) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.NULL_GRAPH_SRC);
		}

		// Verify that the graph has vertices
		if (graph.getVertices() == null || graph.getVertices().isEmpty()) {
			throw new GraphException(CLASSNAME, GraphExceptionMsg.EMPTY_GRAPH);
		}

		// Get the source vertex in the graph
		Vertex sourceVertex = graph.getVertices().get(source.getLabel());

		// Initialize the distance for each vertex (except source) to the max
		// value
		for (Vertex vertex : graph.getVertices().values()) {
			if (!vertex.equals(source)) {
				Map<String, Object> distanceAndPrevVertex = new HashMap<>();
				distanceAndPrevVertex.put(DISTANCE, Integer.MAX_VALUE);
				distanceAndPrevVertex.put(PREVIOUS_VERTEX, null);
				distanceAndPath.put(vertex, distanceAndPrevVertex);
			}
		}

		List<Vertex> verticesToBeProcessed = new ArrayList<Vertex>();
		List<Edge> edgesToTraverse = sourceVertex.getOutgoingEdges();

		// Get each of the next vertices
		// Use the edge to determine the distance from the source (which is
		// currently only one edge length away)
		// Set the previous vertex to the source vertex
		// Add the vertex to the vertices to be processed
		if (edgesToTraverse != null && !edgesToTraverse.isEmpty()) {
			for (Edge edge : edgesToTraverse) {
				Vertex nextVertex = edge.getToVertex();
				distanceAndPath.get(nextVertex).put(DISTANCE, edge.getWeight());
				distanceAndPath.get(nextVertex).put(PREVIOUS_VERTEX, edge.getFromVertex());
				verticesToBeProcessed.add(nextVertex);
			}
		}

		// Loop over the next vertices and do what we just did above for each of
		// them
		while (!verticesToBeProcessed.isEmpty()) {

			Vertex nextVertex = null;
			for (Vertex temp : verticesToBeProcessed) {
				if (nextVertex == null)
					nextVertex = temp;
				else if (getDistance(distanceAndPath, temp) < getDistance(distanceAndPath, nextVertex)) {
					nextVertex = temp;
				}
			}
			verticesToBeProcessed.remove(nextVertex);

			edgesToTraverse = nextVertex.getOutgoingEdges();
			if (edgesToTraverse != null && !edgesToTraverse.isEmpty()) {
				for (Edge edge : edgesToTraverse) {
					Vertex edgeToVertex = edge.getToVertex();

					// We don't want to include the source in our calculations
					if (edgeToVertex.equals(source) || distanceAndPath.get(edgeToVertex) == null) {
						continue;
					}

					// The distance is the distance from the previous vertex +
					// the edge to this vertex
					Integer currentDistance = getDistance(distanceAndPath, nextVertex) + edge.getWeight();

					// If the distance from the source to this vertex is the
					// max, then update the distance
					if (getDistance(distanceAndPath, edgeToVertex).equals(Integer.MAX_VALUE)) {
						distanceAndPath.get(edgeToVertex).put(DISTANCE, currentDistance);
						distanceAndPath.get(edgeToVertex).put(PREVIOUS_VERTEX, nextVertex);
						verticesToBeProcessed.add(edgeToVertex);
					} else {
						// Check if the current distance is less than the
						// distance that was previously calculated
						if (getDistance(distanceAndPath, edgeToVertex) > currentDistance) {
							distanceAndPath.get(edgeToVertex).put(DISTANCE, currentDistance);
							distanceAndPath.get(edgeToVertex).put(PREVIOUS_VERTEX, nextVertex);
						}
					}
				}
			}
		}
		return distanceAndPath;
	}

	private Integer getDistance(Map<Vertex, Map<String, Object>> distanceAndPath, Vertex vertex) {
		return (Integer) distanceAndPath.get(vertex).get(DISTANCE);
	}
}
