package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Un graphe non-oriente compose de noeuds de type N
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 * @param <N>
 * 				Le type des noeuds du graphe
 */
public final class Graph<N> {
	private final Map<N, Set<N>> neighbors;

	/**
	 * Construit un graphe non-oriente a partir des points et relations donnes en parametre
	 * 
	 * @param neighbors
	 * 				Une map associant des nodes a un set de voisins
	 */
	public Graph(Map<N, Set<N>> neighbors) {
		Map<N, Set<N>> neighborsTemp = new HashMap<>();
		
		for (Map.Entry<N, Set<N>> m : neighbors.entrySet())
			neighborsTemp.put(m.getKey(), Collections.unmodifiableSet(new HashSet<>(m.getValue())));
		
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
	 * Retourne le set de voisins associes au node donne en parametre si celui-ci existe, ou lance une IllegalArgumentException sinon
	 * 
	 * @param node
	 * 				Le node en question
	 * @return
	 * 				Le set contenant les nodes associes au node donne en parametre
	 * @throws IllegalArgumentException
	 * 				Si le node donne en parametre ne se trouve pas dans le map
	 */
	public Set<N> neighborsOf(N node) throws IllegalArgumentException {
		if (neighbors.containsKey(node))
			return neighbors.get(node);
		
		throw new IllegalArgumentException("Node introuvable"); 
	}

	/**
	 * Le builder de graphe non-oriente
	 * 
	 * @author Magaly Abboud (249344)
	 * @author David Resin (225452)
	 * @param <N>
	 * 				Le type des nodes du graphe
	 */
	public final static class Builder<N> {
		private final Map<N, Set<N>> neighbors = new HashMap<>();

		/**
		 * Ajoute un node a la map (et lui associe un set vide) s'il ne s'y trouve pas deja
		 * 
		 * @param n
		 * 				Le node a ajouter
		 */
		public void addNode(N n) {
			if (!neighbors.containsKey(n))
				neighbors.put(n, new HashSet<N>());
		}

		/**
		 * Lie les deux nodes entres en parametre en ajoutant chacun des deux au set associe a l'autre, si et seulement si les deux nodes se trouvent deja dans le map, ou lance une IllegalArgumentException sinon
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
		 * Construit et retourne un graphe oriente a partir de la map de voisinage passee en parametre
		 * 
		 * @return
		 * 				Le graphe oriente ainsi construit
		 */
		public Graph<N> build() {
			return new Graph<>(neighbors);
		}
	}
}
