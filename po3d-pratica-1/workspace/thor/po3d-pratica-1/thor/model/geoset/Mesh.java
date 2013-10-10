// Copyright 2013 Pedro B. Pascoal
package thor.model.geoset; 

import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import thor.graphics.Vector3D;
import thor.graphics.Point3D;

/**
 * The abstract class Mesh is the superclass of all classes that represent polygon mesh.
 * The mesh must be obtained in a platform-specific manner.
 * @author Pedro B. Pascoal
 */
public abstract class Mesh extends Object {
	protected List<Vertex> _vertices = new ArrayList<Vertex>();		// list of vertices
	protected List<Point2D> _textCoord = new ArrayList<Point2D>();	// list of texture coordinates
	protected List<Vector3D> _normals = new ArrayList<Vector3D>();	// list of normals
	protected List<Face> _faces = new ArrayList<Face>(); 			// list of faces (triangles)

	protected Point3D _maxVertex;
	protected Point3D _minVertex;
	
	public Mesh() {
		_maxVertex = new Point3D.Float(-9999, -9999, -9999);
		_minVertex = new Point3D.Float(9999, 9999, 9999);
	}
	/**
	 * @return
	 * A list of Vertex with all the vertices of the mesh.
	 */
	public List<Vertex> getVertices() { return _vertices; }
	/**
	 * @return
	 * A list of Point2D with the texture coordinates for each of the vertices of the mesh.
	 */
	public List<Point2D> getTextCoord() { return _textCoord; }
	/**
	 * @return
	 * A list of Vector3D that represent the normals of each of the vertices of the mesh.
	 */
	public List<Vector3D> getNormals() { return _normals; }
	/**
	 * @return
	 * A list of Face with all the faces of the mesh.
	 */
	public List<Face> getFaces() { return _faces; }
	/**
	 * @return
	 * Return the total number of vertices in the mesh.
	 */
	public int countVertices() { return _vertices.size(); }
	/**
	 * @return
	 * Return the total number of faces in the mesh.
	 */
	public int countFaces() { return _faces.size(); }

	/**
	 * Moves every point of the mesh a (x, y, z) distance.
	 * @param x - the X coordinate to add.
	 * @param y - the Y coordinate to add.
	 * @param z - the Z coordinate to add.
	 */
	public void translate(double x, double y, double z) {
		for (Vertex vertex : _vertices) {
			vertex.add(new Point3D.Double(x, y, z));
		}
	}
	/**
	 * Change the dimension of object by a scaling factor, i.e. enlarging or shrinking. 
	 * @param value - the scale factor
	 */
	public void scale(double value) {
		for (Vertex vertex : _vertices) {
			vertex.mul(value);
		}
	}
	public void rotateX(float angle) {
		for (Vertex vertex : _vertices) {
			double rangle = Math.atan(vertex.getY() / vertex.getX()) * (float) (Math.PI /180);
			vertex.setLocation( (float) Math.cos(angle + rangle),
								(float) Math.asin(angle + rangle),
								vertex.getZ());
		}
	}
	public void rotateZ(float angle) {
		for (Vertex vertex : _vertices) {
			double rangle = Math.atan(vertex.getY()/vertex.getX()) * (float) (Math.PI /180);
			vertex.setLocation( (float) Math.cos(angle + rangle),
								(float) Math.asin(angle + rangle),
								vertex.getZ());
		}
	}
	
	public void calculateNormals() {
		//MyTODO: ok... kinda of tired, so calculate only for 3 vertices, screw when there are 4...
		// i'll do it later, maybe calculate each face normal
		for(Face face : _faces) {
			Point3D p1 = _vertices.get(face.Vertices.get(0));
			Point3D p2 = _vertices.get(face.Vertices.get(1));
			Point3D p3 = _vertices.get(face.Vertices.get(2));

			Vector3D v1 = Vector3D.sub(p2, p1);
			Vector3D v2 = Vector3D.sub(p3, p1);
			
			face.Normal = Vector3D.product(v1, v2);
			face.Normal.normalize();
			
			for(int i : face.Vertices) {
				Vertex v = _vertices.get(face.Vertices.get(i));
				v.Normal.add(face.Normal);
			}
		}
		for(Vertex v : _vertices) {
			v.Normal.normalize();
		}
	}
	
	public Point3D getMaxVertex() {
		return _maxVertex;
	}
	public Point3D getMinVertex() {
		return _minVertex;
	}
	

	public Point3D getFaceCenter(Face face) {
		Point3D center = new Point3D.Double();
		
		for(int i : face.Vertices) {
			Vertex v = _vertices.get(i);
			center.add(v);
		}
		center.div(face.Vertices.size());
		return center;
	}
	
	public void draw(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
        for (Face face : _faces) {
        	if(face.Vertices.size() == 4) {
        		gl.glBegin(GL2.GL_QUADS);
        	} else { // default = 3
        		gl.glBegin(GL2.GL_TRIANGLES);
        	}
        	for(int i = 0; i < face.Vertices.size(); i++) {
        		gl.glVertex3d(	_vertices.get(face.Vertices.get(i)).getX(),
        						_vertices.get(face.Vertices.get(i)).getY(),
        						_vertices.get(face.Vertices.get(i)).getZ());
        	}
            gl.glEnd();
		}
	}

	
	
	public Point3D calcFacePoints(List<Integer> l, double totalArea){
	    
		///The bad way (that kinda works?)

		double weight = calculateArea(l) / totalArea;
		Point3D centerPoint = new Point3D.Double();
		
		for(Integer i : l){  //find the center of the face
			centerPoint.add( new Point3D.Double(_vertices.get(i).getX(), _vertices.get(i).getY(), _vertices.get(i).getZ()));
			
		}
			return new Point3D.Double(weight * centerPoint.getX() / 3, weight * centerPoint.getY() / 3, weight * centerPoint.getZ() / 3);
		
		
		///The good way (that doesnt really work)
		/*
		int pointsOnEntireMesh = 10000;
		int pointsToUse = (int) Math.round((calculateArea(l)*pointsOnEntireMesh) / totalArea);
		Point3D sumOfPoints = new Point3D.Double();
		Random generator = new Random( System.currentTimeMillis());
		int holdOriginalValue = pointsToUse;
		
		while(pointsToUse-- > 0){
			
			Point3D originPoint = new Point3D.Double(
										_vertices.get(l.get(0)).x, 
										_vertices.get(l.get(0)).y,	
										_vertices.get(l.get(0)).z);
			
			Point3D point1Offset = new Point3D.Double
										(_vertices.get(l.get(1)).x - originPoint.getX(),
										_vertices.get(l.get(1)).y - originPoint.getY() ,
										_vertices.get(l.get(1)).z - originPoint.getZ());
			
			Point3D point2Offset = new Point3D.Double
					(_vertices.get(l.get(2)).x - originPoint.getX(),
					_vertices.get(l.get(2)).y - originPoint.getY() ,
					_vertices.get(l.get(2)).z - originPoint.getZ());

			
		
			double generatedNumber = generator.nextDouble();
			
			originPoint.add(new Point3D.Double(point1Offset.getX() * generatedNumber,
												point1Offset.getY() * generatedNumber,
												point1Offset.getZ() * generatedNumber));
					
			generatedNumber = generator.nextDouble();

			originPoint.add(new Point3D.Double(point2Offset.getX() * generatedNumber,
											   point2Offset.getY() * generatedNumber,
											   point2Offset.getZ() * generatedNumber));
							
			
			sumOfPoints.add(originPoint);
		}			
		
		if(holdOriginalValue > 0){
			return new Point3D.Double(sumOfPoints.getX() / holdOriginalValue,
									  sumOfPoints.getY() / holdOriginalValue,
						 			  sumOfPoints.getZ() / holdOriginalValue);
		}else{
			return new Point3D.Double();
		}
		*/
	}		
	
		
	public Point3D getBarycenter() {
		/* TODO: PO3D Pratica 1 - calculate the barycenter of the mesh
		 * * Point3D.Double(x, y, z) -> creates a new Point3D with doubles
		 * * _maxVertex -> contains the max values of each coordinate
		 * * _minVertex -> contains the min values of each coordinate
		 */
		Point3D center = new Point3D.Double();
		double totalArea = getSurfaceArea();
		for(Face f : _faces){
			if(f.Vertices.size() == 3){
				center.add(calcFacePoints(f.Vertices, totalArea));
				
			} else if(f.Vertices.size() == 4){
				List<Integer> firstHalf = new  ArrayList<Integer>();
				List<Integer> secondHalf = new  ArrayList<Integer>();
				
				
				//divide the quadrilateral in 2 triangles
				firstHalf.add(f.Vertices.get(0));
				firstHalf.add(f.Vertices.get(1));
				firstHalf.add(f.Vertices.get(2));
				
				secondHalf.add(f.Vertices.get(0));
				secondHalf.add(f.Vertices.get(3));
				secondHalf.add(f.Vertices.get(2));
				
				center.add(calcFacePoints(firstHalf, totalArea)); 
				center.add(calcFacePoints(secondHalf, totalArea));
			}

		
		}
		return center;
	}
	
	public boolean isManifold() {
		/* TODO: PO3D Pratica 1 - determine if the mesh is manifold
		 * *
		 * * _vertices -> contains a list of all vertices of the mesh
		 * * _faces -> contains the list of the id of the vertices that from it
		 */
		List<int[]> edgeList = new ArrayList<int[]>();
		edgeList.add( new int[] {-9999, -9999, -9999});
		boolean edgeAlreadyExists = false;
		
		for(Face f : _faces){							 // construct edge list
			int numVertices = f.Vertices.size();						
			for(int i=0; i < numVertices; i++){
				int firstVertex = f.Vertices.get(i);
				int secondVertex;
				
				if(i == numVertices - 1){ 
					secondVertex = f.Vertices.get(0);
				}else  secondVertex = f.Vertices.get(i + 1);


				edgeAlreadyExists = false;
				for(int j = 0; j < edgeList.size(); j++){
					if(((edgeList.get(j)[0] == firstVertex) && (edgeList.get(j)[1] == secondVertex))
					    || ((edgeList.get(j)[0] == secondVertex) && (edgeList.get(j)[1] == firstVertex))){   //edge exists
						edgeList.get(j)[2]++;
						edgeAlreadyExists = true;
						break;
					}
				}
				if(!edgeAlreadyExists){
					edgeList.add( new int[] {firstVertex, secondVertex, 1});  //edge doesn't exist yet							
				}
			}
		}
		
		
		for(int j = 0; j < edgeList.size(); j++){
			if( edgeList.get(j)[2] > 2){  // if an edge is used for more than 2 faces
				return false;
			}
		}

	return true;
}
	
	public double calculateArea(List<Integer> l){
		double area = 0;		
		if(l.size() == 3){   // face is a triangle
			List<Vertex> vertexOfCurrentFace = new ArrayList<Vertex>();
			
			for(Integer id : l){
				vertexOfCurrentFace.add(_vertices.get(id));
			}
			
			double distanceAB = Math.sqrt(Math.pow(vertexOfCurrentFace.get(1).x - vertexOfCurrentFace.get(0).x, 2) + 
										  Math.pow(vertexOfCurrentFace.get(1).y - vertexOfCurrentFace.get(0).y, 2) + 
										  Math.pow(vertexOfCurrentFace.get(1).z - vertexOfCurrentFace.get(0).z, 2));
			
			double distanceBC =  Math.sqrt(Math.pow(vertexOfCurrentFace.get(2).x - vertexOfCurrentFace.get(1).x, 2) + 
										  Math.pow(vertexOfCurrentFace.get(2).y - vertexOfCurrentFace.get(1).y, 2) + 
					  					  Math.pow(vertexOfCurrentFace.get(2).z - vertexOfCurrentFace.get(1).z, 2));
			
			double distanceCA =  Math.sqrt(Math.pow(vertexOfCurrentFace.get(0).x - vertexOfCurrentFace.get(2).x, 2) + 
										  Math.pow(vertexOfCurrentFace.get(0).y - vertexOfCurrentFace.get(2).y, 2) + 
										  Math.pow(vertexOfCurrentFace.get(0).z - vertexOfCurrentFace.get(2).z, 2));
			
			double s = (distanceAB + distanceBC + distanceCA) /2 ;
			
			area = Math.sqrt(s*(s-distanceAB)*(s-distanceBC)*(s-distanceCA));  // Heron's formula
		
			
		}else if(l.size() == 4){   //quadrilateral
			List<Integer> firstHalf = new  ArrayList<Integer>();
			List<Integer> secondHalf = new  ArrayList<Integer>();
			
			
			//divide the quadrilateral in 2 triangles
			firstHalf.add(l.get(0));
			firstHalf.add(l.get(1));
			firstHalf.add(l.get(2));
			
			secondHalf.add(l.get(0));
			secondHalf.add(l.get(3));
			secondHalf.add(l.get(2));
			
			area = calculateArea(firstHalf) + calculateArea(secondHalf);
		}
		return area;
	}

	public double getSurfaceArea() {
		/* TODO: PO3D Pratica 1 - calculate and return the surface area 
		 * *
		 * * _vertices -> contains a list of all vertices of the mesh
		 * * _faces -> contains the list of the id of the vertices that from it
		 */
		double totalArea = 0;
		for(Face f : _faces){
			totalArea += calculateArea(f.Vertices); 
		}
		
		return totalArea; 
	}
}
