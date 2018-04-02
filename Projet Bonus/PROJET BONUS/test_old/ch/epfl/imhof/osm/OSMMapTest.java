package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member;

public class OSMMapTest {

	@Test
	public void builderBuildsWithEmptyCollections() {
		new OSMMap(new ArrayList<OSMWay>(), new ArrayList<OSMRelation>());
	}
	
	@Test
	public void waysGetterReturnsCorrectWays() {
		List<OSMWay> test = new ArrayList<OSMWay>();
		OSMMap map = new OSMMap(test, new ArrayList<OSMRelation>());
		assertEquals(test, map.ways());
	}
	
	@Test
	public void relationsGetterReturnsCorrectRelations() {
		List<OSMRelation> test = new ArrayList<OSMRelation>();
		OSMMap map = new OSMMap(new ArrayList<OSMWay>(), test);
		assertEquals(test, map.relations());
	}
	
	@Test
	public void nodeAdderAndGetterWorkCorrectly() {
		OSMMap.Builder builder = new OSMMap.Builder();
		OSMNode node = new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>()));
		builder.addNode(node);
		assertEquals(node, builder.nodeForId(1));
	}
	
	@Test
	public void wayAdderAndGetterWorkCorrectly() {
		OSMMap.Builder builder = new OSMMap.Builder();
		OSMNode node1 = new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		OSMWay.Builder wayTemp = new OSMWay.Builder(3);
		wayTemp.addNode(node1);
		wayTemp.addNode(node2);
		OSMWay way = wayTemp.build();
		builder.addWay(way);
		assertEquals(way, builder.wayForId(3));
	}
	
	@Test
	public void relationAdderAndGetterWorkCorrectly() {
		OSMMap.Builder builder = new OSMMap.Builder();
		OSMRelation relation = new OSMRelation(1, new ArrayList<Member>(), new Attributes(new HashMap<String, String>()));
		builder.addRelation(relation);
		assertEquals(relation, builder.relationForId(1));
	}

	@Test
	public void builderBuildMethodBuildsCorrectly() {
		OSMMap.Builder builder = new OSMMap.Builder();
		OSMNode node1 = new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>()));
		OSMNode node2 = new OSMNode(2, new PointGeo(0, 1), new Attributes(new HashMap<String, String>()));
		OSMWay.Builder wayTemp = new OSMWay.Builder(3);
		wayTemp.addNode(node1);
		wayTemp.addNode(node2);
		OSMWay way = wayTemp.build();
		builder.addWay(way);
		OSMRelation relation = new OSMRelation(1, new ArrayList<Member>(), new Attributes(new HashMap<String, String>()));
		builder.addRelation(relation);
		OSMMap map = builder.build();
		assertEquals(way, map.ways().get(0));
		assertEquals(relation, map.relations().get(0));
	}
}
