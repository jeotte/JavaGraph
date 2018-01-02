package com.jessica.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.jessica.graph.exception.GraphException;
import com.jessica.graph.exception.GraphExceptionMsg;
import com.jessica.graph.model.Edge;
import com.jessica.graph.model.Graph;
import com.jessica.graph.model.Vertex;

public class GraphManagementTest {

	GraphManagement graphMgmt = new GraphManagementImpl();

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setupBeforeEachTest() {
		graphMgmt.setGraph(new Graph());
	}

	@After
	public void cleanupAfterEachTest() {
		graphMgmt.clear();
	}

	@Test
	public void testAddEdge() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");

		// Add edge between the vertices
		Edge edge = new Edge(vertex1, vertex2);
		graphMgmt.addEdge(edge);

		// Verify the edge was added to the graph
		assertEquals(1, graphMgmt.getGraph().getEdges().size());
		Edge addedEdge = (new ArrayList<>(graphMgmt.getGraph().getEdges().values())).get(0);
		assertEquals(1, addedEdge.getWeight());
		assertNotNull(addedEdge.getLabel());
	}

	@Test
	public void testAddEdge_NullEdge() throws Exception {

		try {
			// Try adding a null edge to the graph
			graphMgmt.addEdge(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_EDGE.toString()));
		}
	}

	@Test
	public void testAddEdge_NullFromVertex() throws Exception {

		try {
			// Create an edge with a null from vertex
			Edge edge = new Edge(null, new Vertex("V1"));

			// Try adding the edge to the graph
			graphMgmt.addEdge(edge);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.EDGE_MISSING_VERTEX.toString()));
		}
	}

	@Test
	public void testAddEdge_NullToVertex() throws Exception {

		try {
			// Create an edge with a null to vertex
			Edge edge = new Edge(new Vertex("V1"), null);

			// Try adding the edge to the graph
			graphMgmt.addEdge(edge);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.EDGE_MISSING_VERTEX.toString()));
		}
	}

	@Test
	public void testAddEdge_ExistingEdge() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");

		// Add edge between the vertices
		Edge edge = new Edge(vertex1, vertex2);
		graphMgmt.addEdge(edge);

		try {
			// Try adding the edge a second time
			graphMgmt.addEdge(edge);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.PREEXISTING_EDGE.toString()));
		}
	}

	@Test
	public void testAddEdge_AddsVertices() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3, 1));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));

		// Utilizing private method getVertexLabels, which gets the vertex labels from the vertices in the graph
		List<String> vertexLabels = getVertexLabels(graphMgmt.getGraph());

		// Assert the vertices got added to the graph
		assertNotNull(vertexLabels);
		assertEquals(4, vertexLabels.size());
	}

	@Test
	public void testAddVertex() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");

		// Add the vertices to the graph
		graphMgmt.addVertex(vertex1);
		graphMgmt.addVertex(vertex2);
		graphMgmt.addVertex(vertex3);

		// Utilizing private method getVertexLabels, which gets the vertex labels from the vertices in the graph
		List<String> vertexLabels = getVertexLabels(graphMgmt.getGraph());

		// Assert the vertices got added to the graph
		assertNotNull(vertexLabels);
		assertEquals(3, vertexLabels.size());
	}

	@Test
	public void testAddVertex_NullVertex() throws Exception {

		try {
			// Try adding a null vertex to the graph
			graphMgmt.addVertex(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_VERTEX.toString()));
		}
	}

	@Test
	public void testAddVertex_ExistingVertex() throws Exception {
		// Create the vertices
		Vertex vertex1 = new Vertex("V1");

		// Add the vertex to the graph
		graphMgmt.addVertex(vertex1);

		try {
			// Try adding the vertex a second time
			graphMgmt.addVertex(vertex1);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.PREEXISTING_VERTEX.toString()));
		}
	}

	@Test
	public void testRemoveVertex_NoEdges() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");

		// Add the vertices to the graph
		graphMgmt.addVertex(vertex1);
		graphMgmt.addVertex(vertex2);
		graphMgmt.addVertex(vertex3);

		// Precondition: the number of vertices should be 3, and edges should be 0
		List<String> vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(3, vertexLabels.size());
		assertEquals(0, graphMgmt.getGraph().getEdges().size());

		// Remove the vertex "V2"
		graphMgmt.removeVertex(vertex2);

		// Verify that the size of vertices now 2
		vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(2, vertexLabels.size());
		assertEquals(0, graphMgmt.getGraph().getEdges().size());
	}

	@Test
	public void testRemoveVertex_WithEdgesAffected() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge2"));
		graphMgmt.addEdge(new Edge(vertex3, vertex1, 5, "edge 3->1"));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));
		graphMgmt.addEdge(new Edge(vertex4, vertex1));

		// Precondition: verify the number of vertices is 4 and edges is 6
		List<String> vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(4, vertexLabels.size());
		assertEquals(6, graphMgmt.getGraph().getEdges().size());

		// Remove the vertex "V4" from the graph
		graphMgmt.removeVertex(vertex4);

		// Verify that number of vertices is now 3 and edges are 4 (removes both incoming and outgoing edges)
		vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(3, vertexLabels.size());
		assertEquals(4, graphMgmt.getGraph().getEdges().size());
	}

	@Test
	public void testRemoveVertex_NullVertex() throws Exception {
		try {
			// Try removing a null vertex from the graph
			graphMgmt.removeVertex(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_VERTEX.toString()));
		}
	}

	@Test
	public void testRemoveVertex_VertexNotInGraph() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge 2->3"));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));

		try {
			// Try removing a vertex not in the graph
			graphMgmt.removeVertex(new Vertex("V5"));
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.INVALID_VERTEX.toString()));
		}
	}

	@Test
	public void testRemoveEdge() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge 2->3"));
		Edge edge = new Edge(vertex3, vertex4, 10, "edge 3->4");
		graphMgmt.addEdge(edge);

		// Precondition: verify the number of vertices is 4 and edges is 4
		assertEquals(4, graphMgmt.getGraph().getEdges().size());
		List<String> vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(4, vertexLabels.size());

		// Remove the edge linking V3 to V4
		graphMgmt.removeEdge(edge);

		// Verify that the edges decreased by one and the vertices were unaffected
		assertEquals(3, graphMgmt.getGraph().getEdges().size());
		vertexLabels = getVertexLabels(graphMgmt.getGraph());
		assertEquals(4, vertexLabels.size());
	}

	@Test
	public void testRemoveEdge_NullEdge() throws Exception {
		try {
			// Try removing a null edge from the graph
			graphMgmt.removeEdge(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_EDGE.toString()));
		}
	}

	@Test
	public void testGetNextVertices() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");
		Vertex vertex5 = new Vertex("V5");
		Vertex vertex6 = new Vertex("V6");
		Vertex vertex7 = new Vertex("V7");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge 2->3"));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));
		graphMgmt.addEdge(new Edge(vertex3, vertex5, 15, "edge 3->5"));
		graphMgmt.addEdge(new Edge(vertex3, vertex6, 4));
		graphMgmt.addEdge(new Edge(vertex4, vertex6));
		graphMgmt.addEdge(new Edge(vertex5, vertex4, 2));
		graphMgmt.addEdge(new Edge(vertex5, vertex6, 5));
		graphMgmt.addEdge(new Edge(vertex6, vertex7, 10, "edge 6->7"));
		graphMgmt.addEdge(new Edge(vertex7, vertex1));

		// Get the next vertices following the edges after V3
		List<Vertex> vertices = graphMgmt.getNextVertices(vertex3);

		// Assert that the vertices are expected: V4, V5, V6
		assertNotNull(vertices);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(vertex4));
		assertTrue(vertices.contains(vertex5));
		assertTrue(vertices.contains(vertex6));
	}

	@Test
	public void testGetNextVertices_NullVertex() throws Exception {

		try {
			// Try getting the next vertices for a null vertex
			graphMgmt.getNextVertices(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_VERTEX.toString()));
		}
	}

	@Test
	public void testGetNextVertices_VertexNotInGraph() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge 2->3"));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));

		try {
			// Try getting the next vertices for a vertex not in the graph
			graphMgmt.getNextVertices(new Vertex("V5"));
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.INVALID_VERTEX.toString()));
		}
	}

	@Test
	public void testGetNextVertices_NoOutgoingEdges() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex4));
		graphMgmt.addEdge(new Edge(vertex2, vertex3, 3, "edge 2->3"));
		graphMgmt.addEdge(new Edge(vertex3, vertex4, 10, "edge 3->4"));

		// Get the next vertices for V4 (which has no outgoing edges)
		List<Vertex> vertices = graphMgmt.getNextVertices(vertex4);
		assertNull(vertices);
	}

	@Test
	public void testDepthFirstSearch() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3));
		graphMgmt.addEdge(new Edge(vertex3, vertex1));
		graphMgmt.addEdge(new Edge(vertex3, vertex4));

		// Run the depth first search with vertex V3 as the root
		List<Vertex> listOfVertices = graphMgmt.depthFirstSeach(vertex3);

		// Assert the expected order of the results: 3, 1, 2, 4
		assertEquals(4, listOfVertices.size());
		assertEquals(vertex3, listOfVertices.get(0));
		assertEquals(vertex1, listOfVertices.get(1));
		assertEquals(vertex2, listOfVertices.get(2));
		assertEquals(vertex4, listOfVertices.get(3));
	}

	@Test
	public void testSaveAndReadFromFile() throws Exception {

		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");
		Vertex vertex4 = new Vertex("V4");
		Vertex vertex5 = new Vertex("V5");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3));
		graphMgmt.addEdge(new Edge(vertex3, vertex1));
		graphMgmt.addEdge(new Edge(vertex3, vertex4));
		graphMgmt.addVertex(vertex5);

		// Using the temporary folder to create the file, so that the file will get cleaned up after execution
		File testFile = folder.newFile("Graph2File");

		// Save will throw an exception if it ran into an error, therefore nothing to assert to verify it worked
		graphMgmt.saveGraphToFile(testFile.getAbsolutePath());

		Graph readGraph = graphMgmt.readGraphFromFile(testFile.getAbsolutePath());
		assertNotNull(readGraph);
		assertEquals(graphMgmt.getGraph(), readGraph);
	}

	@Test
	public void testSaveGraphToFile_EmptyGraph() throws Exception {
		try {
			// Try saving an empty graph
			graphMgmt.saveGraphToFile("testSave");
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.EMPTY_GRAPH.toString()));
		}
	}

	@Test
	public void testSaveGraphToFile_NullFileName() throws Exception {
		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3));
		graphMgmt.addEdge(new Edge(vertex3, vertex1));

		try {
			// Try saving the graph with an null file name
			graphMgmt.saveGraphToFile(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_FILENAME.toString()));
		}
	}

	@Test
	public void testSaveGraphToFile_EmptyFileName() throws Exception {
		// Create the vertices
		Vertex vertex1 = new Vertex("V1");
		Vertex vertex2 = new Vertex("V2");
		Vertex vertex3 = new Vertex("V3");

		// Add edges between the vertices
		graphMgmt.addEdge(new Edge(vertex1, vertex2));
		graphMgmt.addEdge(new Edge(vertex1, vertex3));
		graphMgmt.addEdge(new Edge(vertex2, vertex3));
		graphMgmt.addEdge(new Edge(vertex3, vertex1));

		try {
			// Try saving the graph with an null file name
			graphMgmt.saveGraphToFile("");
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_FILENAME.toString()));
		}
	}

	@Test
	public void testReadGraphFromFile_NullFileName() throws Exception {
		try {
			// Try saving the graph with an null file name
			graphMgmt.readGraphFromFile(null);
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_FILENAME.toString()));
		}
	}

	@Test
	public void testReadGraphFromFile_EmptyFileName() throws Exception {
		try {
			// Try saving the graph with an null file name
			graphMgmt.readGraphFromFile("");
			fail();
		} catch (GraphException exception) {
			assertTrue(exception.getMessage().contains(GraphExceptionMsg.NULL_FILENAME.toString()));
		}
	}

	private List<String> getVertexLabels(Graph graph) {
		return graph.getVertices().values().stream().map(Vertex::getLabel).collect(Collectors.toList());
	}
}
