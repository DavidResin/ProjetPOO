package ch.epfl.imhof;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class AttributedTest {

	@Test
	public void constructorAcceptsEmptyAttributes() {
		new Attributed<Integer>(3, new Attributes(new HashMap<String, String>()));
	}

	@Test
	public void valueGetterReturnsCorrectValue() {
		Attributed<Integer> test = new Attributed<Integer>(3, new Attributes(new HashMap<String, String>()));
		Integer myInt = 3;
		assertEquals(myInt, test.value());
	}
	
	@Test
	public void attributesGetterReturnsCorrectAttributes() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		Attributed<Integer> test = new Attributed<Integer>(3, attr);
		assertEquals(attr, test.attributes());
	}
	
	@Test
	public void hasAttributesCorrectlyChecks() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		Attributed<Integer> test = new Attributed<Integer>(3, attr);
		assertTrue(test.hasAttribute("key"));
		assertFalse(test.hasAttribute("hello"));
	}
	
	@Test
	public void attributeValueReturnsCorrectValue() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		Attributed<Integer> test = new Attributed<Integer>(3, attr);
		assertEquals("value", test.attributeValue("key"));
		assertNull(test.attributeValue("hello"));
	}
	
	@Test
	public void attributeValueDefaultReturnsCorrectValue() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "value");
		Attributes attr = builder.build();
		Attributed<Integer> test = new Attributed<Integer>(3, attr);
		assertEquals("value", test.attributeValue("key"));
		assertEquals("default", test.attributeValue("hello", "default"));
	}
	
	@Test
	public void attributeValueIntReturnsCorrectValue() {
		Attributes.Builder builder = new Attributes.Builder();
		builder.put("key", "1");
		builder.put("bouh", "blah");
		Attributes attr = builder.build();
		Attributed<Integer> test = new Attributed<Integer>(3, attr);
		assertEquals(1, test.attributeValue("key", 0));
		assertEquals(0, test.attributeValue("bouh", 0));
		assertEquals(0, test.attributeValue("coucou", 0));
	}
}
