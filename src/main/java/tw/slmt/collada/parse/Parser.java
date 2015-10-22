package tw.slmt.collada.parse;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser extends ParserBase {
	
	private AssetParser asPsr = new AssetParser();
	
	// TODO: Add a overloaded method for parsing String object.
	// It should be noted that the method have to handle the IOException itself.
	
	public ColladaObject parseToColladaObject(InputStream xmlStream) throws IOException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xmlDoc = dBuilder.parse(xmlStream);
			
			return parseToColladaObject(xmlDoc);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(
					"Something went wrong. Please contact the developer.");
		} catch (SAXException e) {
			throw new ParseException(e.getMessage());
		}
	}

	public ColladaObject parseToColladaObject(Document xmlDoc) {
		ColladaObject result = new ColladaObject();

		// Check if there is a Collada node
		NodeList childNodes = xmlDoc.getChildNodes();
		if (childNodes.getLength() < 1
				|| !childNodes.item(0).getNodeName().equals("COLLADA"))
			throwFormatError("There is no collada node.");

		// Get the Collada node
		Node colladaNode = xmlDoc.getChildNodes().item(0);

		// Parse the child nodes
		childNodes = colladaNode.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("asset"))
				result.metadata = asPsr.asset(childNode);
			else if (nodeName.equals("library_geometries"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_materials"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_effects"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_visual_scenes"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("scene"))
				;
		}

		return result;
	}
}
