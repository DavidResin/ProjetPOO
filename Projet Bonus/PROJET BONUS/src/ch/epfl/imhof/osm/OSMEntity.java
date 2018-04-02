package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * Une entité OpenStreetMap abstraite définie par un id unique et une liste d'attributs
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public abstract class OSMEntity {
	private final long id;
	private final Attributes attributes;

	/**
	 * Construit une entité avec un id et une liste d'attributs donnés en paramètre
	 * 
	 * @param id
	 * 				L'identifiant unique de l'entité
	 * @param attributes
	 * 				La liste d'attributs de l'entité
	 */
	public OSMEntity(long id, Attributes attributes) {
		this.id = id;
		this.attributes = attributes;
	}

	/**
	 * Retourne l'identifiant de l'entité
	 * 
	 * @return
	 * 				L'identifiant de l'entité
	 */
	public long id() {
		return id;
	}

	/**
	 * Retourne la liste d'attributs de l'entité
	 * 
	 * @return
	 * 				La liste d'attributs de l'entité
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * Vérifie la présence dans la liste d'un attribut portant le nom donné en paramètre
	 * 
	 * @param key
	 * 				La clé à rechercher
	 * @return
	 * 				True si le nom est présent, False sinon
	 */
	public boolean hasAttribute(String key) {
		return attributes.contains(key);
	}

	/**
	 * Retourne la valeur de l'attribut correspondant à la clé donnée en paramètre, ou null si cette clé n'est pas présente
	 * 
	 * @param key
	 * 				La clé à rechercher
	 * @return
	 * 				La valeur de l'attribut correspondant à cette clé si elle est présent, null sinon
	 */
	public String attributeValue(String key) {
		return attributes.get(key);
	}

	/**
	 * Le constructeur de Builder d'entité OpenStreetMap
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public abstract static class Builder {

		protected final long id;
		protected final Attributes.Builder attributes = new Attributes.Builder();
		private boolean incomplete = false;

		/**
		 * Le constructeur de Builder d'entité, qui ne fait qu'attribuer un id à l'entité en construction
		 * 
		 * @param id
		 * 				L'id unique de l'entité
		 */
		public Builder(long id) {
			this.id = id;
		}

		/**
		 * Ajoute un nouvel attribut à l'entité, en prenant le nom et la valeur donnés en paramètre, remplace l'ancienne valeur par la nouvelle si cette clé est déjà présente
		 * 
		 * @param key
		 * 				La clé du nouvel attribut
		 * @param value
		 * 				La valeur du nouvel attribut
		 */
		public void setAttribute(String key, String value) {
			attributes.put(key, value);
		}

		/**
		 * Signale que l'entité en cours de construction est incomplète en changeant l'état de la variable incomplete à True
		 */
		public void setIncomplete() {
			incomplete = true;
		}

		/**
		 * Retourne l'état de l'entité (complète ou incomplète)
		 * 
		 * @return
		 * 				True si l'entité est incomplète, False sinon
		 */
		public boolean isIncomplete() {
			return incomplete;
		}
	}
}
