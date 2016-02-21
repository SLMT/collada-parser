package tw.slmt.collada.parse.geo.mesh;

import tw.slmt.collada.parse.geo.GeometryData;

public class MeshData extends GeometryData {
	
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
