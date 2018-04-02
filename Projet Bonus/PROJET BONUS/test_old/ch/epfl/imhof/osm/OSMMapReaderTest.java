package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.PointGeo;

public class OSMMapReaderTest {

	@Test (expected = SAXException.class)
	public void inputStreamFailsOnSyntaxError() throws IOException, SAXException {
		OSMMapReader.readOSMFile(getClass().getResource("/testFiles/dummy.osm").getFile(), false);
	}
	
	@Test (expected = IOException.class)
	public void inputStreamFailsOnInexistentFile() throws IOException, SAXException {
		OSMMapReader.readOSMFile("/testFiles/blabla.osm", false);
	}
	
	@Test
    public void mapReaderIsCorrect() throws IOException, SAXException {
        OSMMapReader.readOSMFile(getClass().getResource("/testFiles/test.osm").getFile(), false);
    }
	
	@Test
	public void unGZipDecompressesCorrectly() throws IOException, SAXException {
		OSMMapReader.readOSMFile(getClass().getResource("/maps/interlaken.osm.gz").getFile(), true);
	}
	
	@Test
	public void mapCreationIsCorrect() throws IOException, SAXException {
		OSMMap map = OSMMapReader.readOSMFile(getClass().getResource("/testFiles/test.osm").getFile(), false);
		
		OSMNode.Builder temp;
		temp = new OSMNode.Builder(566975626, new PointGeo(Math.toRadians(6.5674450), Math.toRadians(46.5180967)));
		OSMNode node1 = temp.build();
		temp = new OSMNode.Builder(566975628, new PointGeo(Math.toRadians(6.5674123), Math.toRadians(46.5180827)));
		OSMNode node2 = temp.build();
		temp = new OSMNode.Builder(566975629, new PointGeo(Math.toRadians(6.5673942), Math.toRadians(46.5180805)));
		temp.setAttribute("barrier", "entrance");
		temp.setAttribute("bicycle", "no");
		temp.setAttribute("building", "entrance");
		OSMNode node3 = temp.build();
		
		OSMWay.Builder temp2 = new OSMWay.Builder(72320770);
		temp2.addNode(node1);
		temp2.addNode(node2);
		temp2.addNode(node3);
		temp2.setAttribute("layer", "1");
		temp2.setAttribute("source", "Plan EPFL");
		OSMWay way = temp2.build();
		
		OSMRelation.Builder temp3 = new OSMRelation.Builder(331569);
		temp3.addMember(OSMRelation.Member.Type.NODE, "inner", node1);
		temp3.addMember(OSMRelation.Member.Type.NODE, "inner", node3);
		temp3.addMember(OSMRelation.Member.Type.WAY, "inner", way);
		temp3.setAttribute("building", "yes");
		temp3.setAttribute("layer", "0");
		temp3.setAttribute("mp", "multipolygon");
		OSMRelation relation = temp3.build();
		
		for (int i = 0; i < way.nodes().size(); i++) {
			assertEquals(way.nodes().get(i).position().latitude(), map.ways().get(0).nodes().get(i).position().latitude(), 0.000001);
			assertEquals(way.nodes().get(i).position().longitude(), map.ways().get(0).nodes().get(i).position().longitude(), 0.000001);
		}
		
		for (int i = 0; i < relation.members().size(); i++) {
			assertEquals(relation.members().get(i).type(), map.relations().get(0).members().get(i).type());
			assertEquals(relation.members().get(i).role(), map.relations().get(0).members().get(i).role());
		}
		
		assertEquals(node3.attributes().get("barrier"), map.ways().get(0).nodes().get(2).attributes().get("barrier"));
		assertEquals(node3.attributes().get("bicycle"), map.ways().get(0).nodes().get(2).attributes().get("bicycle"));
		assertEquals(node3.attributes().get("building"), map.ways().get(0).nodes().get(2).attributes().get("building"));
		
		assertEquals(way.attributes().get("layer"), map.ways().get(0).attributes().get("layer"));
		assertEquals(way.attributes().get("source"), map.ways().get(0).attributes().get("source"));
		
		assertEquals(relation.attributes().get("building"), map.relations().get(0).attributes().get("building"));
		assertEquals(relation.attributes().get("layer"), map.relations().get(0).attributes().get("layer"));
		assertEquals(relation.attributes().get("mp"), map.relations().get(0).attributes().get("mp"));
	}
}
