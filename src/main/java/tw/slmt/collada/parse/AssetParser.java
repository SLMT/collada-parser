package tw.slmt.collada.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tw.slmt.collada.parse.Metadata.UpAxis;

public class AssetParser extends ParserBase {

	protected Metadata asset(Node node) {
		Metadata meta = new Metadata();

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("contributor"))
				meta.addContributor(contributor(childNode));
			
			else if (nodeName.equals("coverage"))
				// XXX: Not implemented
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
				// XXX: Not implemented
				notImplemented("<extra>");
		}

		return meta;
	}

	protected Contributor contributor(Node node) {
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
}
