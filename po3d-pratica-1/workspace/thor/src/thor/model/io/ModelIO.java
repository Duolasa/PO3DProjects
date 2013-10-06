// Copyright 2013 Pedro B. Pascoal
package thor.model.io; 

import java.io.File;
import java.io.IOException;

import thor.model.BufferedModel;

/**
 * A class containing static convenience methods for locating ModelReaders and ModelWriters, and performing simple encoding and decoding.
 * 
 * @author Pedro B. Pascoal
 */
public final class ModelIO extends Object {
	/**
	 * Returns a BufferedModel as the result of decoding a supplied File with an ImageReader chosen automatically from among those currently registered.
	 * 
	 * @param file - an InputStream to read from.
	 * @return
	 * a BufferedModel containing the decoded contents of the input, or null.
	 * @throws IllegalArgumentException - if input is null.
	 * @throws IOException - if an error occurs during reading.
	 */
	public static BufferedModel read(File file) throws IOException, IllegalArgumentException {
		if(file == null)
			throw new IllegalArgumentException("ModelIO: Argument cannot be null");
		
		System.out.println("ModelIO read file: " + file.getName());
		
		if(!file.exists() || !file.isFile())
			throw new IOException("ModelIO: file not found!");
		
		String name = file.getName();
		String extension = null;
        int i = name.lastIndexOf('.');
        if (i > 0 &&  i < name.length() - 1) {
        	extension = name.substring(i+1).toLowerCase();
        }
		String absolutePath = file.getAbsolutePath();
		
		if(extension.toString().compareToIgnoreCase("off") == 0) {
			// Object File Format (OFF)
			return (new ModelReaderOff(name, extension)).read(absolutePath);
		} else if(extension.toString().compareToIgnoreCase("obj") == 0) {
			// Wavefront
			return (new ModelReaderObj(name, extension)).read(absolutePath);
		} else {
			throw new IOException("ModelIO: file format not recognized");
		}
	}
}
