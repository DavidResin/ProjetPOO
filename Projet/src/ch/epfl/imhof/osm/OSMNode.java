package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

/**
 * Un noeud OpenStreetMap qui herite de OSMEntity, avec en plus une position fixe definie par un PointGeo
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMNode extends OSMEntity {
	private final PointGeo position;

	/**
	 * Construit un noeud OSM en appelant le constructeur de la super-classe pour l'id et la liste d'attributs
	 * 
	 * @param id
	 * 				L'identifiant unique du noeud
	 * @param position
	 * 				La position du noeud en coordonnees spheriques
	 * @param attributes
	 * 				La liste d'attibuts du noeud
	 */
	public OSMNode(long id, PointGeo position, Attributes attributes) {
		super(id, attributes);
		this.position = position;
	}

	/**
	 * Retourne la position du noeud
	 * 
	 * @return
	 * 				La position du noeud
	 */
	public PointGeo position() {
		return position;
	}

	/**
	 * Un builder de OSMNode
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder extends OSMEntity.Builder {
		private final PointGeo position;

		/**
		 * Le constructeur de builder de OSMNode
		 * 
		 * @param id
		 * 				L'id de l'OSMNode
		 * @param position
		 * 				La position de l'OSMNode
		 */
		public Builder(long id, PointGeo position) {
			super(id);
			this.position = position;
		}

		/**
		 * Retourne un OSMNode complete
		 * 
		 * @return
		 * 				Le OSMNode complet
		 * @throws IllegalStateException
		 * 				Si l'entite est incomplete
		 */
		public OSMNode build() throws IllegalStateException {
			if (isIncomplete())
				throw new IllegalStateException("L'OSMNode est incomplet");

			return new OSMNode(id, position, attributes.build());
		}
	}
}
