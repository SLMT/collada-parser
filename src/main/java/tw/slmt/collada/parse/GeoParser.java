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

			if (nodeName.equals("asset"))
				// XXX: Not implemented
				notImplemented("<asset>");

			else if (nodeName.equals("geometry")) {
				GeometryData geoData = geometry(childNode);
				geomertiesData.add(geoData);

			} else if (nodeName.equals("extra"))
				// XXX: Not implemented
				notImplemented("<extra>");
		}

		return geomertiesData;
	}

	private GeometryData geometry(Node node) {
		GeometryData geoData = new GeometryData();

		// Check the attributes
		geoData.id = retrieveAttribute(node, "id", false);
		geoData.name = retrieveAttribute(node, "name", false);

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("asset"))
				// XXX: Not implemented
				notImplemented("<asset>");
			
			// ======================================
			// The followings are geomertric elements
			// ======================================
			
			else if (nodeName.equals("convex_mesh")) {
				// XXX: Not implemented
				notImplemented("<convex_mesh>");
			
			// The most common type
			} else if (nodeName.equals("mesh")) { 
				mesh(childNode);
			
			} else if (nodeName.equals("spline")) {
				// XXX: Not implemented
				notImplemented("<spline>");
			
			} else if (nodeName.equals("brep")) {
				// XXX: Not implemented
				notImplemented("<brep>");
			
			} else if (nodeName.equals("extra"))
				// XXX: Not implemented
				notImplemented("<extra>");
		}

		return geoData;
	}
	
	private void mesh(Node node) {
		// TODO: Implement this (spec. p.129)
	}
}
