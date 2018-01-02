package com.jessica.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Graph implements Serializable {

	private static final long serialVersionUID = -4074830786187544498L;

	private Map<String, Vertex> vertices;
	private Map<String, Edge> edges;

	public Graph() {
		vertices = new ConcurrentHashMap<String, Vertex>();
		edges = new ConcurrentHashMap<String, Edge>();
	}

	/**
	 * 
	 * @return Each vertex in the graph
	 */
	public Map<String, Vertex> getVertices() {
		return vertices;
	}

	/**
	 * 
	 * @param vertices
	 */
	public void setVertices(Map<String, Vertex> vertices) {
		this.vertices = vertices;
	}

	/**
	 * 
	 * @return Each edge in the graph
	 */
	public Map<String, Edge> getEdges() {
		return edges;
	}

	/**
	 * 
	 * @param edges
	 */
	public void setEdges(Map<String, Edge> edges) {
		this.edges = edges;
	}

	/**
	 * Returns a string representation of the graph.
	 * 
	 * @return A string representing the edges and vertices of the graph
	 */
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		Boolean hasEdges = edges != null && !edges.isEmpty();
		Boolean hasVertices = vertices != null && !vertices.isEmpty();

		strBuilder.append("\nGraph Summary: ");
		strBuilder.append((hasEdges ? edges.size() : "0") + " edges, ");
		strBuilder.append((hasVertices ? vertices.size() : "0") + " vertices ");

		strBuilder.append("\n\nEdges:");

		if (edges != null && !edges.isEmpty()) {
			List<Edge> sortedEdges = edges.values().stream().sorted((o1, o2) -> {
				int compareVal = o1.getFromVertex().getLabel().compareTo(o2.getFromVertex().getLabel());
				if (compareVal == 0) {
					compareVal = o1.getToVertex().getLabel().compareTo(o2.getToVertex().getLabel());
				}
				return compareVal;
			}).collect(Collectors.toList());
			for (Edge edge : sortedEdges) {
				strBuilder.append("\n\t" + edge.toString());
			}
		} else {
			strBuilder.append("\n\tNone.");
		}

		strBuilder.append("\n\nVertices:\n\t");

		if (vertices != null && !vertices.isEmpty()) {
			List<String> sortedVertices = new ArrayList<>(vertices.keySet());
			Collections.sort(sortedVertices);
			strBuilder.append(sortedVertices);
		} else {
			strBuilder.append("None.");
		}
		strBuilder.append("\n");

		return strBuilder.toString();
	}

	/**
	 * A graph is equal (in this case) if the vertices and edges are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Graph)) {
			return false;
		}

		boolean isEqual = true;
		Graph graph = (Graph) obj;

		// A graph is equal if it's vertices and edges are equal
		// First just check to make sure the amount of vertices and edges are
		// equal
		isEqual = ((getVertices() == null && graph.getVertices() == null)
				|| (getVertices().isEmpty() && graph.getVertices().isEmpty())
				|| (getVertices() != null && graph.getVertices() != null));
		isEqual = isEqual && ((getEdges() == null && graph.getEdges() == null)
				|| (getEdges().isEmpty() && graph.getEdges().isEmpty())
				|| (getEdges() != null && graph.getEdges() != null));
		isEqual = isEqual && getVertices().size() == graph.getVertices().size()
				&& getEdges().size() == graph.getEdges().size();

		if (isEqual) {

			// Make sure all of the vertices in the other graph exist in the
			// list of vertices in this graph
			if (graph.getVertices() != null && !graph.getVertices().isEmpty()) {
				for (Vertex otherVertex : graph.getVertices().values()) {
					if (!getVertices().values().contains(otherVertex)) {
						isEqual = false;
						break;
					}
				}
			}

			if (isEqual) {
				// Make sure all of the edges in the other graph are contained
				// in this graph
				if (graph.getEdges() != null && !graph.getEdges().isEmpty()) {
					for (Edge otherEdge : graph.getEdges().values()) {
						if (!getEdges().values().contains(otherEdge)) {
							isEqual = false;
							break;
						}
					}
				}
			}
		}

		return isEqual;
	}

	@Override
	public int hashCode() {
		return Objects.hash(vertices, edges);
	}
}
