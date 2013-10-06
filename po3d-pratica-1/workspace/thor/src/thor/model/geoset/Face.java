// Copyright 2013 Pedro B. Pascoal
package thor.model.geoset;

import java.util.ArrayList;
import java.util.List;

import thor.graphics.Vector3D;

/**
 * A face is a closed set of vertices, in which a triangle face has three vertices, and a quad face has four vertices.
 * 
 * @author Pedro B. Pascoal
 */
public class Face {
	public Vector3D Normal;
	
	/**
	 * List of the vertices identifiers, that form the face.
	 */
	public List<Integer> Vertices = new ArrayList<Integer>();
	
	/**
	 * List of the texture coordinate identifiers. 
	 */
	public List<Integer> TextCoord = new ArrayList<Integer>();
	//public List<Float> VNormals = new ArrayList<Float>();	// Normals in (x,y,z) form; normals might not be unit.
	
}
