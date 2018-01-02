package com.jessica.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.exception.GraphExceptionMsg;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

public class DijkstrasShortestPathTest {

	DijkstrasShortestPath shortestPathAlgorithm = new DijkstrasShortestPath();

	@Test
	@SuppressWarnings("unchecked")
	public void testFindMinimumDistance_Graph1_SourceA() throws Exception {
		Graph graph = getGraph1();
		Vertex vertexA = graph.getVertices().get("A");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		Map<Vertex, Map<String, Object>> result = (Map<Vertex, Map<String, Object>>) method
				.invoke(shortestPathAlgorithm, graph, vertexA);

		assertEquals(5, getDistance(result, graph.getVertices().get("B")));
		assertEquals(10, getDistance(result, graph.getVertices().get("C")));
		assertEquals(10, getDistance(result, graph.getVertices().get("D")));
		assertEquals(8, getDistance(result, graph.getVertices().get("E")));
		assertEquals(12, getDistance(result, graph.getVertices().get("F")));
		assertEquals(10, getDistance(result, graph.getVertices().get("G")));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFindMinimumDistance_Graph2_SourceA() throws Exception {
		Graph graph = getGraph2();
		Vertex vertexA = graph.getVertices().get("A");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		Map<Vertex, Map<String, Object>> result = (Map<Vertex, Map<String, Object>>) method
				.invoke(shortestPathAlgorithm, graph, vertexA);

		assertEquals(4, getDistance(result, graph.getVertices().get("B")));
		assertEquals(3, getDistance(result, graph.getVertices().get("C")));
		assertEquals(11, getDistance(result, graph.getVertices().get("D")));
		assertEquals(5, getDistance(result, graph.getVertices().get("E")));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFindMinimumDistance_Graph3_SourceA() throws Exception {
		Graph graph = getGraph3();
		Vertex vertexA = graph.getVertices().get("A");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		Map<Vertex, Map<String, Object>> result = (Map<Vertex, Map<String, Object>>) method
				.invoke(shortestPathAlgorithm, graph, vertexA);

		assertEquals(12, getDistance(result, graph.getVertices().get("B")));
		assertEquals(9, getDistance(result, graph.getVertices().get("C")));
		assertEquals(8, getDistance(result, graph.getVertices().get("D")));
		assertEquals(9, getDistance(result, graph.getVertices().get("E")));
		assertEquals(4, getDistance(result, graph.getVertices().get("F")));
		assertEquals(5, getDistance(result, graph.getVertices().get("G")));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFindMinimumDistance_Graph3_SourceD() throws Exception {
		Graph graph = getGraph3();
		Vertex vertexD = graph.getVertices().get("D");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		Map<Vertex, Map<String, Object>> result = (Map<Vertex, Map<String, Object>>) method
				.invoke(shortestPathAlgorithm, graph, vertexD);

		assertEquals(4, getDistance(result, graph.getVertices().get("A")));
		assertEquals(5, getDistance(result, graph.getVertices().get("B")));
		assertEquals(2, getDistance(result, graph.getVertices().get("C")));
		assertEquals(1, getDistance(result, graph.getVertices().get("E")));
		assertEquals(4, getDistance(result, graph.getVertices().get("F")));
		assertEquals(3, getDistance(result, graph.getVertices().get("G")));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testFindMinimumDistance_Graph4_SourceA() throws Exception {
		Graph graph = new Graph();

		Vertex vertexA = new Vertex("A");
		Vertex vertexB = new Vertex("B");
		Vertex vertexC = new Vertex("C");
		Vertex vertexD = new Vertex("D");
		Vertex vertexE = new Vertex("E");
		Vertex vertexF = new Vertex("F");
		Vertex vertexG = new Vertex("G");
		Vertex vertexH = new Vertex("H");

		Edge edgeAB = new Edge(vertexA, vertexB, 20);
		Edge edgeAD = new Edge(vertexA, vertexD, 80);
		Edge edgeAG = new Edge(vertexA, vertexG, 90);
		Edge edgeBF = new Edge(vertexB, vertexF, 10);
		Edge edgeCD = new Edge(vertexC, vertexD, 10);
		Edge edgeCF = new Edge(vertexC, vertexF, 50);
		Edge edgeCH = new Edge(vertexC, vertexH, 20);
		Edge edgeDG = new Edge(vertexD, vertexG, 20);
		Edge edgeEB = new Edge(vertexE, vertexB, 50);
		Edge edgeEG = new Edge(vertexE, vertexG, 30);
		Edge edgeFC = new Edge(vertexF, vertexC, 10);
		Edge edgeFD = new Edge(vertexF, vertexD, 40);
		Edge edgeGA = new Edge(vertexG, vertexA, 20);

		vertexA.addOutgoingEdge(edgeAB);
		vertexA.addOutgoingEdge(edgeAD);
		vertexA.addOutgoingEdge(edgeAG);
		vertexB.addOutgoingEdge(edgeBF);
		vertexC.addOutgoingEdge(edgeCD);
		vertexC.addOutgoingEdge(edgeCF);
		vertexC.addOutgoingEdge(edgeCH);
		vertexD.addOutgoingEdge(edgeDG);
		vertexE.addOutgoingEdge(edgeEB);
		vertexE.addOutgoingEdge(edgeEG);
		vertexF.addOutgoingEdge(edgeFC);
		vertexF.addOutgoingEdge(edgeFD);
		vertexG.addOutgoingEdge(edgeGA);

		graph.getVertices().put(vertexA.getLabel(), vertexA);
		graph.getVertices().put(vertexB.getLabel(), vertexB);
		graph.getVertices().put(vertexC.getLabel(), vertexC);
		graph.getVertices().put(vertexD.getLabel(), vertexD);
		graph.getVertices().put(vertexE.getLabel(), vertexE);
		graph.getVertices().put(vertexF.getLabel(), vertexF);
		graph.getVertices().put(vertexG.getLabel(), vertexG);
		graph.getVertices().put(vertexH.getLabel(), vertexH);

		graph.getEdges().put(edgeAB.getLabel(), edgeAB);
		graph.getEdges().put(edgeAD.getLabel(), edgeAD);
		graph.getEdges().put(edgeAG.getLabel(), edgeAG);
		graph.getEdges().put(edgeBF.getLabel(), edgeBF);
		graph.getEdges().put(edgeCD.getLabel(), edgeCD);
		graph.getEdges().put(edgeCF.getLabel(), edgeCF);
		graph.getEdges().put(edgeCH.getLabel(), edgeCH);
		graph.getEdges().put(edgeDG.getLabel(), edgeDG);
		graph.getEdges().put(edgeEB.getLabel(), edgeEB);
		graph.getEdges().put(edgeEG.getLabel(), edgeEG);
		graph.getEdges().put(edgeFC.getLabel(), edgeFC);
		graph.getEdges().put(edgeFD.getLabel(), edgeFD);
		graph.getEdges().put(edgeGA.getLabel(), edgeGA);

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		Map<Vertex, Map<String, Object>> result = (Map<Vertex, Map<String, Object>>) method
				.invoke(shortestPathAlgorithm, graph, vertexA);

		assertEquals(70, getDistance(result, graph.getVertices().get("G")));
	}

	@Test
	public void testFindMinimumDistance_NullGraph() throws Exception {
		Graph graph = null;
		Vertex vertexA = new Vertex("V1");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		try {
			method.invoke(shortestPathAlgorithm, graph, vertexA);
			fail();
		} catch (Exception exception) {
			assertTrue(exception instanceof InvocationTargetException);
			assertTrue(((InvocationTargetException) exception).getTargetException() instanceof GraphException);
			GraphException graphEx = (GraphException) ((InvocationTargetException) exception).getTargetException();
			assertTrue(graphEx.getMessage().contains(GraphExceptionMsg.NULL_GRAPH_SRC.toString()));
		}
	}

	@Test
	public void testFindMinimumDistance_NullSourceVertex() throws Exception {
		Graph graph = getGraph1();
		Vertex vertexA = null;

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		try {
			method.invoke(shortestPathAlgorithm, graph, vertexA);
			fail();
		} catch (Exception exception) {
			assertTrue(exception instanceof InvocationTargetException);
			assertTrue(((InvocationTargetException) exception).getTargetException() instanceof GraphException);
			GraphException graphEx = (GraphException) ((InvocationTargetException) exception).getTargetException();
			assertTrue(graphEx.getMessage().contains(GraphExceptionMsg.NULL_GRAPH_SRC.toString()));
		}
	}

	@Test
	public void testFindMinimumDistance_EmptyGraph() throws Exception {
		Graph graph = new Graph();
		Vertex vertexA = new Vertex("V1");

		// Test finding the minimum distance from all vertices to the source vertex
		Method method = shortestPathAlgorithm.getClass().getDeclaredMethod("findMinimumDistance",
				new Class[] { Graph.class, Vertex.class });
		method.setAccessible(true);

		try {
			method.invoke(shortestPathAlgorithm, graph, vertexA);
			fail();
		} catch (Exception exception) {
			assertTrue(exception instanceof InvocationTargetException);
			assertTrue(((InvocationTargetException) exception).getTargetException() instanceof GraphException);
			GraphException graphEx = (GraphException) ((InvocationTargetException) exception).getTargetException();
			assertTrue(graphEx.getMessage().contains(GraphExceptionMsg.EMPTY_GRAPH.toString()));
		}
	}

	@Test
	public void testGetShortestPath_Graph1_SourceA() throws Exception {
		Graph graph = getGraph1();

		// Test finding the shortest path from vertex A to vertex F
		List<Vertex> path = shortestPathAlgorithm.getShortestPath(graph, graph.getVertices().get("A"),
				graph.getVertices().get("F"));

		assertEquals("A", path.get(0).getLabel());
		assertEquals("B", path.get(1).getLabel());
		assertEquals("E", path.get(2).getLabel());
		assertEquals("G", path.get(3).getLabel());
		assertEquals("F", path.get(4).getLabel());
	}

	@Test
	public void testGetShortestPath_Graph2_SourceA() throws Exception {
		Graph graph = getGraph2();

		// Test finding the shortest path from vertex A to vertex B
		List<Vertex> path = shortestPathAlgorithm.getShortestPath(graph, graph.getVertices().get("A"),
				graph.getVertices().get("B"));

		assertEquals("A", path.get(0).getLabel());
		assertEquals("C", path.get(1).getLabel());
		assertEquals("B", path.get(2).getLabel());
	}

	@Test
	public void testGetShortestPath_Graph3_SourceD() throws Exception {
		Graph graph = getGraph3();

		// Test finding the shortest path from vertex D to vertex A
		List<Vertex> path = shortestPathAlgorithm.getShortestPath(graph, graph.getVertices().get("D"),
				graph.getVertices().get("A"));

		assertEquals("D", path.get(0).getLabel());
		assertEquals("E", path.get(1).getLabel());
		assertEquals("G", path.get(2).getLabel());
		assertEquals("A", path.get(3).getLabel());
	}

	@Test
	public void testGetShortestPath_Graph2_A_to_D() throws Exception {
		Graph graph = getGraph2();

		// Test finding the shortest path from vertex A to vertex B
		List<Vertex> path = shortestPathAlgorithm.getShortestPath(graph, graph.getVertices().get("A"),
				graph.getVertices().get("D"));

		assertEquals("A", path.get(0).getLabel());
		assertEquals("C", path.get(1).getLabel());
		assertEquals("B", path.get(2).getLabel());
		assertEquals("D", path.get(3).getLabel());
	}

	@Test
	public void testGetShortestPath_NullDestination() throws Exception {
		Graph graph = getGraph2();

		try {
			// Test passing in a null destination
			shortestPathAlgorithm.getShortestPath(getGraph2(), graph.getVertices().get("A"), null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_DESTINATION.toString()));
		}
	}

	@Test
	public void testGetShortestDistance_Graph1_SourceA() throws Exception {
		Graph graph = getGraph1();

		// Test finding the shortest distance from vertex A to vertex F
		Integer value = shortestPathAlgorithm.getShortestDistance(graph, graph.getVertices().get("A"),
				graph.getVertices().get("F"));

		assertEquals(12, value.intValue());
	}

	@Test
	public void testGetShortestDistance_Graph2_SourceA() throws Exception {
		Graph graph = getGraph2();

		// Test finding the shortest distance from vertex A to vertex B
		Integer value = shortestPathAlgorithm.getShortestDistance(graph, graph.getVertices().get("A"),
				graph.getVertices().get("B"));

		assertEquals(4, value.intValue());
	}

	@Test
	public void testGetShortestDistance_Graph3_SourceD() throws Exception {
		Graph graph = getGraph3();

		// Test finding the shortest distance from vertex D to vertex A
		Integer value = shortestPathAlgorithm.getShortestDistance(graph, graph.getVertices().get("D"),
				graph.getVertices().get("A"));

		assertEquals(4, value.intValue());
	}

	@Test
	public void testGetShortestDistance_NullDestination() throws Exception {
		Graph graph = getGraph2();

		try {
			// Test passing in a null destination
			shortestPathAlgorithm.getShortestDistance(getGraph2(), graph.getVertices().get("A"), null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_DESTINATION.toString()));
		}
	}

	private Graph getGraph1() {
		Graph graph = new Graph();

		Vertex vertexA = new Vertex("A");
		Vertex vertexB = new Vertex("B");
		Vertex vertexC = new Vertex("C");
		Vertex vertexD = new Vertex("D");
		Vertex vertexE = new Vertex("E");
		Vertex vertexF = new Vertex("F");
		Vertex vertexG = new Vertex("G");

		Edge edgeAB = new Edge(vertexA, vertexB, 5, "a->b");
		Edge edgeAC = new Edge(vertexA, vertexC, 10, "a->c");
		Edge edgeBD = new Edge(vertexB, vertexD, 6, "b->d");
		Edge edgeBE = new Edge(vertexB, vertexE, 3, "b->e");
		Edge edgeDF = new Edge(vertexD, vertexF, 6, "d->f");
		Edge edgeEC = new Edge(vertexE, vertexC, 2, "e->c");
		Edge edgeED = new Edge(vertexE, vertexD, 2, "e->d");
		Edge edgeEG = new Edge(vertexE, vertexG, 2, "e->g");
		Edge edgeGF = new Edge(vertexG, vertexF, 2, "g->f");

		vertexA.addOutgoingEdge(edgeAB);
		vertexA.addOutgoingEdge(edgeAC);
		vertexB.addOutgoingEdge(edgeBD);
		vertexB.addOutgoingEdge(edgeBE);
		vertexD.addOutgoingEdge(edgeDF);
		vertexE.addOutgoingEdge(edgeEC);
		vertexE.addOutgoingEdge(edgeED);
		vertexE.addOutgoingEdge(edgeEG);
		vertexG.addOutgoingEdge(edgeGF);

		graph.getVertices().put(vertexA.getLabel(), vertexA);
		graph.getVertices().put(vertexB.getLabel(), vertexB);
		graph.getVertices().put(vertexC.getLabel(), vertexC);
		graph.getVertices().put(vertexD.getLabel(), vertexD);
		graph.getVertices().put(vertexE.getLabel(), vertexE);
		graph.getVertices().put(vertexF.getLabel(), vertexF);
		graph.getVertices().put(vertexG.getLabel(), vertexG);

		graph.getEdges().put(edgeAB.getLabel(), edgeAB);
		graph.getEdges().put(edgeAC.getLabel(), edgeAC);
		graph.getEdges().put(edgeBD.getLabel(), edgeBD);
		graph.getEdges().put(edgeBE.getLabel(), edgeBE);
		graph.getEdges().put(edgeDF.getLabel(), edgeDF);
		graph.getEdges().put(edgeEC.getLabel(), edgeEC);
		graph.getEdges().put(edgeED.getLabel(), edgeED);
		graph.getEdges().put(edgeEG.getLabel(), edgeEG);
		graph.getEdges().put(edgeGF.getLabel(), edgeGF);

		return graph;
	}

	private Graph getGraph2() {
		Graph graph = new Graph();

		Vertex vertexA = new Vertex("A");
		Vertex vertexB = new Vertex("B");
		Vertex vertexC = new Vertex("C");
		Vertex vertexD = new Vertex("D");
		Vertex vertexE = new Vertex("E");

		Edge edgeAB = new Edge(vertexA, vertexB, 5, "a->b");
		Edge edgeAC = new Edge(vertexA, vertexC, 3, "a->c");
		Edge edgeBC = new Edge(vertexB, vertexC, 2, "b->c");
		Edge edgeBD = new Edge(vertexB, vertexD, 7, "b->d");
		Edge edgeBE = new Edge(vertexB, vertexE, 1, "b->e");
		Edge edgeCB = new Edge(vertexC, vertexB, 1, "c->b");
		Edge edgeCE = new Edge(vertexC, vertexE, 4, "c->e");
		Edge edgeDC = new Edge(vertexD, vertexC, 2, "d->c");
		Edge edgeED = new Edge(vertexE, vertexD, 7, "e->d");

		vertexA.addOutgoingEdge(edgeAB);
		vertexA.addOutgoingEdge(edgeAC);
		vertexB.addOutgoingEdge(edgeBC);
		vertexB.addOutgoingEdge(edgeBD);
		vertexB.addOutgoingEdge(edgeBE);
		vertexC.addOutgoingEdge(edgeCB);
		vertexC.addOutgoingEdge(edgeCE);
		vertexD.addOutgoingEdge(edgeDC);
		vertexE.addOutgoingEdge(edgeED);

		graph.getVertices().put(vertexA.getLabel(), vertexA);
		graph.getVertices().put(vertexB.getLabel(), vertexB);
		graph.getVertices().put(vertexC.getLabel(), vertexC);
		graph.getVertices().put(vertexD.getLabel(), vertexD);
		graph.getVertices().put(vertexE.getLabel(), vertexE);

		graph.getEdges().put(edgeAB.getLabel(), edgeAB);
		graph.getEdges().put(edgeAC.getLabel(), edgeAC);
		graph.getEdges().put(edgeBC.getLabel(), edgeBC);
		graph.getEdges().put(edgeBD.getLabel(), edgeBD);
		graph.getEdges().put(edgeBE.getLabel(), edgeBE);
		graph.getEdges().put(edgeCB.getLabel(), edgeCB);
		graph.getEdges().put(edgeCE.getLabel(), edgeCE);
		graph.getEdges().put(edgeDC.getLabel(), edgeDC);
		graph.getEdges().put(edgeED.getLabel(), edgeED);

		return graph;
	}

	private Graph getGraph3() {
		Graph graph = new Graph();

		Vertex vertexA = new Vertex("A");
		Vertex vertexB = new Vertex("B");
		Vertex vertexC = new Vertex("C");
		Vertex vertexD = new Vertex("D");
		Vertex vertexE = new Vertex("E");
		Vertex vertexF = new Vertex("F");
		Vertex vertexG = new Vertex("G");

		Edge edgeAF = new Edge(vertexA, vertexF, 4, "a->f");
		Edge edgeBA = new Edge(vertexB, vertexA, 2, "b->a");
		Edge edgeBG = new Edge(vertexB, vertexG, 2, "b->g");
		Edge edgeCB = new Edge(vertexC, vertexB, 3, "c->b");
		Edge edgeDC = new Edge(vertexD, vertexC, 2, "d->c");
		Edge edgeDE = new Edge(vertexD, vertexE, 1, "d->e");
		Edge edgeEF = new Edge(vertexE, vertexF, 3, "e->f");
		Edge edgeEG = new Edge(vertexE, vertexG, 2, "e->g");
		Edge edgeFG = new Edge(vertexF, vertexG, 1, "f->g");
		Edge edgeGF = new Edge(vertexG, vertexF, 1, "g->f");
		Edge edgeGA = new Edge(vertexG, vertexA, 1, "g->a");
		Edge edgeGC = new Edge(vertexG, vertexC, 4, "g->c");
		Edge edgeGD = new Edge(vertexG, vertexD, 3, "g->d");

		vertexA.addOutgoingEdge(edgeAF);
		vertexB.addOutgoingEdge(edgeBA);
		vertexB.addOutgoingEdge(edgeBG);
		vertexC.addOutgoingEdge(edgeCB);
		vertexD.addOutgoingEdge(edgeDC);
		vertexD.addOutgoingEdge(edgeDE);
		vertexE.addOutgoingEdge(edgeEF);
		vertexE.addOutgoingEdge(edgeEG);
		vertexF.addOutgoingEdge(edgeFG);
		vertexG.addOutgoingEdge(edgeGF);
		vertexG.addOutgoingEdge(edgeGA);
		vertexG.addOutgoingEdge(edgeGC);
		vertexG.addOutgoingEdge(edgeGD);

		graph.getVertices().put(vertexA.getLabel(), vertexA);
		graph.getVertices().put(vertexB.getLabel(), vertexB);
		graph.getVertices().put(vertexC.getLabel(), vertexC);
		graph.getVertices().put(vertexD.getLabel(), vertexD);
		graph.getVertices().put(vertexE.getLabel(), vertexE);
		graph.getVertices().put(vertexF.getLabel(), vertexF);
		graph.getVertices().put(vertexG.getLabel(), vertexG);

		graph.getEdges().put(edgeAF.getLabel(), edgeAF);
		graph.getEdges().put(edgeBA.getLabel(), edgeBA);
		graph.getEdges().put(edgeBG.getLabel(), edgeBG);
		graph.getEdges().put(edgeCB.getLabel(), edgeCB);
		graph.getEdges().put(edgeDC.getLabel(), edgeDC);
		graph.getEdges().put(edgeDE.getLabel(), edgeDE);
		graph.getEdges().put(edgeEF.getLabel(), edgeEF);
		graph.getEdges().put(edgeEG.getLabel(), edgeEG);
		graph.getEdges().put(edgeFG.getLabel(), edgeFG);
		graph.getEdges().put(edgeGF.getLabel(), edgeGF);
		graph.getEdges().put(edgeGA.getLabel(), edgeGA);
		graph.getEdges().put(edgeGC.getLabel(), edgeGC);
		graph.getEdges().put(edgeGD.getLabel(), edgeGD);

		return graph;
	}

	private int getDistance(Map<Vertex, Map<String, Object>> distanceAndPath, Vertex vertex) {
		return ((Integer) distanceAndPath.get(vertex).get("distance")).intValue();
	}

}
