package tw.slmt.collada.parse.geo.mesh;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tw.slmt.collada.parse.ParserBaseClass;

public class MeshParser extends ParserBaseClass {
	
	// XXX: We assume that a data array will only be referenced by
	// elements in the same <mesh>
	// XXX: We assume there is only <float_array>
	private Map<String, float[]> dataArrays = new HashMap<String, float[]>();
	private Map<String, float[]> sourceArrays = new HashMap<String, float[]>();
	
	public MeshData mesh(Node node, String id, String name) {
		MeshData meshData = new MeshData(id, name);

		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("source")) {
				source(childNode);

			} else if (nodeName.equals("vertices")) {
				// TODO: Wait for implementation (spec. p.196)
				notImplemented("<vertices>");

			} else if (nodeName.equals("polygons")) {
				// TODO: Wait for implementation (spec. p.152)
				notImplemented("<polygons>");

			} else if (nodeName.equals("triangles")) {
				// TODO: Wait for implementation (spec. p.188)
				notImplemented("<triangles>");

			} else
				notImplemented("<" + nodeName + ">");
		}

		return meshData;
	}
	
	private void source(Node node) {
		// Get the id and name
		String sourceId = retrieveAttribute(node, "id", true);
		
		// Parse the child nodes
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("float_array")) {
				floatArray(childNode);

			// Note: It must read data before reading this
			} else if (nodeName.equals("technique_common")) {
				Node accessorNode = getSpecifiedChildNode(childNode, "accessor");
				generateSourceArray(accessorNode, sourceId);
			} else
				notImplemented("<" + nodeName + ">");
		}
	}
	
	private void floatArray(Node node) {
		// Get the id and the count
		String id = retrieveAttribute(node, "id", true);
		String countStr = retrieveAttribute(node, "count", true);
		int count = Integer.parseInt(countStr);
		
		// Create a array
		float[] data = new float[count];
		
		// Read the data
		StringTokenizer tokenizer = new StringTokenizer(node.getTextContent());
		for (int i = 0; i < count; i++)
			data[i] = Float.parseFloat(tokenizer.nextToken());
		
		// Put it in the map
		dataArrays.put(id, data);
	}
	
	private void generateSourceArray(Node node, String sourceId) {
		// XXX: It doesn't consider <param>s here.
		// It only read the data blindly.
		
		// Grep the necessary info
		String dataId = retrieveAttribute(node, "source", true);
		int count = retrieveAttributeAsInt(node, "count", true, -1);
		int offset = retrieveAttributeAsInt(node, "offset", false, 0);
		int stride = retrieveAttributeAsInt(node, "stride", false, 1);
		
		// Create a source array
		// XXX: We assume that there is only floating-point type data
		// which is not true for Collada data format
		float[] sourceArray = new float[count * stride];
		dataId = dataId.substring(1); // Remove a '#'
		float[] dataArray = dataArrays.get(dataId);
		
		// Copy the data
		for (int iter = 0; iter < count; iter++) {
			int dataOffset = iter * stride + offset;
			int srcOffset = iter * stride;
			for (int dim = 0; dim < stride; dim++) {
				sourceArray[srcOffset + dim] = dataArray[dataOffset + dim];
			}
		}
		
		// Save the source array
		sourceArrays.put(sourceId, sourceArray);
	}
}
