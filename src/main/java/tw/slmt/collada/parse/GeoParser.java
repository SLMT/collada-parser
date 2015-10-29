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
		GeometryData geoData = null;

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
				geoData = mesh(childNode);

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
				// TODO: Wait for implementation (spec. p.177)
				notImplemented("<source>");

			} else if (nodeName.equals("vertices")) {
				// TODO: Wait for implementation (spec. p.196)
				notImplemented("<vertices>");

			} else if (nodeName.equals("lines")) {
				// XXX: Not implemented
				notImplemented("<lines>");

			} else if (nodeName.equals("linestrips")) {
				// XXX: Not implemented
				notImplemented("<linestrips>");

			} else if (nodeName.equals("polygons")) {
				// TODO: Wait for implementation (spec. p.152)
				notImplemented("<polygons>");

			} else if (nodeName.equals("polylist")) {
				// XXX: Not implemented
				notImplemented("<polylist>");

			} else if (nodeName.equals("triangles")) {
				// TODO: Wait for implementation (spec. p.188)
				notImplemented("<triangles>");

			} else if (nodeName.equals("trifans")) {
				// XXX: Not implemented
				notImplemented("<trifans>");

			} else if (nodeName.equals("tristrips")) {
				// XXX: Not implemented
				notImplemented("<tristrips>");

			} else if (nodeName.equals("extra"))
				// XXX: Not implemented
				notImplemented("<extra>");
		}

		return meshData;
	}
}
