package com.jessica.graph.model;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable {

	private static final long serialVersionUID = 7603845860143198710L;

	private Vertex fromVertex;
	private Vertex toVertex;
	private String label;
	private int weight;
	private final static int DEFAULT_WEIGHT = 1;

	/**
	 * Class constructor that takes in the from and to vertices, initializes the label to empty string and defaults the
	 * weight to 1.
	 * 
	 * @param fromVertex
	 *            The vertex that this edge starts at
	 * @param toVertex
	 *            The vertex that this edge connects to
	 */
	public Edge(Vertex fromVertex, Vertex toVertex) {
		this(fromVertex, toVertex, DEFAULT_WEIGHT, "");
	}

	/**
	 * Class constructor that takes in the from and to vertices, as well as the weight, and initializes the label to
	 * empty string.
	 * 
	 * @param fromVertex
	 *            The vertex that this edge starts at
	 * @param toVertex
	 *            The vertex that this edge connects to
	 * @param weight
	 *            The weight or cost of this edge
	 */
	public Edge(Vertex fromVertex, Vertex toVertex, int weight) {
		this(fromVertex, toVertex, weight, "");
	}

	/**
	 * Class constructor that takes in the from and to vertices, the weight, and the label.
	 * 
	 * @param fromVertex
	 *            The vertex that this edge starts at
	 * @param toVertex
	 *            The vertex that this edge connects to
	 * @param weight
	 *            The weight or cost of this edge
	 * @param label
	 *            The label or name
	 */
	public Edge(Vertex fromVertex, Vertex toVertex, int weight, String label) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.weight = weight;

		if (label != null && label != "") {
			this.label = label;
		} else if (fromVertex != null && toVertex != null) {
			this.label = fromVertex.getLabel() + "-" + toVertex.getLabel();
		} else {
			this.label = Integer.toString(hashCode());
		}
	}

	/**
	 * 
	 * @return The from vertex, which is the starting point for this edge
	 */
	public Vertex getFromVertex() {
		return fromVertex;
	}

	/**
	 * 
	 * @return The to vertex, which is the end of this edge
	 */
	public Vertex getToVertex() {
		return toVertex;
	}

	/**
	 * 
	 * @return The label or name of this edge
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param pLabel
	 *            The label or name for this edge
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 
	 * @return The weight or cost of this edge
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * 
	 * @param pWeight
	 *            The weight or cost of this edge
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Two edges are considered equal if their from and to vertices, and the weight is equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Edge)) {
			return false;
		}

		Edge edge = (Edge) obj;

		boolean isEqual = true;

		isEqual = (fromVertex == null && edge.getFromVertex() == null)
				|| (fromVertex != null && fromVertex.equals(edge.getFromVertex()));
		isEqual = isEqual && ((toVertex == null && edge.getToVertex() == null)
				|| (toVertex != null && toVertex.equals(edge.getToVertex())));
		isEqual = isEqual && (weight == edge.getWeight());

		return isEqual;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fromVertex, toVertex, label, weight);
	}

	/**
	 * Returns a string representation of the edge, displaying the from and to vertices, along with the weight and label
	 */
	@Override
	public String toString() {
		return "{" + fromVertex + ", " + toVertex + "}\t\tWeight: " + weight + "\tLabel: " + label;
	}

}
