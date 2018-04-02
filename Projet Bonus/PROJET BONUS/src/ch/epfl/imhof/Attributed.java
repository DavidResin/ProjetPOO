package ch.epfl.imhof;

/**
 * Classe générique contenant une liste d'attributs et l'élément de type T auquel ils doivent être attribués
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 * 
 * @param <T>
 * 				Le type de l'objet auquel la liste d'attributs est associée
 */
public final class Attributed<T> {
	private final T value;
	private final Attributes attributes;

	/**
	 * Construit un Attributed contenant un objet de type T et un Attribute contenant la liste d'attributs associés à cet objet
	 * 
	 * @param value
	 * 				L'élément auquel est associée la liste d'attributs
	 * @param attributes
	 * 				Le groupe d'attibuts à associer à l'élément value
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
	 * Getter de l'Attributes associé à l'objet
	 * 
	 * @return L'Attributes en question
	 */
	public Attributes attributes() {
		return attributes;
	}

	/**
	 * Vérifie si oui ou non la liste d'attributs contient le nom d'attribut donné en paramètre
	 * 
	 * @param attributeName
	 * 				Le nom d'attribut dont la présence est à vérifier
	 * @return
	 * 				True si il se trouve dans la liste, False sinon
	 */
	public boolean hasAttribute(String attributeName) {
		return attributes.contains(attributeName);
	}

	/**
	 * Retourne la valeur associée au nom donné en paramètre si ce nom est présent dans la liste d'attributs
	 * 
	 * @param attributeName
	 * 				Le nom à rechercher dans la liste d'attributs
	 * @return
	 * 				La valeur associée à ce nom
	 */
	public String attributeValue(String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * Retourne la valeur associée au nom donné en paramètre si ce nom est présent dans la liste d'attributs, autrement retourne la valeur par défaut
	 * 
	 * @param attributeName
	 * 				Le nom à rechercher dans la liste d'attributs
	 * @param defaultValue
	 * 				La valeur par défaut
	 * @return
	 * 				La valeur associée à ce nom, la valeur par défaut si ce nom n'est pas trouvé	
	 */
	public String attributeValue(String attributeName, String defaultValue) {
		return attributes.get(attributeName, defaultValue);
	}

	/**
	 * Retourne la valeur associée au nom donné en paramètre si ce nom est présent dans la liste d'attributs, autrement retourne la valeur par défaut si il n'est pas présent ou si la valeur n'est pas transcriptible en int
	 * 
	 * @param attributeName
	 * 				Le nom à rechercher dans la liste d'attributs
	 * @param defaultValue
	 * 				La valeur par défaut
	 * @return
	 * 				La valeur associée à ce nom, la valeur par défaut si ce nom n'est pas trouvé ou s'il n'est pas transcriptible en int
	 */
	public int attributeValue(String attributeName, int defaultValue) {
		return attributes.get(attributeName, defaultValue);
	}
}
