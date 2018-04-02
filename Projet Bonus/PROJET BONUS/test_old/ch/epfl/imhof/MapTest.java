package ch.epfl.imhof;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

public class MapTest {

	@Test
	public void builderBuildsCorrectly() {
		new Map(new ArrayList<Attributed<PolyLine>>(), new ArrayList<Attributed<Polygon>>());
	}
	
	@Test
	public void polyLinesGetterGetsCorrectPolyLines() {
		List<Attributed<PolyLine>> test = new ArrayList<Attributed<PolyLine>>();
		Map map = new Map(test, new ArrayList<Attributed<Polygon>>());
		assertEquals(test, map.polyLines());
	}
	
	@Test
	public void polygonsGetterGetsCorrectPolygons() {
		List<Attributed<Polygon>> test = new ArrayList<Attributed<Polygon>>();
		Map map = new Map(new ArrayList<Attributed<PolyLine>>(), test);
		assertEquals(test, map.polygons());
	}
	
	@Test
	public void builderBuildMethodBuildsCorrectly() {
		Map.Builder test = new Map.Builder();
		test.build();
	}
	
	@Test
	public void polyLineAdderAddsCorrectly() {
		Map.Builder test = new Map.Builder();
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(0, 0));
		list.add(new Point(1, 1));
		Attributed<PolyLine> polyLine = new Attributed<PolyLine>(new ClosedPolyLine(list), new Attributes(new HashMap<String, String>()));
		test.addPolyLine(polyLine);
		Map map = test.build();
		assertEquals(polyLine, map.polyLines().get(0));
	}
	
	@Test
	public void polygonAdderAddsCorrectly() {
		Map.Builder test = new Map.Builder();
		List<Point> list = new ArrayList<Point>();
		list.add(new Point(0, 0));
		list.add(new Point(1, 1));
		Attributed<Polygon> polygon = new Attributed<Polygon>(new Polygon(new ClosedPolyLine(list)), new Attributes(new HashMap<String, String>()));
		test.addPolygon(polygon);
		Map map = test.build();
		assertEquals(polygon, map.polygons().get(0));
	}
}
