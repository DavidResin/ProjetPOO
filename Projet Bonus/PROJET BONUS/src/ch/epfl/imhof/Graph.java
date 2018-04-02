package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Un graphe non-orienté composé de noeuds de type N
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 * @param <N>
 * 				Le type des noeuds du graphe
 */
public final class Graph<N> {
	private final Map<N, Set<N>> neighbors;

	/**
	 * Construit un graphe non-orienté à partir des points et relations donnés en paramètre
	 * 
	 * @param neighbors
	 * 				Une map associant des nodes à un set de voisins
	 */
	public Graph(Map<N, Set<N>> neighbors) {
		Map<N, Set<N>> neighborsTemp = new HashMap<>();
		
		for (Map.Entry<N, Set<N>> m : neighbors.entrySet()) {
			neighborsTemp.put(m.getKey(), Collections.unmodifiableSet(new HashSet<>(m.getValue())));
		}
		this.neighbors = Collections.unmodifiableMap(neighborsTemp);
	}

	/**
	 * Retourne une vue de la map contenant les nodes du graphe
	 * 
	 * @return
	 * 				Une vue de la map contenant les nodes du graphe
	 */
	public Set<N> nodes() {
		return neighbors.keySet();
	}

	/**
	 * Retourne le set de voisins associés au node donné en paramètre si celui-ci existe, ou lance une IllegalArgumentException sinon
	 * 
	 * @param node
	 * 				Le node en question
	 * @return
	 * 				Le set contenant les nodes associés au node donné en paramètre
	 * @throws IllegalArgumentException
	 * 				Si le node donné en paramètre ne se trouve pas dans le map
	 */
	public Set<N> neighborsOf(N node) throws IllegalArgumentException {
		if (neighbors.containsKey(node)) {
			return neighbors.get(node);
		}
		throw new IllegalArgumentException("Node introuvable"); 
	}

	/**
	 * Le builder de graphe non-orienté
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 * @param <N>
	 * 				Le type des nodes du graphe
	 */
	public final static class Builder<N> {
		private final Map<N, Set<N>> neighbors = new HashMap<>();

		/**
		 * Ajoute un node à la map (et lui associe un set vide) s'il ne s'y trouve pas déjà
		 * 
		 * @param n
		 * 				Le node à ajouter
		 */
		public void addNode(N n) {
			if (!neighbors.containsKey(n)) {
				neighbors.put(n, new HashSet<N>());
			}
		}

		/**
		 * Lie les deux nodes entrès en paramètre en ajoutant chacun des deux au set associé à l'autre, si et seulement si les deux nodes se trouvent déjà dans le map, ou lance une IllegalArgumentException sinon
		 * 
		 * @param n1
		 * 				Le premier node
		 * @param n2
		 * 				Le second node
		 * @throws IllegalArgumentException
		 * 				Si au moins un des deux nodes ne se trouve pas dans neighbors
		 */
		public void addEdge(N n1, N n2) throws IllegalArgumentException {
			if (neighbors.containsKey(n1) && neighbors.containsKey(n2)) {
				neighbors.get(n1).add(n2);
				neighbors.get(n2).add(n1);
			}
			else throw new IllegalArgumentException("Nodes injoignables");
		}

		/**
		 * Construit et retourne un graphe orienté à partir de la map de voisinage passée en paramètre
		 * 
		 * @return
		 * 				Le graphe orienté ainsi construit
		 */
		public Graph<N> build() {
			return new Graph<>(neighbors);
		}
	}
}
