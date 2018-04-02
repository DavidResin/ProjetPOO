package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Une relation OpenStreetMap entre différentes OSMEntity
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMRelation extends OSMEntity {
	private final List<Member> members;

	/**
	 * Construit une OSMRelation d'après une id, une liste de membres et des attributs passés en paramètre
	 * 
	 * @param id
	 * 				L'id unique
	 * @param members
	 * 				La liste de membres
	 * @param attributes
	 * 				Les attributs
	 */
	public OSMRelation(long id, List<Member> members, Attributes attributes) {
		super(id, attributes);
		List<Member> membersTemp = new ArrayList<>();
		membersTemp.addAll(members);
		this.members = Collections.unmodifiableList(membersTemp);
	}

	/**
	 * Retourne la liste de membres
	 * 
	 * @return
	 * 				La liste de membres
	 */
	public List<Member> members() {
		return members;
	}

	/**
	 * La classe imbriquée représentant un membre de l'OSMRelation
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Member {
		private final Type type;
		private final String role;
		private final OSMEntity member;

		/**
		 * Construit un membre d'après un type énuméré, une String représentant le rôle et une OSMEntity
		 * 
		 * @param type
		 * 				Le type (peut valoir NODE, RELATION ou WAY)
		 * @param role
		 * 				Le rôle du membre
		 * @param member
		 * 				L'OSMEntity du membre
		 */
		public Member(Type type, String role, OSMEntity member) {
			this.type = type;
			this.role = role;
			this.member = member;
		}

		/**
		 * Retourne le type
		 * 
		 * @return
		 * 				Le type
		 */
		public Type type() {
			return type;
		}

		/**
		 * Retourne le rôle
		 * 
		 * @return
		 * 				Le rôle
		 */
		public String role() {
			return role;
		}

		/**
		 * Retourne l'OSMEntity du membre
		 * 
		 * @return
		 * 				L'OSMEntity
		 */
		public OSMEntity member() {
			return member;
		}

		/**
		 * Une énumération des trois types possibles, à savoir NODE, RELATION ou WAY
		 * 
		 * @author Magaly Abboud (249344)
		 * @author David Resin (225452)
		 */
		public enum Type {
			NODE, RELATION, WAY;
		}
	}

	/**
	 * Le builder de OSMRelation
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder extends OSMEntity.Builder {
		private final List<Member> members = new ArrayList<>();

		/**
		 * Construit un builder d'OSMRelation
		 * 
		 * @param id
		 */
		public Builder(long id) {
			super(id);
		}

		/**
		 * Ajoute un nouveau membre à la liste de membre
		 * 
		 * @param type
		 * 				Le type du nouveau membre
		 * @param role
		 * 				Le rôle du nouveau membre
		 * @param newMember
		 * 				L'OSMEntity du nouveau membre
		 */
		public void addMember(Member.Type type, String role, OSMEntity newMember) {
			members.add(new Member(type, role, newMember));
		}

		/**
		 * Construit une nouvelle OSMRelation à partir des éléments du builder
		 * 
		 * @return
		 * 				L'OSMRelation
		 * @throws IllegalStateException
		 * 				Si l'entité est incomplète
		 */
		public OSMRelation build() throws IllegalStateException {
			if (isIncomplete()) {
				throw new IllegalStateException("L'OSMRelation est incomplète");
			}
			return new OSMRelation(id, members, attributes.build());
		}
	}
}
