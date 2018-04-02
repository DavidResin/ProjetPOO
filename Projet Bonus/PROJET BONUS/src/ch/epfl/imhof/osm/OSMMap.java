package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Une OpenStreetMap, représentée par des OSMWay et des OSMRelation
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMMap {
	private final List<OSMWay> ways;
	private final List<OSMRelation> relations;

	/**
	 * Construit une OSMMap à partir d'une collection de OSMWay et une collection de OSMRelation
	 * 
	 * @param ways
	 * 				La collection de OSMWay
	 * @param relations
	 * 				La collection de OSMRelation
	 */
	public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations) {
		this.ways = Collections.unmodifiableList(new ArrayList<>(ways));
		this.relations = Collections.unmodifiableList(new ArrayList<>(relations));
	}

	/**
	 * Le getter de la liste d'OSMWay
	 * 
	 * @return
	 * 				La liste d'OSMWay
	 */
	public List<OSMWay> ways() {
		return ways;
	}

	/**
	 * Le getter de la liste d'OSMRelation
	 * 
	 * @return
	 * 				La liste d'OSMRelation
	 */
	public List<OSMRelation> relations() {
		return relations;
	}

	/**
	 * Un builder de OSMMap
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder {
		private final Map<Long,OSMNode> nodes = new HashMap<>();
		private final Map<Long,OSMWay> ways = new HashMap<>();
		private final Map<Long,OSMRelation> relations = new HashMap<>();


		/**
		 * Ajoute un noeud à la HashMap d'OSMNode
		 * 
		 * @param newNode
		 * 				Le noeud à ajouter
		 */
		public void addNode(OSMNode newNode) {
			nodes.put(newNode.id(), newNode);
		}

		/**
		 * Retourne le noeud correspondant à l'id donné en paramètre s'il se trouve dans la HashMap, ou retourne null sinon
		 * 
		 * @param id
		 * 				L'id à rechercher
		 * @return
		 * 				Le noeud correspondant à l'id s'il existe dans la HashMap, null sinon
		 */
		public OSMNode nodeForId(long id) {
			return nodes.get(id);
		}

		/**
		 * Ajoute un chemin à la HashMap d'OSMWay
		 * 
		 * @param newWay
		 * 				Le chemin à ajouter
		 */
		public void addWay(OSMWay newWay) {
			ways.put(newWay.id(), newWay);
		}

		/**
		 * Retourne le chemin correspondant à l'id donné en paramètre s'il se trouve dans la HashMap, ou retourne null sinon
		 * 
		 * @param id
		 * 				L'id à rechercher
		 * @return
		 * 				Le chemin correspondant à l'id s'il existe dans la HashMap, null sinon
		 */
		public OSMWay wayForId(long id) {
			return ways.get(id);
		}

		/**
		 * Ajoute une relation à la HashMap d'OSMRelation
		 * 
		 * @param newRelation
		 * 				La relation à ajouter
		 */
		public void addRelation(OSMRelation newRelation) {
			relations.put(newRelation.id(), newRelation);
		}

		/**
		 * Retourne la relation correspondant à l'id donné en paramètre si elle se trouve dans la HashMap, ou retourne null sinon
		 * 
		 * @param id
		 * 				L'id à rechercher
		 * @return
		 * 				La relation correspondant à l'id si elle existe dans la HashMap, null sinon
		 */
		public OSMRelation relationForId(long id) {
			return relations.get(id);
		}

		/**
		 * Construit une OSMMap à partir des HashMap de OSMWay et OSMRelation du builder
		 * 
		 * @return
		 * 				Une OSMMap contenant les OSMWay et OSMRelation du builder
		 */
		public OSMMap build() {
			return new OSMMap(ways.values(), relations.values());
		}
	}
}
