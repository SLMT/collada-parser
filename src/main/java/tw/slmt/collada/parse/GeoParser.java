package tw.slmt.collada.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GeoParser extends ParserBase {

	protected void geomertries(Node node) {
		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("asset"))
				// XXX: Not implemented
				notImplemented("<asset>");

			else if (nodeName.equals("geometry"))
				geometry(childNode);

			else if (nodeName.equals("extra"))
				// XXX: Not implemented
				notImplemented("<extra>");
		}
		
		// TODO: It should return something
	}
	
	protected void geometry(Node node) {
		// TODO: Implement this (in spec. p.82)
	}
}
