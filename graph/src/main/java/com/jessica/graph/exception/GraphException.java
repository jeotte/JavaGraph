package com.jessica.graph.exception;

public class GraphException extends Exception {

	private static final long serialVersionUID = 8333078237600078451L;

	public GraphException(GraphExceptionMsg message) {
		super(message.toString());
	}

	public GraphException(String className, GraphExceptionMsg message) {
		super(className + ": " + message.toString());
	}

	public GraphException(String className, GraphExceptionMsg message, Throwable throwable) {
		super(className + ": " + message.toString(), throwable);
	}
}
