package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * Une entite OpenStreetMap abstraite definie par un id unique et une liste d'attributs
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public abstract class OSMEntity {
	private final long id;
	private final Attributes attributes;

	/**
	 * Construit une entite avec un id et une liste d'attributs donnes en parametre
	 * 
	 * @param id
	 * 				L'identifiant unique de l'entite
	 * @param attributes
	 * 				La liste d'attributs de l'entite
	 */
	public OSMEntity(long id, Attributes attributes) {
		this.id = id;
		this.attributes = attributes;
	}

	/**
	 * Retourne l'identifiant de l'entite
	 * 
	 * @return
	 * 				L'identifiant de l'entite
	 */
	public long id() {
		return id;
	}

	/**
	 * Retourne la liste d'attributs de l'entite
	 * 
	 * @return
	 * 				La liste d'attributs de l'entite
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * Verifie la presence dans la liste d'un attribut portant le nom donne en parametre
	 * 
	 * @param key
	 * 				La cle a rechercher
	 * @return
	 * 				True si le nom est present, False sinon
	 */
	public boolean hasAttribute(String key) {
		return attributes.contains(key);
	}

	/**
	 * Retourne la valeur de l'attribut correspondant a la cle donnee en parametre, ou null si cette cle n'est pas presente
	 * 
	 * @param key
	 * 				La cle a rechercher
	 * @return
	 * 				La valeur de l'attribut correspondant a cette cle si elle est present, null sinon
	 */
	public String attributeValue(String key) {
		return attributes.get(key);
	}

	/**
	 * Le constructeur de Builder d'entite OpenStreetMap
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public abstract static class Builder {

		protected final long id;
		protected final Attributes.Builder attributes = new Attributes.Builder();
		private boolean incomplete = false;

		/**
		 * Le constructeur de Builder d'entite, qui ne fait qu'attribuer un id a l'entite en construction
		 * 
		 * @param id
		 * 				L'id unique de l'entite
		 */
		public Builder(long id) {
			this.id = id;
		}

		/**
		 * Ajoute un nouvel attribut a l'entite, en prenant le nom et la valeur donnes en parametre, remplace l'ancienne valeur par la nouvelle si cette cle est deja presente
		 * 
		 * @param key
		 * 				La cle du nouvel attribut
		 * @param value
		 * 				La valeur du nouvel attribut
		 */
		public void setAttribute(String key, String value) {
			attributes.put(key, value);
		}

		/**
		 * Signale que l'entite en cours de construction est incomplete en changeant l'etat de la variable incomplete a True
		 */
		public void setIncomplete() {
			incomplete = true;
		}

		/**
		 * Retourne l'etat de l'entite (complete ou incomplete)
		 * 
		 * @return
		 * 				True si l'entite est incomplete, False sinon
		 */
		public boolean isIncomplete() {
			return incomplete;
		}
	}
}
