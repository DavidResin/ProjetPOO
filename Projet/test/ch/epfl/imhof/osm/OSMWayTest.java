package ch.epfl.imhof.osm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class OSMWayTest {
	
	final PointGeo point = new PointGeo(2, 1);
	final Attributes attr = new Attributes(new HashMap<String, String>());
	
	@Test
	public void builderBuildsCorrectly() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		new OSMWay(3, nodes, attr);
	}

	@Test (expected = IllegalArgumentException.class)
	public void builderFailsWhenTooFewNodes() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		new OSMWay(3, nodes, attr);
	}
	
	@Test
	public void nodesCountReturnsRightNumber() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertEquals(2, test.nodesCount());
	}
	
	@Test
	public void nodesGetterReturnsRightNodes() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertEquals(nodes, test.nodes());
	}
	
	@Test
	public void nonRepeatingNodesGetterReturnsRightNodes() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertEquals(nodes.subList(0, 1), test.nonRepeatingNodes());
		nodes.add(new OSMNode(2, new PointGeo(1, 1), attr));
		OSMWay test2 = new OSMWay(3, nodes, attr);
		assertEquals(nodes, test2.nonRepeatingNodes());
	}

	@Test
	public void firstNodeGetterReturnsFirstNode() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertEquals(nodes.get(0), test.firstNode());
	}
	
	@Test
	public void lastNodeGetterReturnsLastNode() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertEquals(nodes.get(1), test.lastNode());
	}
	
	@Test
	public void isClosedChecksStateCorrectly() {
		List<OSMNode> nodes = new ArrayList<OSMNode>();
		nodes.add(new OSMNode(1, point, attr));
		nodes.add(new OSMNode(2, point, attr));
		OSMWay test = new OSMWay(3, nodes, attr);
		assertTrue(test.isClosed());
	}
	
	@Test
	public void builderBuilderBuildsCorrectly() {
		new OSMWay.Builder(1);
	}
	
	@Test
	public void addNodeAddsCorrectly() {
		OSMNode node = new OSMNode(1, point, attr);
		OSMWay.Builder test = new OSMWay.Builder(1);
		test.addNode(node);
		test.addNode(new OSMNode(2, point, attr));
		OSMWay test2 = test.build();
		assertEquals(node, test2.firstNode());
	}
	
	@Test
	public void builderBuildMethodBuildsCorrectly() {
		OSMWay.Builder test = new OSMWay.Builder(1);
		test.addNode(new OSMNode(1, point, attr));
		test.addNode(new OSMNode(2, point, attr));
		test.build();
	}
	
	@Test (expected = IllegalStateException.class)
	public void builderBuildMethodFailsWhenIncomplete() {
		OSMWay.Builder test = new OSMWay.Builder(1);
		test.addNode(new OSMNode(1, point, attr));
		test.addNode(new OSMNode(2, point, attr));
		test.setIncomplete();
		test.build();
	}
	
	@Test
	public void isIncompleteChecksStateCorrectly() {
		OSMWay.Builder test = new OSMWay.Builder(1);
		test.addNode(new OSMNode(1, point, attr));
		test.addNode(new OSMNode(2, point, attr));
		test.setIncomplete();
		assertTrue(test.isIncomplete());
		OSMWay.Builder test2 = new OSMWay.Builder(1);
		test.addNode(new OSMNode(1, point, attr));
		assertTrue(test2.isIncomplete());
	}
}
