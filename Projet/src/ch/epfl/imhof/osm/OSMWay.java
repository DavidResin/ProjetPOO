package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.Attributes;

/**
 * Un chemin compose d'une serie de noeuds OSM
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMWay extends OSMEntity {
	private final List<OSMNode> nodes;

	/**
	 * Construit un OSMWay a l'aide d'un id unique, une list de noeuds OSM, et des attributs donnees en parametre, lance une exception si il y a moins de 2 noeuds
	 * 
	 * @param id
	 * 				L'id unique de l'OSMWay
	 * @param nodes
	 * 				La liste de OSMNode qui compose le chemin
	 * @param attributes
	 * 				Les attributs de l'OSMWay
	 * @throws IllegalArgumentException
	 * 				Si la taille de nodes est plus petite que 2
	 */
	public OSMWay(long id, List<OSMNode> nodes, Attributes attributes) throws IllegalArgumentException {
		super(id, attributes);
		
		if (nodes.size() < 2)
			throw new IllegalArgumentException("Il y a moins de 2 OSMNodes");

		List<OSMNode> nodeTemp = new ArrayList<>();
		nodeTemp.addAll(nodes);
		this.nodes = Collections.unmodifiableList(nodeTemp);
	}

	/**
	 * Retourne la quantite de noeuds composant le OSMWay
	 * 
	 * @return
	 * 				La taille de la liste de noeuds
	 */
	public int nodesCount() {
		return nodes.size();
	}

	/**
	 * Retourne la liste de noeuds
	 * 
	 * @return
	 * 				La liste de noeuds
	 */
	public List<OSMNode> nodes() {
		return nodes;
	}

	/**
	 * Retourne la liste de noeuds, moins le dernier si il est identique au premier
	 * 
	 * @return
	 * 				La liste de noeuds moins le dernier si il est identique au premier
	 */
	public List<OSMNode> nonRepeatingNodes() {
		if (isClosed())
			return nodes.subList(0, nodes.size() - 1);
		
		return nodes;
	}

	/**
	 * Retourne le premier noeud de la liste
	 * 
	 * @return
	 * 				Le premier noeud de la liste
	 */
	public OSMNode firstNode() {
		return nodes.get(0);
	}

	/**
	 * Retourne le dernier noeud de la liste
	 * 
	 * @return
	 * 				Le dernier noeud de la liste
	 */
	public OSMNode lastNode() {
		return nodes.get(nodes.size() - 1);
	}

	/**
	 * Verifie si le chemin est ferme, c'est-a-dire si le dernier noeud est le même que le premier
	 * 
	 * @return
	 * 				True si le chemin est ferme, False sinon
	 */
	public boolean isClosed() {
		return firstNode().position().equals(lastNode().position());
	}

	/**
	 * Le builder de l'OSMWay
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder extends OSMEntity.Builder {
		private final List<OSMNode> nodes = new ArrayList<>();

		/**
		 * Construit un builder d'OSMWay
		 * 
		 * @param id
		 * 				L'id de l'OSMWay
		 */
		public Builder(long id) {
			super(id);
		}

		/**
		 * Ajoute un noeud a la fin de la liste de OSMNode
		 * 
		 * @param newNode
		 * 				Le noeud a ajouter
		 */
		public void addNode(OSMNode newNode) {
			nodes.add(newNode);
		}

		/**
		 * Redefinit la methode isIncomplete() de OSMEntity.Builder, de façon a ce que la variable incomplete soit True egalement si la taille de nodes est inferieure a 2
		 * 
		 * @return
		 * 				True si la methode IsIncomplete() de OSMEntity.Builder est True ou si la taille de nodes est inferieure a 2, False sinon
		 */
		public boolean isIncomplete() {
			return super.isIncomplete() || nodes.size() < 2;
		}

		/**
		 * Construit un OSMWay d'apres les elements du builder, sauf si l'entite est incomplete, auquel cas une exception est levee
		 * 
		 * @return
		 * 				Le OSMWay construit
		 * @throws IllegalStateException
		 * 				Si l'entite est incomplete
		 */
		public OSMWay build() throws IllegalStateException {
			if (isIncomplete())
				throw new IllegalStateException("L'OSMWay est incomplete");

			return new OSMWay(id, nodes, attributes.build());
		}
	}
}
