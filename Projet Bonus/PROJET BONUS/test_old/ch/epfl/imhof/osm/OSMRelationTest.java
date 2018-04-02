package ch.epfl.imhof.osm;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.osm.OSMRelation.Member.Type;

public class OSMRelationTest {

	private static final Type DUMMY = null;
	private static final Double DELTA = 0.000001;

	@Test
	public void buildIsCorrect() {
		new OSMRelation(1, new ArrayList<OSMRelation.Member>(), new Attributes(new HashMap<String, String>()));
	}

	@Test
	public void membersGetterReturnsCorrectMembers() {
		List<OSMRelation.Member> members = new ArrayList<OSMRelation.Member>();
		members.add(new OSMRelation.Member(DUMMY, "role", new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>()))));
		OSMRelation test = new OSMRelation(1, members, new Attributes(new HashMap<String, String>()));
		assertEquals(members, test.members());
	}
	
	@Test
	public void memberBuildIsCorrect() {
		new OSMRelation.Member(DUMMY, "role", new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>())));
	}
	
	@Test
	public void typeGetterGetsCorrectType() {
		OSMRelation.Member test = new OSMRelation.Member(DUMMY, "role", new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>())));
		assertNull(test.type());
	}
	
	@Test
	public void roleGetterGetsCorrectRole() {
		OSMRelation.Member test = new OSMRelation.Member(DUMMY, "role", new OSMNode(1, new PointGeo(2, 1), new Attributes(new HashMap<String, String>())));
		assertEquals("role", test.role());
	}
	
	@Test
	public void memberGetterGetsCorrectMember() {
		Attributes attr = new Attributes(new HashMap<String, String>());
		OSMNode member = new OSMNode(1, new PointGeo(2, 1), attr);
		OSMRelation.Member test = new OSMRelation.Member(DUMMY, "role", member);
		assertEquals(1, test.member().id(), DELTA);
		assertEquals(attr, test.member().attributes());
	}
	
	@Test
	public void builderBuildIsCorrect() {
		new OSMRelation.Builder(1);
	}
	
	@Test
	public void addMemberAddsCorrectly() {
		Attributes attr = new Attributes(new HashMap<String, String>());
		OSMNode member = new OSMNode(1, new PointGeo(2, 1), attr);
		List<Member> members = new ArrayList<Member>();
		members.add(new Member(DUMMY, "role", member));
		OSMRelation.Builder test = new OSMRelation.Builder(1);
		test.addMember(DUMMY, "role", member);
		OSMRelation test2 = test.build();
		assertEquals(1, test2.id(), DELTA);
		assertEquals(1, test2.members().get(0).member().id());
		assertEquals(attr, test2.members().get(0).member().attributes());
	}
	
	@Test
	public void builderBuildMethodIsCorrect() {
		Attributes attr = new Attributes(new HashMap<String, String>());
		OSMNode member = new OSMNode(1, new PointGeo(2, 1), attr);
		List<Member> members = new ArrayList<Member>();
		members.add(new Member(DUMMY, "role", member));
		OSMRelation.Builder test = new OSMRelation.Builder(1);
		test.addMember(DUMMY, "role", member);
		test.build();
	}
	
	@Test (expected = IllegalStateException.class)
	public void builderBuildMethodFailsWhenIncomplete() {
		Attributes attr = new Attributes(new HashMap<String, String>());
		OSMNode member = new OSMNode(1, new PointGeo(2, 1), attr);
		List<Member> members = new ArrayList<Member>();
		members.add(new Member(DUMMY, "role", member));
		OSMRelation.Builder test = new OSMRelation.Builder(1);
		test.addMember(DUMMY, "role", member);
		test.setIncomplete();
		test.build();
	}
}
