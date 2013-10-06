// Copyright 2013 Pedro B. Pascoal
package thor; 

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GLAutoDrawable;

import thor.graphics.Point3D;
import thor.model.geoset.Mesh;

/**
 * The abstract class Model is the superclass of all classes that represent graphical 3d model.
 * The model must be obtained in a platform-specific manner.
 * 
 * @author Pedro B. Pascoal
 */
public abstract class Model {
	private String _name = null;
	private String _extension = null;
	
	protected List<Mesh> _meshes = new ArrayList<Mesh>();	// list of meshes/geosets
	//MYTODO: Add Bones
	//MYTODO: Add Attachments
	//MYTODO: Add Events
	//MYTODO: Add ParticleEmitters
	
	public Model(String name, String extension) {
		_name = name;
		_extension = extension;
	}

	public String getName() {
		return _name;
	}
	public String getModelFormat() {
		return _extension;
	}
	
	public List<Mesh> getMeshes() {
		return _meshes;
	}
	
	
	public void translate(double x, double y, double z){
		for(Mesh mesh : _meshes) {
			mesh.translate(x, y, z);
		}
	}
	public void scale(double value) {
		for(Mesh mesh : _meshes) {
			mesh.scale(value);
		}
	}
	public void rotate() {
	}

	public Point3D getMaxVertex() {
		return _meshes.get(0).getMaxVertex();
	}
	public Point3D getMinVertex() {
		return _meshes.get(0).getMinVertex();
	}
	public Point3D getBarycenter() {
		return _meshes.get(0).getBarycenter();
	}
	
	public int countVertices() {
		int count = 0;
		for(Mesh mesh : _meshes) {
			count += mesh.countVertices();
		}
		return count;
	}
	public int countFaces() {
		int count = 0;
		for(Mesh mesh : _meshes) {
			count += mesh.countFaces();
		}
		return count;
	}
	
	public boolean isManifold() {
		return _meshes.get(0).isManifold();
	}
	public double getSurfaceArea() {
		return _meshes.get(0).getSurfaceArea();
	}

	public void draw(GLAutoDrawable drawable) {
		for(Mesh mesh : _meshes) {
			mesh.draw(drawable);
		}
	}

}
