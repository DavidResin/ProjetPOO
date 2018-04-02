package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public class OSMToGeoTransformerTest {

	@Test
	public void builderBuildsCorrectly() {
		new OSMToGeoTransformer(new CH1903Projection());
	}
	
	@Test
	public void openWaysAreIgnored() {
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(new CH1903Projection());
		
		OSMNode node11 = new OSMNode(11, new PointGeo(-3, -0.5), null);
		OSMNode node12 = new OSMNode(12, new PointGeo(-2, -1.5), null);
		OSMNode node13 = new OSMNode(13, new PointGeo(-3, -1.5), null);
		
		OSMWay.Builder temp = new OSMWay.Builder(1);
		temp.addNode(node11);
		temp.addNode(node12);
		temp.addNode(node13);
		temp.setAttribute("area", "yes");
		temp.setAttribute("building", "houba");
		OSMWay way = temp.build();
		
		OSMMap.Builder mapBuilder = new OSMMap.Builder();
		mapBuilder.addWay(way);
		OSMMap osmmap = mapBuilder.build();
		Map map = transformer.transform(osmmap);
		
		assertEquals(0, map.polygons().size());
		assertEquals(0, map.polyLines().size());
	}
	
	@Test
	public void waysMissingAttributesAreIgnored() {
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(new CH1903Projection());
		
		OSMNode node11 = new OSMNode(11, new PointGeo(-3, -0.5), null);
		OSMNode node12 = new OSMNode(12, new PointGeo(-2, -1.5), null);
		OSMNode node13 = new OSMNode(13, new PointGeo(-3, -1.5), null);
		
		OSMWay.Builder temp = new OSMWay.Builder(1);
		temp.addNode(node11);
		temp.addNode(node12);
		temp.addNode(node13);
		temp.addNode(node11);
		OSMWay way = temp.build();
		
		OSMMap.Builder mapBuilder = new OSMMap.Builder();
		mapBuilder.addWay(way);
		OSMMap osmmap = mapBuilder.build();
		Map map = transformer.transform(osmmap);
		
		assertEquals(0, map.polygons().size());
		assertEquals(0, map.polyLines().size());
	}
	
	@Test
	public void correctPolygonWayIsAccepted() {
		Projection projection = new CH1903Projection();
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(projection);
		
		OSMNode node11 = new OSMNode(11, new PointGeo(-3, -0.5), null);
		OSMNode node12 = new OSMNode(12, new PointGeo(-2, -1.5), null);
		OSMNode node13 = new OSMNode(13, new PointGeo(-3, -1.5), null);
		
		OSMWay.Builder temp = new OSMWay.Builder(1);
		temp.addNode(node11);
		temp.addNode(node12);
		temp.addNode(node13);
		temp.addNode(node11);
		temp.setAttribute("area", "yes");
		temp.setAttribute("building", "bla");
		OSMWay way = temp.build();
		
		OSMMap.Builder mapBuilder = new OSMMap.Builder();
		mapBuilder.addWay(way);
		OSMMap osmmap = mapBuilder.build();
		Map map = transformer.transform(osmmap);
		
		assertEquals(0, map.polyLines().size());
		assertEquals(1, map.polygons().size());
		assertTrue(map.polygons().get(0).hasAttribute("building"));
		assertEquals("bla", map.polygons().get(0).attributeValue("building"));
		
		assertEquals(projection.project(node11.position()).x(), map.polygons().get(0).value().shell().points().get(0).x(), 0.000001);
		assertEquals(projection.project(node11.position()).y(), map.polygons().get(0).value().shell().points().get(0).y(), 0.000001);
		assertEquals(projection.project(node12.position()).x(), map.polygons().get(0).value().shell().points().get(1).x(), 0.000001);
		assertEquals(projection.project(node12.position()).y(), map.polygons().get(0).value().shell().points().get(1).y(), 0.000001);
		assertEquals(projection.project(node13.position()).x(), map.polygons().get(0).value().shell().points().get(2).x(), 0.000001);
		assertEquals(projection.project(node13.position()).y(), map.polygons().get(0).value().shell().points().get(2).y(), 0.000001);
	}
	
	@Test
	public void correctPolyLineWayIsAccepted() {
		Projection projection = new CH1903Projection();
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(projection);
		
		OSMNode node11 = new OSMNode(11, new PointGeo(-3, -0.5), null);
		OSMNode node12 = new OSMNode(12, new PointGeo(-2, -1.5), null);
		OSMNode node13 = new OSMNode(13, new PointGeo(-3, -1.5), null);
		
		OSMWay.Builder temp = new OSMWay.Builder(1);
		temp.addNode(node11);
		temp.addNode(node12);
		temp.addNode(node13);
		temp.setAttribute("bridge", "bla");
		OSMWay way = temp.build();
		
		OSMMap.Builder mapBuilder = new OSMMap.Builder();
		mapBuilder.addWay(way);
		OSMMap osmmap = mapBuilder.build();
		Map map = transformer.transform(osmmap);
		
		assertEquals(1, map.polyLines().size());
		assertEquals(0, map.polygons().size());
		assertTrue(map.polyLines().get(0).hasAttribute("bridge"));
		assertEquals("bla", map.polyLines().get(0).attributeValue("bridge"));
		
		assertEquals(projection.project(node11.position()).x(), map.polyLines().get(0).value().points().get(0).x(), 0.000001);
		assertEquals(projection.project(node11.position()).y(), map.polyLines().get(0).value().points().get(0).y(), 0.000001);
		assertEquals(projection.project(node12.position()).x(), map.polyLines().get(0).value().points().get(1).x(), 0.000001);
		assertEquals(projection.project(node12.position()).y(), map.polyLines().get(0).value().points().get(1).y(), 0.000001);
		assertEquals(projection.project(node13.position()).x(), map.polyLines().get(0).value().points().get(2).x(), 0.000001);
		assertEquals(projection.project(node13.position()).y(), map.polyLines().get(0).value().points().get(2).y(), 0.000001);
	}
	
	@Test
	public void rolexLearningCenterTest() throws IOException, SAXException {
		Projection projection = new CH1903Projection();
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(projection);
		
		OSMMap osmmap = OSMMapReader.readOSMFile(getClass().getResource("/maps/lc.osm").getFile(), false);
		Map map = transformer.transform(osmmap);
		
		assertEquals(1, map.polygons().size());
		assertEquals(14, map.polygons().get(0).value().holes().size());
	}
	
	@Test
	public void interlakenNoExceptions() throws IOException, SAXException {
		Projection projection = new CH1903Projection();
		OSMToGeoTransformer transformer = new OSMToGeoTransformer(projection);
		
		OSMMap osmmap = OSMMapReader.readOSMFile(getClass().getResource("/maps/interlaken.osm.gz").getFile(), true);
		Map map = transformer.transform(osmmap);
		
		assertEquals(17903, map.polygons().size());
		assertEquals(56336, map.polyLines().size());
	}
}
