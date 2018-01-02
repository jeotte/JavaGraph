package com.jessica.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Serializable {

	private static final long serialVersionUID = -7465770451734993616L;

	private String label;
	private List<Edge> outgoingEdges;
	private List<Edge> incomingEdges;

	/**
	 * Class constructor
	 * 
	 * @param pLabel
	 *            The label which uniquely identifies this vertex
	 */
	public Vertex(String label) {
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
		if (label != null && label != "") {
			this.label = label;
		} else {
			this.label = Integer.toString(hashCode());
		}
	}

	/**
	 * 
	 * @return The list of all edges that are directed toward this vertex
	 */
	public List<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	/**
	 * 
	 * @return The list of all edges that are directed away from this vertex
	 */
	public List<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	/**
	 * 
	 * @return The label, which uniquely identifies this vertex
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Adds an incoming edge to the list of incoming edges. If the edge is null, an error will be printed out and the
	 * method will return immediately. If the list already contains this edge, it will not be added.
	 * 
	 * @param edge
	 *            An edge directed toward this vertex
	 */
	public void addIncomingEdge(Edge edge) {

		// Validate that the edge is non-null
		if (edge == null) {
			System.out.println("Cannot add a null edge");
			return;
		}

		// Initialize the list if it hasn't yet been initialized
		if (incomingEdges == null) {
			incomingEdges = new ArrayList<Edge>();
		}

		// Add the edge if it doesn't already exist
		if (!incomingEdges.contains(edge)) {
			incomingEdges.add(edge);
		}
	}

	/**
	 * Adds an outgoing edge to the list of outgoing edges. If the edge is null, an error will be printed out and the
	 * method will return immediately. If the list already contains this edge, it will not be added.
	 * 
	 * @param edge
	 *            An edge directed away from this vertex
	 */
	public void addOutgoingEdge(Edge edge) {

		// Validate that the edge is non-null
		if (edge == null) {
			System.out.println("Cannot add a null edge");
			return;
		}

		// Initialize the list if it hasn't yet been initialized
		if (outgoingEdges == null) {
			outgoingEdges = new ArrayList<Edge>();
		}

		// Add the edge if it doesn't already exist
		if (!outgoingEdges.contains(edge)) {
			outgoingEdges.add(edge);
		}
	}

	/**
	 * This method will check if the edge exists in incoming edges list or the outgoing edges list and remove the edge
	 * from the appropriate list. If the edge is null, an error will be printed out an the method will return
	 * immediately. Additionally, if there are no edges associated with this vertex, an error will be printed and the
	 * method will return.
	 * 
	 * @param edge
	 *            The edge to remove
	 */
	public void removeEdge(Edge edge) {

		// Validate that the edge is non-null
		if (edge == null) {
			System.out.println("Cannot remove a null edge.");
			return;
		}

		// Validate that there are edges associated with this vertex
		if ((incomingEdges == null || incomingEdges.isEmpty()) && (outgoingEdges == null || outgoingEdges.isEmpty())) {
			System.out.println("There are currently no edges to remove from.");
			return;
		}

		// Look for the edge in the incoming and outgoing lists, and remove it
		// accordingly
		if (incomingEdges != null && incomingEdges.contains(edge)) {
			incomingEdges.remove(edge);
		} else if (outgoingEdges != null && outgoingEdges.contains(edge)) {
			outgoingEdges.remove(edge);
		}
	}

	/**
	 * 
	 * @return A new list comprised of all incoming and outgoing edges.
	 */
	public List<Edge> getAllEdges() {
		List<Edge> lAllEdges = new ArrayList<Edge>();
		lAllEdges.addAll(outgoingEdges);
		lAllEdges.addAll(incomingEdges);
		return lAllEdges;
	}

	/**
	 * Two vertices are considered equal if their labels are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Vertex)) {
			return false;
		}

		Vertex lVertex = (Vertex) obj;

		return ((label == null && lVertex.getLabel() == null) || label != null && label.equals(lVertex.getLabel()));
	}

	@Override
	public int hashCode() {
		return label.hashCode();
	}

	/**
	 * Returns a string representation of the vertex, displaying the label
	 */
	@Override
	public String toString() {
		return "Vertex: " + label;
	}

}
