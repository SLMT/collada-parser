package tw.slmt.collada;

public enum NodeType {
	
	// Special types
	NONE,
	
	// Second level nodes
	ASSET, LIB_MAT, LIB_EFFECT, LIB_GEO, LIB_VIS_SCENE, SCENE;
	
	// XXX: There might be a better parsing algorithm
	public static NodeType parse(String name) {
		if (name.equals("asset"))
			return ASSET;
		if (name.equals("library_materials"))
			return LIB_MAT;
		if (name.equals("library_effects"))
			return LIB_EFFECT;
		if (name.equals("library_geometries"))
			return LIB_GEO;
		if (name.equals("library_visual_scenes"))
			return LIB_VIS_SCENE;
		if (name.equals("scene"))
			return SCENE;
		
		return NONE;
	}
}
