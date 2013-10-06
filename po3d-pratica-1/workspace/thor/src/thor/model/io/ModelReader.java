// Copyright 2013 Pedro B. Pascoal
package thor.model.io; 

import java.io.IOException;

import thor.model.BufferedModel;

/**
 * An abstract superclass for parsing and decoding of models. This class must be subclassed by classes that read in models in the context of the ThOR I/O framework.
 * 
 * @author Pedro B. Pascoal
 */
public abstract class ModelReader extends Object {	
	protected String _name = null;
	protected String _extension = null;
	
	public ModelReader(String name, String extension) {
		_name = name;
		_extension = extension;
	}
	public String getName() {
		return _name;
	}
	public String getExtension() {
		return _extension;
	}
	
	/**
	 * This method receives the full path of a model file, reads its contents and builds an instance of Model.
	 * @param filename
	 * The full path of the model file.
	 * @return An instance of Model, built from the file contents.
	 * @throws IOException - if file doesn't exist, or format not recognized.
	 */
	public abstract BufferedModel read(String filename) throws IOException;
	
	//protected abstract Mesh readGeoset() throws IOException;
	
}
