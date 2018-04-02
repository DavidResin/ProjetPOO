package ch.epfl.imhof;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import ch.epfl.imhof.osm.OSMNode;

public class GraphTest {

	@Test
	public void constructorAcceptsEmptyMaps() {
		new Graph<>(new HashMap<OSMNode, Set<OSMNode>>());
	}
	
	@Test
	public void constructorAcceptsEmptySets() {
		Map<OSMNode, Set<OSMNode>> test = new HashMap<OSMNode, Set<OSMNode>>();
		test.put(new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>())), new HashSet<OSMNode>());
		new Graph<>(test);
	}
	
	@Test
	public void nodesReturnsCorrectSet() {
		Map<OSMNode, Set<OSMNode>> test = new HashMap<OSMNode, Set<OSMNode>>();
		OSMNode node1 = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		test.put(node1 , new HashSet<OSMNode>());
		test.put(node2 , new HashSet<OSMNode>());
		Graph<OSMNode> graph = new Graph<>(test);
		assertTrue(graph.nodes().contains(node1));
		assertTrue(graph.nodes().contains(node2));
	}
	
	@Test
	public void neighborsGetterReturnsCorrectSet() {
		Map<OSMNode, Set<OSMNode>> test = new HashMap<OSMNode, Set<OSMNode>>();
		Set<OSMNode> test2 = new HashSet<OSMNode>();
		OSMNode node1 = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node3 = new OSMNode(3, new PointGeo(0, 0), new Attributes(new HashMap<String, String>()));
		test2.add(node1);
		test2.add(node2);
		test.put(node3, test2);
		Graph<OSMNode> graph = new Graph<>(test);
		assertEquals(test2, graph.neighborsOf(node3));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void neighborsGetterFailsOnIncorrectInput() {
		Map<OSMNode, Set<OSMNode>> test = new HashMap<OSMNode, Set<OSMNode>>();
		Set<OSMNode> test2 = new HashSet<OSMNode>();
		OSMNode node1 = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node3 = new OSMNode(3, new PointGeo(0, 0), new Attributes(new HashMap<String, String>()));
		test2.add(node2);
		test.put(node3, test2);
		Graph<OSMNode> graph = new Graph<>(test);
		graph.neighborsOf(node1);
	}
	
	@Test
	public void graphBuilderNodeAdderAddsCorrectly() {
		Graph.Builder<OSMNode> test = new Graph.Builder<OSMNode>();
		OSMNode node = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		test.addNode(node);
		Graph<OSMNode> graph = test.build();
		assertTrue(graph.nodes().contains(node));
	}
	
	@Test
	public void graphBuilderEdgeAdderAddsCorrectly() {
		Graph.Builder<OSMNode> test = new Graph.Builder<OSMNode>();
		OSMNode node1 = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		test.addNode(node1);
		test.addNode(node2);
		test.addEdge(node1, node2);
		Graph<OSMNode> graph = test.build();
		assertTrue(graph.neighborsOf(node2).contains(node1));
		assertTrue(graph.neighborsOf(node1).contains(node2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void graphBuilderEdgeAdderFailsOnIncorrectInput() {
		Graph.Builder<OSMNode> test = new Graph.Builder<OSMNode>();
		OSMNode node1 = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		test.addNode(node1);
		test.addEdge(node1, node2);
	}
	
	@Test
	public void graphBuilderBuildsCorrectly() {
		Graph.Builder<OSMNode> test = new Graph.Builder<OSMNode>();
		test.build();
	}
}
