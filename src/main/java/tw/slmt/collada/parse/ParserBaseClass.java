package tw.slmt.collada.parse;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class ParserBaseClass {
	private static final Logger logger = LogManager
			.getLogger(ParserBaseClass.class);

	protected Set<String> wordSet(String words) {
		StringTokenizer st = new StringTokenizer(words);
		Set<String> results = new HashSet<String>();

		while (st.hasMoreTokens())
			results.add(st.nextToken());

		return results;
	}

	protected void throwFormatError(String detail) {
		if (logger.isErrorEnabled())
			logger.error("Document format error: " + detail);
		throw new ParseException("Document format error: " + detail);
	}

	protected void notImplemented(String component) {
		if (logger.isWarnEnabled())
			logger.warn("Parsing task for '" + component
					+ "' node is not implemented.");
	}

	protected String retrieveAttribute(Node node, String attrName,
			boolean isNecessary) {
		if (!node.hasAttributes()) {
			if (isNecessary)
				throwFormatError("The node '" + node.getNodeName()
						+ "' should have a attribute '" + attrName + "'.");
			else
				return null;
		}

		Node attrNode = node.getAttributes().getNamedItem(attrName);
		if (attrNode == null) {
			if (isNecessary)
				throwFormatError("The node '" + node.getNodeName()
						+ "' should have a attribute '" + attrName + "'.");
			else
				return null;
		}

		return attrNode.getNodeValue();
	}

	protected int retrieveAttributeAsInt(Node node, String attrName,
			boolean isNecessary, int defaultVal) {
		String valStr = retrieveAttribute(node, attrName, false);
		
		if (valStr == null)
			return defaultVal;
		
		int val = Integer.parseInt(valStr);
		return val;
	}

	protected Node getSpecifiedChildNode(Node parent, String nodeName) {
		NodeList childNodes = parent.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			if (childNode.getNodeName().equals(nodeName))
				return childNode;
		}

		throwFormatError("Cannot find the node <" + nodeName + "> in <"
				+ parent.getNodeName() + ">.");
		
		return null;
	}
}
