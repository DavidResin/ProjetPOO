package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Une liste d'attributs associes a des cles
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Attributes {
	private final Map<String, String> attributes;

	/**
	 * Construit une liste d'attributs a partir de la liste d'attributs donnee en parametre
	 * 
	 * @param attributes
	 * 				La liste d'attributs
	 */
	public Attributes(Map<String, String> attributes) {
		Map<String, String> attributesTemp = new HashMap<>();
		attributesTemp.putAll(attributes);
		this.attributes = Collections.unmodifiableMap(attributesTemp);
	}

	/**
	 * Verifie si la liste est vide
	 * 
	 * @return True si la liste est vide, False sinon
	 */
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	/**
	 * Verifie si la liste contient la String donnee en argument
	 * 
	 * @param key
	 * 				La String a rechercher
	 * @return True si la String apparaît dans la liste, False sinon
	 */
	public boolean contains(String key) {
		return attributes.containsKey(key);
	}

	/**
	 * Retourne la String associee a la cle passee en argument, retourne null si cette cle n'existe pas
	 * 
	 * @param key
	 * 				La cle a chercher
	 * @return La String associee si la cle existe, null sinon
	 */
	public String get(String key) {
		return attributes.get(key);
	}

	/**
	 * Retourne la String associee a la cle passee en argument, retourne la valeur par defaut si cette cle n'existe pas
	 * 
	 * @param key
	 * 				La cle a chercher
	 * @param defaultValue
	 * 				La valeur par defaut
	 * @return La string si la cle existe, la valeur par defaut sinon
	 */
	public String get(String key, String defaultValue) {
		return attributes.getOrDefault(key, defaultValue);
	}

	/**
	 * Retourne une conversion en int de la String associee a la cle passee en argument, retourne la valeur par defaut si cette cle n'existe pas, ou si la valeur n'est pas convertissable en int
	 * 
	 * @param key
	 * 				La cle a chercher
	 * @param defaultValue
	 * 				La valeur par defaut
	 * @return La conversion en int de la string si la cle existe, la valeur par defaut sinon, ou si la valeur associee a la cle n'est pas convertissable en int
	 */
	public int get(String key, int defaultValue) {
		try {
			return Integer.parseInt(attributes.get(key));
		}
		catch (NullPointerException | NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * Retourne un Attributes ne contenant que les cles se trouvant dans this et dans le Set passe en argument
	 * 
	 * @param keysToKeep
	 * 				Le Set a comparer
	 * @return
	 * 				Le Attributes ne contenant que les cles se trouvant dans this et dans le Set passe en argument
	 */
	public Attributes keepOnlyKeys(Set<String> keysToKeep) {
		Map<String, String> temp = new HashMap<>();
		
		for (String s : keysToKeep)
			if (attributes.containsKey(s))
			    temp.put(s, attributes.get(s));
		
		return new Attributes(temp);
	}

	/**
	 * Le builder de la liste d'attributs
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 */
	public final static class Builder {
		private Map<String, String> attributes = new HashMap<>();

		/**
		 * Ajoute un couple cle + valeur a la liste d'attributs, remplace l'ancienne valeur si la cle entree etait deja utilisee
		 * 
		 * @param key
		 * 				La cle a ajouter
		 * @param value
		 * 				La valeur associee a ajouter
		 */
		public void put(String key, String value) {
			attributes.put(key, value);
		}

		/**
		 * Construit une liste d'attributs immuable
		 * 
		 * @return La liste d'attributs
		 */
		public Attributes build() {
			return new Attributes(attributes);
		}
	}
}
