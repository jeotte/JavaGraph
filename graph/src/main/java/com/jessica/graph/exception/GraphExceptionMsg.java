package com.jessica.graph.exception;

public enum GraphExceptionMsg {

	/**
	 * The vertex must be provided.
	 */
	NULL_VERTEX("The vertex must be provided."),

	/**
	 * The vertex does not exist in the graph.
	 */
	INVALID_VERTEX("The vertex does not exist in the graph."),

	/**
	 * The vertex already exists in the graph.
	 */
	PREEXISTING_VERTEX("The vertex already exists in the graph."),

	/**
	 * An edge must have both a from and to vertex.
	 */
	EDGE_MISSING_VERTEX("An edge must have both a from and to vertex."),

	/**
	 * The edge already exists in the graph.
	 */
	PREEXISTING_EDGE("The edge already exists in the graph."),

	/**
	 * The edge must be provided.
	 */
	NULL_EDGE("The edge must be provided."),

	/**
	 * The graph is null.
	 */
	NULL_GRAPH("The graph is null."),

	/**
	 * The graph is empty.
	 */
	EMPTY_GRAPH("The graph is empty."),

	/**
	 * The file name must be provided.
	 */
	NULL_FILENAME("The file name must be provided."),

	/**
	 * The graph file was not found.
	 */
	FILE_NOT_FOUND("The graph file was not found."),

	/**
	 * An error occurred saving the graph.
	 */
	FILE_SAVE_ERROR("An error occurred saving the graph."),

	/**
	 * The file does not contain a graph.
	 */
	UNEXPECTED_FILE_CONTENTS("The file does not contain a graph."),

	/**
	 * An error occurred reading the graph from the file.
	 */
	FILE_READ_ERROR("An error occurred reading the graph from the file."),

	/**
	 * The destination vertex must be provided.
	 */
	NULL_DESTINATION("The destination vertex must be provided."),

	/**
	 * The graph and source vertex must be provided.
	 */
	NULL_GRAPH_SRC("The graph and source vertex must be provided.");

	private final String text;

	private GraphExceptionMsg(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
