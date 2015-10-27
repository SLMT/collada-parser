package tw.slmt.collada.parse;

import java.util.List;

public class ColladaData {

	protected MetadataData metadata;
	protected List<GeometryData> geomertires;
	
	public MetadataData getMetadata() {
		return metadata;
	}
	
	public List<GeometryData> getGeomertires() {
		return geomertires;
	}
}
