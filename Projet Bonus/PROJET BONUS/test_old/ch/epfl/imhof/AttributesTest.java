package ch.epfl.imhof;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class AttributesTest {

	@Test
	public void constructorAcceptsEmptyMaps() {
		new Attributes(new HashMap<String, String>());
	}
	
	@Test
	public void emptyCheckIsCorrect() {
		Map<String, String> temp = new HashMap<String, String>();
		assertTrue(new Attributes(temp).isEmpty());
		temp.put("key", "value");
		assertFalse(new Attributes(temp).isEmpty());
	}
	
	@Test
	public void containsCheckIsCorrect() {
		Map<String, String> temp = new HashMap<String, String>();
		assertFalse(new Attributes(temp).contains("key"));
		temp.put("key", "value");
		assertTrue(new Attributes(temp).contains("key"));
	}
	
	@Test
	public void keyGettersReturnCorrectValues() {
		Map<String, String> temp1 = new HashMap<String, String>();
		assertNull(new Attributes(temp1).get("key"));
		temp1.put("key", "value");
		assertEquals("value", new Attributes(temp1).get("key"));
		
		Map<String, String> temp2 = new HashMap<String, String>();
		assertEquals("default", new Attributes(temp2).get("key", "default"));
		temp2.put("key", "value");
		assertEquals("value", new Attributes(temp2).get("key", "default"));
		
		Map<String, String> temp3 = new HashMap<String, String>();
		assertEquals(0, new Attributes(temp3).get("key1", 0));
		temp3.put("key1", "hello");
		assertEquals(0, new Attributes(temp3).get("key1", 0));
		temp3.put("key2", "1");
		assertEquals(1, new Attributes(temp3).get("key2", 0));
	}
	
	@Test
	public void keepsTheRightKeys() {
		Map<String, String> temp = new HashMap<String, String>();
		Set<String> set = new HashSet<String>();
		assertTrue(new Attributes(temp).keepOnlyKeys(set).isEmpty());
		set.add("key1");
		assertTrue(new Attributes(temp).keepOnlyKeys(set).isEmpty());
		temp.put("key1", "a");
		temp.put("key2", "b");
		assertEquals("a", new Attributes(temp).keepOnlyKeys(set).get("key1"));
		assertNull(new Attributes(temp).keepOnlyKeys(set).get("key2"));
	}
	
	@Test
	public void builderTest() {
		Attributes.Builder temp = new Attributes.Builder();
		temp.put("key", "value");
		assertEquals("value", temp.build().get("key"));
	}
}
