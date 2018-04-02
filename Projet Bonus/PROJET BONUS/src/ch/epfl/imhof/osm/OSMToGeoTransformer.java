package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.projection.Projection;

/**
 * Un convertisseur d'entités OSM en entités géométriques
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMToGeoTransformer {
	private Projection projection;
	private final static String[] CONFIRM = {"yes", "1", "true"};
	private final static Set<String> POLYGONATTR = new HashSet<>(Arrays.asList("building", "landuse", "layer", "leisure", "natural", "waterway"));
	private final static Set<String> POLYLINEATTR = new HashSet<>(Arrays.asList("bridge", "highway", "layer", "man_made", "railway", "tunnel", "waterway"));	
	private final static Set<String> WAYATTR = new HashSet<>(Arrays.asList("aeroway", "amenity", "building", "harbour", "historic",
																				"landuse", "leisure", "man_made", "military", "natural",
																				"office", "place", "power", "public_transport", "shop",
																				"sport", "tourism", "water", "waterway", "wetland"));								

	/**
	 * Le constructeur de convertisseur, qui prend une projection en attribut
	 * 
	 * @param projection
	 * 				La projection que le convertisseur devra utiliser
	 */
	public OSMToGeoTransformer(Projection projection) {
		this.projection = projection;
	}

	/**
	 * Transforme une OSMMap en une Map composée d'entités géométriques
	 * 
	 * @param map
	 * 			La OSMMap à transformer
	 * @return
	 * 			La Map contenant les polylignes construites à partir des OSMEntity de l'OSMMap
	 */
	public Map transform(OSMMap map) {
		PolyLine.Builder tempPolyLine;
		Map.Builder tempMap = new Map.Builder();
		List<Attributed<Polygon>> polygons;

		/*
		 * Parcourt les OSMWay de la OSMMap, et les convertit en polylignes
		 * ouvertes ou fermées selon si ces OSMWay décrivent des surfaces ou
		 * non.
		 */
		for (OSMWay way : map.ways()) {
			tempPolyLine = new PolyLine.Builder();

			for (OSMNode node : way.nonRepeatingNodes()) {
				tempPolyLine.addPoint(projection.project(node.position()));
			}

			if (way.isClosed() && ((way.hasAttribute("area") && Arrays.asList(CONFIRM).contains(way.attributeValue("area"))) || !way.attributes().keepOnlyKeys(WAYATTR).isEmpty())) {
				if (!way.attributes().keepOnlyKeys(POLYGONATTR).isEmpty()) {
					tempMap.addPolygon(new Attributed<Polygon>(new Polygon(tempPolyLine.buildClosed()), way.attributes().keepOnlyKeys(POLYGONATTR)));
				}
			}
			else if (!way.attributes().keepOnlyKeys(POLYLINEATTR).isEmpty()) {
				tempMap.addPolyLine(new Attributed<PolyLine>(tempPolyLine.buildOpen(), way.attributes().keepOnlyKeys(POLYLINEATTR)));
			}
		}

		/*
		 * Convertit chaque OSMRelation en lot de polygones si elle décrit un
		 * multipolygone et que ses attributs sont valides.
		 */
		for (OSMRelation relation : map.relations()) {
			if (!relation.attributes().keepOnlyKeys(POLYGONATTR).isEmpty() && relation.attributes().get("type", "").equals("multipolygon")) {
				polygons = assemblePolygon(relation, relation.attributes().keepOnlyKeys(POLYGONATTR));

				for (Attributed<Polygon> poly : polygons) {
					tempMap.addPolygon(poly);
				}
			}
		}
		Map newMap = tempMap.build();
		return newMap;
	}

	/**
	 * Construit des polylignes correspondant aux membres (au rôle donné en argument) de la relation donnée en argument
	 * 
	 * @param relation
	 * 				La relation dont les données sont extraites
	 * @param role
	 * 				Le rôle des éléments recherchés
	 * @return
	 * 				Une liste de polylignes si chaque node a exactement 2 voisins, retourne une liste vide sinon
	 */
	private List<ClosedPolyLine> ringsForRole(OSMRelation relation, String role) {
		Graph.Builder<OSMNode> builder = new Graph.Builder<>();
		OSMWay way;
		List<OSMNode> nodes;

		/*
		 * Parcourt toutes les OSMWay de la OSMRelation, en extrait les nodes,
		 * et les place dans un graphe non-orienté, liés à leur voisins comme
		 * ils l'étaient dans leurs OSMWay respectives.
		 */
		for (Member member : relation.members()) {
			if (member.type().equals(Member.Type.WAY) && member.role().equals(role)) {
				way = (OSMWay)member.member();
				nodes = way.nodes();
				builder.addNode(way.firstNode());

				for (int i = 1; i < way.nodesCount() - 1; ++i) {
					builder.addNode(nodes.get(i));
					builder.addEdge(nodes.get(i - 1), nodes.get(i));
				}

				if (way.isClosed()) {
					builder.addEdge(nodes.get(way.nodesCount() - 2), way.firstNode());
				}
				else {
					builder.addNode(way.lastNode());
					builder.addEdge(nodes.get(way.nodesCount() - 2), way.lastNode());
				}
			}
		}
		Graph<OSMNode> graph = builder.build();

		PolyLine.Builder newLine;
		OSMNode node;
		Set<OSMNode> memory = new HashSet<>(graph.nodes());
		Set<OSMNode> neighbors = new HashSet<>();
		List<ClosedPolyLine> lines = new ArrayList<>();
		
		/*
		 * Construit des polylignes à partir des points du graphe non-orienté.
		 * Si tous les points n'ont pas exactement 2 voisins, renverra une liste
		 * vide.
		 */
		while (!memory.isEmpty()) {
			node = memory.iterator().next();

			if (graph.neighborsOf(node).size() != 2) {
				return Collections.emptyList();
			}
			newLine = new PolyLine.Builder();
			newLine.addPoint(projection.project(node.position()));
			memory.remove(node);
			neighbors.clear();
			neighbors.addAll(graph.neighborsOf(node));
			neighbors.retainAll(memory);

			while (!neighbors.isEmpty()) {
				node = neighbors.iterator().next();

				if (graph.neighborsOf(node).size() != 2) {
					return Collections.emptyList();
				}
				newLine.addPoint(projection.project(node.position()));
				memory.remove(node);
				neighbors.clear();
				neighbors.addAll(graph.neighborsOf(node));
				neighbors.retainAll(memory);
			}
			lines.add(newLine.buildClosed());
		}
		return lines;
	}

	/**
	 * Retourne une liste de Attributed<Polygon> construits à partir des listes polylignes extraites de la relation, avec les attributs données en argument
	 * 
	 * @param relation
	 * 				La relation dont les données sont extraites
	 * @param attributes
	 * 				Les attributs à associer à chaque polygone
	 * @return
	 * 				Une liste de Attributed<Polygon>
	 */
	public List<Attributed<Polygon>> assemblePolygon(OSMRelation relation, Attributes attributes) {
		Comparator<ClosedPolyLine> c = (a, b) -> Double.compare(a.area(), b.area());
		List<Attributed<Polygon>> list = new ArrayList<>();
		List<ClosedPolyLine> holes;
		ClosedPolyLine temp;

		List<ClosedPolyLine> inner = ringsForRole(relation, "inner");
		List<ClosedPolyLine> outer = ringsForRole(relation, "outer");
		Collections.sort(inner, c);
		Collections.sort(outer, c);

		/*
		 * Parcourt les polylignes externes de la plus petite à la plus grande
		 * et associe à la polyligne courante toutes les polylignes internes
		 * pas encore assignées se trouvant à l'intérieur de cette dernière.
		 */
		for (ClosedPolyLine outerLine : outer) {
			holes = new ArrayList<>();
			Iterator<ClosedPolyLine> it = inner.iterator();

			while (it.hasNext()) {
				temp = it.next();

				if (temp.area() >= outerLine.area()) {
					break;
				}
				if (outerLine.containsPoint(temp.firstPoint())) {
					holes.add(temp);
					it.remove();
				}
			}
			list.add(new Attributed<Polygon>(new Polygon(outerLine, holes), attributes));
		}
		return list;
	}
}
