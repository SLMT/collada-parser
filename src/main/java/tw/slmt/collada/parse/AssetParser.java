package tw.slmt.collada.parse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.bind.DatatypeConverter;

import tw.slmt.collada.parse.MetadataData.UpAxis;

public class AssetParser extends ParserBase {

	protected MetadataData asset(Node node) {
		MetadataData meta = new MetadataData();

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
				meta.createdTime = DatatypeConverter.parseDateTime(childNode
						.getTextContent());

			else if (nodeName.equals("keywords"))
				meta.keywords = wordSet(childNode.getTextContent());

			else if (nodeName.equals("modified"))
				meta.modifiedTime = DatatypeConverter.parseDateTime(childNode
						.getTextContent());

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

	protected ContributorData contributor(Node node) {
		ContributorData con = new ContributorData();

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
