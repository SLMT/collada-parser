package tw.slmt.collada.parse.geo;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tw.slmt.collada.parse.ParserBaseClass;
import tw.slmt.collada.parse.geo.mesh.MeshParser;

public class GeoParser extends ParserBaseClass {

	public List<GeometryData> geomertries(Node node) {
		// XXX: Maybe we can use ids as key to construct a map
		List<GeometryData> geomertiesData = new LinkedList<GeometryData>();

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("geometry")) {
				GeometryData geoData = geometry(childNode);
				geomertiesData.add(geoData);

			} else
				notImplemented("<" + nodeName + ">");
		}

		return geomertiesData;
	}

	private GeometryData geometry(Node node) {	
		GeometryData geoData = null;
		
		// Get the id and name
		String id = retrieveAttribute(node, "id", true);
		String name = retrieveAttribute(node, "name", true);

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("mesh")) {
				MeshParser meshPsr = new MeshParser();
				geoData = meshPsr.mesh(childNode, id, name);
			} else
				notImplemented("<" + nodeName + ">");
		}

		// Check if there is geometry data found
		if (geoData == null)
			throwFormatError("There is no gometry data found in <geomerty> node.");

		return geoData;
	}
}
