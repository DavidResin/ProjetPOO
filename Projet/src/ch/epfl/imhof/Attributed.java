package ch.epfl.imhof;

/**
 * Classe generique contenant une liste d'attributs et l'element de type T auquel ils doivent être attribues
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 * 
 * @param <T>
 * 				Le type de l'objet auquel la liste d'attributs est associee
 */
public final class Attributed<T> {
	private final T value;
	private final Attributes attributes;

	/**
	 * Construit un Attributed contenant un objet de type T et un Attribute contenant la liste d'attributs associes a cet objet
	 * 
	 * @param value
	 * 				L'element auquel est associee la liste d'attributs
	 * @param attributes
	 * 				Le groupe d'attibuts a associer a l'element value
	 */
	public Attributed(T value, Attributes attributes) {
		this.value = value;
		this.attributes = attributes;
	}

	/**
	 * Getter de l'objet de type T
	 * 
	 * @return L'objet en question
	 */
	public T value() {
		return value;
	}

	/**
	 * Getter de l'Attributes associe a l'objet
	 * 
	 * @return L'Attributes en question
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * Verifie si oui ou non la liste d'attributs contient le nom d'attribut donne en parametre
	 * 
	 * @param attributeName
	 * 				Le nom d'attribut dont la presence est a verifier
	 * @return
	 * 				True si il se trouve dans la liste, False sinon
	 */
	public boolean hasAttribute(String attributeName) {
		return attributes.contains(attributeName);
	}

	/**
	 * Retourne la valeur associee au nom donne en parametre si ce nom est present dans la liste d'attributs
	 * 
	 * @param attributeName
	 * 				Le nom a rechercher dans la liste d'attributs
	 * @return
	 * 				La valeur associee a ce nom
	 */
	public String attributeValue(String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * Retourne la valeur associee au nom donne en parametre si ce nom est present dans la liste d'attributs, autrement retourne la valeur par defaut
	 * 
	 * @param attributeName
	 * 				Le nom a rechercher dans la liste d'attributs
	 * @param defaultValue
	 * 				La valeur par defaut
	 * @return
	 * 				La valeur associee a ce nom, la valeur par defaut si ce nom n'est pas trouve	
	 */
	public String attributeValue(String attributeName, String defaultValue) {
		return attributes.get(attributeName, defaultValue);
	}

	/**
	 * Retourne la valeur associee au nom donne en parametre si ce nom est present dans la liste d'attributs, autrement retourne la valeur par defaut si il n'est pas present ou si la valeur n'est pas transcriptible en int
	 * 
	 * @param attributeName
	 * 				Le nom a rechercher dans la liste d'attributs
	 * @param defaultValue
	 * 				La valeur par defaut
	 * @return
	 * 				La valeur associee a ce nom, la valeur par defaut si ce nom n'est pas trouve ou s'il n'est pas transcriptible en int
	 */
	public int attributeValue(String attributeName, int defaultValue) {
		return attributes.get(attributeName, defaultValue);
	}
}
