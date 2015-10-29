package tw.slmt.collada.parse;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeoParser extends ParserBase {

	protected List<GeometryData> geomertries(Node node) {
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

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("mesh")) {
				geoData = mesh(childNode);
			} else
				notImplemented("<" + nodeName + ">");
		}

		// Check if there is geometry data found
		if (geoData == null)
			throwFormatError("There is no gometry data found in <geomerty> node.");
		
		// Get the id and name
		geoData.id = retrieveAttribute(node, "id", false);
		geoData.name = retrieveAttribute(node, "name", false);

		return geoData;
	}

	private MeshData mesh(Node node) {
		MeshData meshData = new MeshData();

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("source")) {
				SourceData s = source(childNode);
				meshData.source.put(s.id, s);

			} else if (nodeName.equals("vertices")) {
				// TODO: Wait for implementation (spec. p.196)
				notImplemented("<vertices>");

			} else if (nodeName.equals("polygons")) {
				// TODO: Wait for implementation (spec. p.152)
				notImplemented("<polygons>");

			} else if (nodeName.equals("triangles")) {
				// TODO: Wait for implementation (spec. p.188)
				notImplemented("<triangles>");

			} else
				notImplemented("<" + nodeName + ">");
		}

		return meshData;
	}
	
	private SourceData source(Node node) {
		SourceData sourceData = new SourceData();

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("float_array")) {
				// TODO: Wait for implementation (spec. p.77)
				notImplemented("<float_array>");

			} else if (nodeName.equals("technique_common")) {
				// TODO: Wait for implementation (spec. p.186, p.45)
				notImplemented("<technique_common>");

			} else
				notImplemented("<" + nodeName + ">");
		}

		return sourceData;
	}
}
