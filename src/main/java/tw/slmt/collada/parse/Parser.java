package tw.slmt.collada.parse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tw.slmt.collada.parse.Metadata.UpAxis;

public class Parser {
	private static final Logger logger = LogManager.getLogger(Parser.class);

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
				result.metadata = asset(childNode);
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

		return result;
	}

	private Metadata asset(Node node) {
		Metadata meta = new Metadata();

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("contributor"))
				meta.addContributor(contributor(childNode));
			
			else if (nodeName.equals("coverage"))
				// TODO: Not implemented
				notImplemented("<coverage>");
			
			else if (nodeName.equals("created"))
				meta.createdTime = javax.xml.bind.DatatypeConverter
						.parseDateTime(childNode.getTextContent());
			
			else if (nodeName.equals("keywords"))
				meta.keywords = wordSet(childNode.getTextContent());
			
			else if (nodeName.equals("modified"))
				meta.modifiedTime = javax.xml.bind.DatatypeConverter
						.parseDateTime(childNode.getTextContent());
			
			else if (nodeName.equals("revision"))
				// XXX: Not sure what this is for
				meta.revision = childNode.getTextContent();
			
			else if (nodeName.equals("subject"))
				meta.subject = childNode.getTextContent();
			
			else if (nodeName.equals("title"))
				meta.title = childNode.getTextContent();
			
			else if (nodeName.equals("unit")) {
				String meter = retrieveAttribute(childNode, "meter");
				String name = retrieveAttribute(childNode, "name");
				
				meta.metersPerUnit = Double.parseDouble(meter);
				meta.unitName = name;
				
			} else if (nodeName.equals("up_axis")) {
				String value = childNode.getTextContent();
				
				if (value.equals("X_UP"))
					meta.upAxis = UpAxis.X_UP;
				else if (value.equals("Y_UP"))
					meta.upAxis = UpAxis.Y_UP;
				else if (value.equals("Z_UP"))
					meta.upAxis = UpAxis.Z_UP;
				
			} else if (nodeName.equals("extra"))
				// TODO: Not implemented
				notImplemented("<extra>");
		}

		return meta;
	}

	private Contributor contributor(Node node) {
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
	
	private Set<String> wordSet(String words) {
		StringTokenizer st = new StringTokenizer(words);
		Set<String> results = new HashSet<String>();
		
		while (st.hasMoreTokens())
			results.add(st.nextToken());
		
		return results;
	}

	private void throwFormatError(String detail) {
		if (logger.isErrorEnabled())
			logger.error("Document format error: " + detail);
		throw new ParseException("Document format error: " + detail);
	}

	private void notImplemented(String component) {
		if (logger.isWarnEnabled())
			logger.warn("Parsing task for '" + component
					+ "' node is not implemented.");
	}

	private String retrieveAttribute(Node node, String attrName) {
		if (!node.hasAttributes())
			throwFormatError("The node '" + node.getNodeName()
					+ "' should have a attribute '" + attrName + "'.");

		return node.getAttributes().getNamedItem(attrName).getNodeValue();
	}
}
