package tw.slmt.collada;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import tw.slmt.collada.parse.Parser;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.setProperty("log4j.configurationFile", "logging_config.xml");
		
		Parser parser = new Parser();
		parser.parseToColladaData(new FileInputStream(new File("examples/teapot.dae")));
	}

}
