// Copyright 2013 Pedro B. Pascoal
package thor.model.geoset;

import thor.graphics.Point3D;
import thor.graphics.Vector3D;

/**
 * A vertex is a position along with other information such as color, normal vector and texture coordinates.
 * 
 * @author Pedro B. Pascoal
 */
public class Vertex extends Point3D.Double {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The normalized average of the surface normals of the faces that contain that vertex.
	 */
	public Vector3D Normal;
	
	public Vertex(double x, double y, double z) {
		super(x, y, z);
		
		Normal = new Vector3D(0, 0, 0);
	}
}