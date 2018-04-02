package ch.epfl.imhof.osm;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class OSMNodeTest {
	private static final double DELTA = 0.000001;
	
	@Test
	public void buildIsCorrect() {
		new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
	}

	@Test
	public void positionGetterGetsRightPosition() {
		OSMNode test = new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>()));
		assertEquals(2, test.position().longitude(), DELTA);
		assertEquals(1, test.position().latitude(), DELTA);
	}
	
	@Test
	public void builderBuildIsCorrect() {
		new OSMNode.Builder(1, new PointGeo(1, 1));
	}
	
	@Test
	public void buildMethodIsCorrect() {
		OSMNode.Builder test = new OSMNode.Builder(1, new PointGeo(1, 1));
		test.build();
	}
	
	@Test (expected = IllegalStateException.class)
	public void buildMethodThrowsExceptionWhenIncomplete() {
		OSMNode.Builder test = new OSMNode.Builder(1, new PointGeo(1, 1));
		test.setIncomplete();
		test.build();
	}
}
