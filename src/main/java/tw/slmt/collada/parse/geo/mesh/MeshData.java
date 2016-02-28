package tw.slmt.collada.parse.geo.mesh;

import tw.slmt.collada.parse.geo.GeometryData;

public class MeshData extends GeometryData {
	
	// FIXME: This is not true all the time
	private static final int DIMENSION = 3;
	
	// XXX: We assume that there is only floating-pint data
	float[] vertexData;
	int[] vertexIndices;
	float[] normalData;
	int[] normalIndices;
	float[] textureCoordData;
	int[] textureCoordIndices;
	
	String materialName;
	
	public MeshData(String id, String name) {
		super(id, name);
	}
	
	/**
	 * Normalize the values of vertices to [-1.0, 1.0] and center at (0.0, 0.0).
	 */
	public void normalize() {
		float numVertices = vertexData.length / DIMENSION;
		
		// Find the center
		float[] center = new float[3];
		float max, min, value;
		for (int ci = 0; ci < 3; ci++) {
			max = Float.MIN_VALUE;
			min = Float.MAX_VALUE;

			for (int vi = 0; vi < numVertices; vi++) {
				value = vertexData[vi * 3 + ci];

				if (max < value)
					max = value;

				if (min > value)
					min = value;
			}

			center[ci] = (max + min) / 2;
		}

		// Shift
		for (int vi = 0; vi < numVertices; vi++)
			for (int ci = 0; ci < 3; ci++)
				vertexData[vi * 3 + ci] -= center[ci];

		// Find the longest distance to the center
		float diff, max_diff = 0;
		for (int ci = 0; ci < 3; ci++) {
			max = Float.MIN_VALUE;
			min = Float.MAX_VALUE;

			for (int vi = 0; vi < numVertices; vi++) {
				value = vertexData[vi * 3 + ci];

				if (max < value)
					max = value;

				if (min > value)
					min = value;
			}

			diff = (max - min) / 2;
			if (max_diff < diff)
				max_diff = diff;
		}

		// Scale
		float ratio = 1.0f / max_diff;
		for (int vi = 0; vi < numVertices; vi++)
			for (int ci = 0; ci < 3; ci++)
				vertexData[vi * 3 + ci] *= ratio;
	}
	
	public float[] getVertexData() {
		return vertexData;
	}
	
	public int[] getVertexIndices() {
		return vertexIndices;
	}
	
	public float[] getNormalData() {
		return normalData;
	}
	
	public int[] getNormalIndices() {
		return normalIndices;
	}
	
	public float[] getTextureCoordData() {
		return textureCoordData;
	}
	
	public int[] getTextureCoordIndices() {
		return textureCoordIndices;
	}
	
	public String getMaterialName() {
		return materialName;
	}
}
