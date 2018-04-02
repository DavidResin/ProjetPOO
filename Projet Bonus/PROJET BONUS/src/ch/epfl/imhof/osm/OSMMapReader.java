package ch.epfl.imhof.osm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMMap.Builder;
import ch.epfl.imhof.osm.OSMRelation.Member;

/**
 * Un lecteur de fichier OSM
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public final class OSMMapReader {
	private OSMMapReader() {}

	/**
	 * Retourne le fichier donc le nom est passé en argument et le décompresse si le paramètre unGZip vaut true
	 * 
	 * @param fileName
	 * 				Le nom du fichier
	 * @param unGZip
	 * 				Le paramètre déterminant s'il faut décompresser le fichier
	 * @return
	 * 				Le fichier décompressé
	 * @throws IOException
	 * 				Si le fichier n'existe pas, est corrompu, etc....
	 */
	private static InputStream inputStream(String fileName, boolean unGZip) throws IOException {
		if (unGZip) {
			return new GZIPInputStream(new FileInputStream(fileName));
		}
		return new FileInputStream(fileName);
	}

	/**
	 * Retourne une OSMMap construite à partir du fichier XML passé en paramètre
	 * 
	 * @param fileName
	 * 				Le nom du fichier
	 * @param unGZip
	 * 				Le paramètre déterminant s'il faut décompresser le fichier
	 * @return
	 * 				Une OSMMap construite à partir des instructions du fichier XML dont le nom est passé en paramètre
	 * @throws SAXException
	 * 				Si le fichier contient une erreur de syntaxe
	 * @throws IOException
	 * 				Si le fichier n'existe pas, est corrompu, etc....
	 */
	public static OSMMap readOSMFile(String fileName, boolean unGZip) throws SAXException, IOException {
		XMLReader r = XMLReaderFactory.createXMLReader();
		final OSMMap.Builder builder = new Builder();

		r.setContentHandler(new DefaultHandler() {

		    private OSMEntity.Builder temp;
			private OSMNode.Builder currentNode;
			private OSMWay.Builder currentWay;
			private OSMRelation.Builder currentRelation;
			
			public final static String  NODE = "node", WAY = "way", RELATION = "relation", ND = "nd",
			                            MEMBER = "member", TAG = "tag", ID = "id", LONGITUDE = "lon",
			                            LATITUDE = "lat", REFERENCE = "ref", TYPE = "type", ROLE = "role",
			                            K = "k", V = "v";
			
			private long currentId;
			
			@Override
			public void startElement(String uri, String lName, String qName, Attributes atts) throws SAXException {
				if (atts.getValue(ID) != null) {
					currentId = Long.parseLong(atts.getValue(ID));
				}

				switch (qName) {
				case NODE:
					currentNode = new OSMNode.Builder((currentId), new PointGeo(Math.toRadians(Double.parseDouble(atts.getValue(LONGITUDE))), Math.toRadians(Double.parseDouble(atts.getValue(LATITUDE)))));
					temp = currentNode;
					break;

				case WAY:
					currentWay = new OSMWay.Builder(currentId);
					temp = currentWay;
					break;

				case RELATION:
					currentRelation = new OSMRelation.Builder(currentId);
					temp = currentRelation;
					break;

				case ND:
					if (builder.nodeForId(Long.parseLong(atts.getValue(REFERENCE))) == null) {
					    currentWay.setIncomplete();
					    break;
					}
					currentWay.addNode(builder.nodeForId(Long.parseLong(atts.getValue(REFERENCE))));
					break;

				case MEMBER:
					OSMEntity member;
					switch (atts.getValue(TYPE)) {
					case NODE :
						member = builder.nodeForId(Long.parseLong(atts.getValue(REFERENCE)));
						break;

					case WAY :
						member = builder.wayForId(Long.parseLong(atts.getValue(REFERENCE)));
						break;

					case RELATION :
						member = builder.relationForId(Long.parseLong(atts.getValue(REFERENCE)));
						break;

					default : throw new SAXException("Type de membre invalide");
					}
					if (member == null) currentRelation.setIncomplete();
					else currentRelation.addMember(Member.Type.valueOf(atts.getValue(TYPE).toUpperCase()), atts.getValue(ROLE), member);
					break;

				case TAG:
				    temp.setAttribute(atts.getValue(K), atts.getValue(V));
					break;
				}
			}

			@Override
			public void endElement(String uri, String lName, String qName) {
				switch (qName) {
				case NODE :
					if (!currentNode.isIncomplete()) builder.addNode(currentNode.build());
					break;
				case WAY : 
					if (!currentWay.isIncomplete()) builder.addWay(currentWay.build());
					break;
				case RELATION : 
					if (!currentRelation.isIncomplete()) builder.addRelation(currentRelation.build());
					break;
				}
			}
		});
		try (InputStream i = inputStream(fileName, unGZip)) {
			r.parse(new InputSource(i));
		}
		return builder.build();
	}
}
