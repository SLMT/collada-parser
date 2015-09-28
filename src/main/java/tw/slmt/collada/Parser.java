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
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("asset"))
				asset(childNode);
			else if (nodeName.equals("library_materials"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_effects"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_geometries"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("library_visual_scenes"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("scene"))
				;
		}

		// TODO: Wait for implementation
		return null;
	}

	public Metadata asset(Node node) {
		Metadata meta = new Metadata();
		
		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("contributor"))
				meta.addContributor(contributor(childNode));
			else if (nodeName.equals("coverage"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("created"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("keywords"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("modified"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("revision"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("subject"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("title"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("unit"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("up_axis"))
				; // TODO: Wait for implementation
			else if (nodeName.equals("extra"))
				; // TODO: Wait for implementation
		}

		return meta;
	}

	public Contributor contributor(Node node) {
		Contributor con = new Contributor();
		
		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();
			
			if (nodeName.equals("author"))
				con.author = childNode.getTextContent();
			else if (nodeName.equals("author_email"))
				con.author_email = childNode.getTextContent();
			else if (nodeName.equals("author_website"))
				con.author_website = childNode.getTextContent();
			else if (nodeName.equals("authoring_tool"))
				con.authoring_tool = childNode.getTextContent();
			else if (nodeName.equals("comments"))
				con.comments = childNode.getTextContent();
			else if (nodeName.equals("copyright"))
				con.copyright = childNode.getTextContent();
			else if (nodeName.equals("source_data"))
				con.source_data = childNode.getTextContent();
		}

		return con;
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
