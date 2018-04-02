package ch.epfl.imhof.osm;

import static org.junit.Assert.*;
import java.util.HashMap;
import org.junit.Test;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

public class OSMEntityTest {

	@Test
	public void buildIsCorrect() {
		new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
	}
	
	@Test
	public void idGetterReturnsCorrectId() {
		OSMEntity test = new OSMNode(1, new PointGeo(1, 1), new Attributes(new HashMap<String, String>()));
		assertEquals(1, test.id());
	}
	
	@Test
	public void attributesGetterReturnsCorrectAttributes() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		OSMEntity test = new OSMNode(1, new PointGeo(1, 1), attr);
		assertEquals(attr, test.attributes());
	}
	
	@Test
	public void hasAttributesCorrectlyChecks() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		OSMEntity test = new OSMNode(1, new PointGeo(1, 1), attr);
		assertTrue(test.hasAttribute("key"));
		assertFalse(test.hasAttribute("hello"));
	}
	
	@Test
	public void attributeValueReturnsCorrectValue() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		OSMEntity test = new OSMNode(1, new PointGeo(1, 1), attr);
		assertEquals("value", test.attributeValue("key"));
		assertNull(test.attributeValue("hello"));
	}
	
	@Test
	public void builderBuildIsCorrect() {
		new OSMNode.Builder(1, new PointGeo(1, 1));
	}
	
	@Test
	public void setAttributeAddsAttributeCorrecty() {
		OSMNode.Builder test = new OSMNode.Builder(1, new PointGeo(1, 1));
		test.setAttribute("key", "value");
		OSMNode test2 = test.build();
		assertEquals("value", test2.attributeValue("key"));
	}
	
	@Test
	public void isIncompleteChecksCorrectly() {
		OSMNode.Builder test = new OSMNode.Builder(1, new PointGeo(1, 1));
		assertFalse(test.isIncomplete());
	}
	
	@Test
	public void setIncompleteSetsCorrectly() {
		OSMNode.Builder test = new OSMNode.Builder(1, new PointGeo(1, 1));
		test.setIncomplete();
		assertTrue(test.isIncomplete());
	}
}
