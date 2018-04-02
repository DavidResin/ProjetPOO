package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Une liste d'attributs associés à des clés
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class Attributes {
	private final Map<String, String> attributes;

	/**
	 * Construit une liste d'attributs à partir de la liste d'attributs donnée en paramètre
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
	 * Vérifie si la liste est vide
	 * 
	 * @return True si la liste est vide, False sinon
	 */
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	/**
	 * Vérifie si la liste contient la String donnée en argument
	 * 
	 * @param key
	 * 				La String à rechercher
	 * @return True si la String apparaît dans la liste, False sinon
	 */
	public boolean contains(String key) {
		return attributes.containsKey(key);
	}

	/**
	 * Retourne la String associée à la clé passée en argument, retourne null si cette clé n'existe pas
	 * 
	 * @param key
	 * 				La clé à chercher
	 * @return La String associée si la clé existe, null sinon
	 */
	public String get(String key) {
		return attributes.get(key);
	}

	/**
	 * Retourne la String associée à la clé passée en argument, retourne la valeur par défaut si cette clé n'existe pas
	 * 
	 * @param key
	 * 				La clé à chercher
	 * @param defaultValue
	 * 				La valeur par défaut
	 * @return La string si la clé existe, la valeur par défaut sinon
	 */
	public String get(String key, String defaultValue) {
		return attributes.getOrDefault(key, defaultValue);
	}

	/**
	 * Retourne une conversion en int de la String associée à la clé passée en argument, retourne la valeur par défaut si cette clé n'existe pas, ou si la valeur n'est pas convertissable en int
	 * 
	 * @param key
	 * 				La clé à chercher
	 * @param defaultValue
	 * 				La valeur par défaut
	 * @return La conversion en int de la string si la clé existe, la valeur par défaut sinon, ou si la valeur associée à la clé n'est pas convertissable en int
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
	 * Retourne un Attributes ne contenant que les clés se trouvant dans this et dans le Set passé en argument
	 * 
	 * @param keysToKeep
	 * 				Le Set à comparer
	 * @return
	 * 				Le Attributes ne contenant que les clés se trouvant dans this et dans le Set passé en argument
	 */
	public Attributes keepOnlyKeys(Set<String> keysToKeep) {
		Map<String, String> temp = new HashMap<>();
		
		for (String s : keysToKeep) {
			if (attributes.containsKey(s)) {
				temp.put(s, attributes.get(s));
			}
		}
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
		 * Ajoute un couple clé + valeur à la liste d'attributs, remplace l'ancienne valeur si la clé entrée était déjà utilisée
		 * 
		 * @param key
		 * 				La clé à ajouter
		 * @param value
		 * 				La valeur associée à ajouter
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
