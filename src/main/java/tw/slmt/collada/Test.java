package tw.slmt.collada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Parser parser = new Parser(new FileInputStream(new File("C:\\Users\\SLMT\\Desktop\\cube.xml")));
		parser.parseToColladaObject();
	}

}
