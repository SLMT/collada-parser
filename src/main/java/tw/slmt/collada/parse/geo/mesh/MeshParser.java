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
	private String vertexSourceId;

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
				vertices(childNode);

			} else if (nodeName.equals("polygons")) {
				// TODO: Wait for implementation (spec. p.152)
				notImplemented("<polygons>");

			} else if (nodeName.equals("triangles")) {
				triangles(childNode, meshData);

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

	private void vertices(Node node) {
		// Get index data information
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("input")) {
				vertexSourceId = retrieveAttribute(childNode, "source", true);
				vertexSourceId = vertexSourceId.substring(1); // Remove a '#'
			}
		}
	}

	private void triangles(Node node, MeshData mesh) {
		// Set some basic information
		mesh.materialName = retrieveAttribute(node, "material", true);
		int count = retrieveAttributeAsInt(node, "count", true, 0);
		int inputCount = 0;
		int[] offsets = new int[3]; // {Vertex, Normal, TextureCoord}
		Node indexDataNode = null;

		// Get index data information
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node childNode = childNodes.item(i);
			String nodeName = childNode.getNodeName();

			if (nodeName.equals("input")) {
				String semantic = retrieveAttribute(childNode, "semantic", true);
				int offset = retrieveAttributeAsInt(childNode, "offset", true, 0);
				String sourceId = retrieveAttribute(childNode, "source", true);
				sourceId = sourceId.substring(1); // Remove a '#'

				if (semantic.equals("VERTEX")) {
					// XXX: This input maps to a <vertices>.
					// Then, that <vertices> maps to a <source>.
					// We should follow this mappings.
					offsets[0] = offset;
					mesh.vertexData = sourceArrays.get(vertexSourceId);
				} else if (semantic.equals("NORMAL")) {
					offsets[1] = offset;
					mesh.normalData = sourceArrays.get(sourceId);
				} else if (semantic.equals("TEXCOORD")) {
					offsets[2] = offset;
					mesh.textureCoordData = sourceArrays.get(sourceId);
				}

				inputCount++;
			} else if (nodeName.equals("p")) {
				indexDataNode = childNode;
			}
		}

		// Create arrays for indices
		int[][] indexArrays = new int[inputCount][];
		for (int i = 0; i < inputCount; i++)
			indexArrays[i] = new int[count * 3];

		// Read the indices
		StringTokenizer tokenizer = new StringTokenizer(
				indexDataNode.getTextContent());
		for (int tri = 0; tri < count; tri++) {
			for (int dim = 0; dim < 3; dim++) {
				for (int in = 0; in < inputCount; in++) {
					indexArrays[in][tri * 3 + dim] = Integer.parseInt(tokenizer
							.nextToken());
				}
			}
		}

		// Put the data at right positions
		mesh.vertexIndices = indexArrays[offsets[0]];
		mesh.normalIndices = indexArrays[offsets[1]];
		mesh.textureCoordIndices = indexArrays[offsets[2]];
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
