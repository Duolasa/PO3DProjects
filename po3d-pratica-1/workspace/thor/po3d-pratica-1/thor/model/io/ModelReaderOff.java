// Copyright 2012 Pedro B. Pascoal
package thor.model.io; 

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import thor.model.BufferedModel;
import thor.model.geoset.BufferedMesh;

// Object File Format
class ModelReaderOff extends ModelReader {

	public ModelReaderOff(String name, String extension) {
		super(name, extension);
	}

	public BufferedModel read(String filename) throws IOException {
		/*
		 * ================================================================ 
		 * TODO: PO3D Pratica 1 - Object File Format (OFF)
		 * Add here the code to read a OFF file
		 * The file "thor.model.io.ModelReaderObj.java" serves as example of file reading
		 *  
		 * * BufferedModel model = new BufferedModel("model-name", "model-extension");
		 * * 
		 * * BufferedMesh mesh = new BufferedMesh();
		 * * 
		 * * for each vertex:
		 * * * mesh.addVertex(x, y, z);
		 * * 
		 * * for each face:
		 * * * mesh.addFace("list-of-vertices");
		 * * 
		 * * model.addMesh(mesh);
		 * * return model;
		 * ================================================================
		 */

		
		BufferedModel model = new BufferedModel(_name, _extension);
		BufferedMesh mesh = new BufferedMesh();

		Scanner root = new Scanner(new FileReader(filename));
		if(!root.hasNext()) {
			root.close();
			throw new IOException("ModelReader: " + filename + " is empty");
		}
		Scanner line = new Scanner(root.nextLine());
		String firstLine = line.nextLine();
		if(firstLine.compareTo("OFF") != 0){ 
			line.close();
			root.close();
			throw new IOException("This is not an off file");
		}
		
		int numFaces = 0;
		int numVertices = 0;
		int numEdges = 0;
		boolean hasInfo = false;
	
		while(root.hasNext()) {
			line = new Scanner(root.nextLine());
			line.useLocale(Locale.US);
			String content = line.next();
			if(content.compareToIgnoreCase("#") == 0) { 		//ignore all commented text
			} else if(!hasInfo){								//read the initial info on the model		
				numVertices = Integer.parseInt(content);
				numFaces = line.nextInt();
				numEdges = line.nextInt();
				hasInfo=true;
			} else if(numVertices-- > 0){						//read the vertexes
				double x = Double.parseDouble(content);
				double y = line.nextDouble();
				double z = line.nextDouble();
				mesh.addVertex(x, y, z);
			} else if (numFaces -- > 0){						//read the Faces
				int vertOnFace = Integer.parseInt(content);
				List<Integer> v = new ArrayList<Integer>(); // vertices id's
				List<Integer> vt = new ArrayList<Integer>();// texture coordinates id's
				List<Float> vn = new ArrayList<Float>();// normal id's
				while(vertOnFace-- > 0){
					v.add(line.nextInt());					
				}
				mesh.addFace(v, vt, vn);
			}
		
			line.close();
		}
		model.addMesh(mesh);
		root.close();
		
		System.out.println("Model loaded");
		return model;
	}
}

