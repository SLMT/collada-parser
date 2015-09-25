package tw.slmt.collada;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	private Document xmlDoc;

	public Parser(InputStream xmlStream) throws IOException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlStream);
		} catch (ParserConfigurationException e) {
			new RuntimeException(
					"Something went wrong. Please contact the developer.");
		} catch (SAXException e) {
			throw new ParseException(e.getMessage());
		}
	}

	public ColladaObject parseToColladaObject() {

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
			Node childNode = childNodes.item(0);
			
			// TODO: Finish this
		}

		return null;
	}

	private void throwFormatError(String detail) {
		throw new ParseException("Document format error: " + detail);
	}

	private String retrieveAttribute(Node node, String attrName) {
		if (!node.hasAttributes())
			throwFormatError("The node '" + node.getNodeName()
					+ "' should have a attribute '" + attrName + "'.");

		return node.getAttributes().getNamedItem(attrName).getNodeValue();
	}
}
