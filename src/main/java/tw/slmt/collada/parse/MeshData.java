package tw.slmt.collada.parse;

import java.util.HashMap;
import java.util.Map;

public class MeshData extends GeometryData {
	Map<String, SourceData> source = new HashMap<String, SourceData>();
	
	public SourceData getSource(String id) {
		return source.get(id);
	}
}
