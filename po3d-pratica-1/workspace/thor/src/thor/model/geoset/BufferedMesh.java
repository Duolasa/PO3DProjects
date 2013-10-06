// Copyright 2013 Pedro B. Pascoal
package thor.model.geoset;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import thor.graphics.Vector3D;
import thor.model.geoset.Mesh;

/**
 * The BufferedMesh subclass describes a Mesh with an accessible buffer of mesh data.
 * A BufferedMesh is a collection of vertices, edges and faces.
 * It also contains information of vertices texture coordinates and vertices normal vectors.
 * 
 * @author Pedro B. Pascoal
 */
public class BufferedMesh extends Mesh {
	
	public BufferedMesh() {
		super();
	}
	
	public void addVertex(double x, double y, double z) {
		Vertex v = new Vertex(x, y, z);
		this.addVertex(v);
	}
	public void addVertex(Vertex v) {
		if(v.getX() > _maxVertex.getX()) _maxVertex.setLocation(v.getX(), _maxVertex.getY(), _maxVertex.getZ());
		if(v.getY() > _maxVertex.getY()) _maxVertex.setLocation(_maxVertex.getX(), v.getY(), _maxVertex.getZ());
		if(v.getZ() > _maxVertex.getZ()) _maxVertex.setLocation(_maxVertex.getX(), _maxVertex.getY(), v.getZ());
		
		if(v.getX() < _minVertex.getX()) _minVertex.setLocation(v.getX(), _minVertex.getY(), _minVertex.getZ());
		if(v.getY() < _minVertex.getY()) _minVertex.setLocation(_minVertex.getX(), v.getY(), _minVertex.getZ());
		if(v.getZ() < _minVertex.getZ()) _minVertex.setLocation(_minVertex.getX(), _minVertex.getY(), v.getZ());
		_vertices.add(v);
	}
	public void addTextCoord(float u, float v) {
		Point2D p = new Point2D.Float();
		p.setLocation(u, v);
		this.addTextCoord(p);
	}
	public void addTextCoord(Point2D p) {
		_textCoord.add(p);
	}
	
	public void addNormal(float nx, float ny, float nz) {
		Vector3D n = new Vector3D(nx, ny, nz);
		this.addNormal(n);
	}
	public void addNormal(Vector3D n) {
		_normals.add(n);
	}
	
	public void addFace(List<Integer> v) {
		this.addFace(v, new ArrayList<Integer>(), new ArrayList<Float>());
	}
	public void addFace(List<Integer> v, List<Integer> vt) {
		this.addFace(v, vt, new ArrayList<Float>());
	}
	public void addFace(List<Integer> v, List<Integer> vt, List<Float> vn) {
		Face face = new Face();
		face.Vertices = v;
		face.TextCoord = vt;
		//face.VNormals = vn;
		_faces.add(face);
	}
}