// Copyright 2013 Pedro B. Pascoal
package thor.graphics;

import java.io.Serializable;

/**
 * The Point3D class defines a point representing a location in (x,y,z) coordinate space.
 * 
 * This class is only the abstract superclass for all objects that store a 3D coordinate.
 * The actual storage representation of the coordinates is left to the subclass.
 * 
 * This class was based on the java.awt.geom.Point2D.
 * @author Pedro B. Pascoal
 */
public abstract class Point3D extends Object implements Cloneable {
	/**
	 * This is an abstract class that cannot be instantiated directly.
	 * Type-specific implementation subclasses are available for instantiation
	 * and provide a number of formats for storing the information necessary to
	 * satisfy the various accessor methods below.
	 */
	protected Point3D() {}
	
	/**
	 * @return
	 * Returns the X coordinate of this Point3D in double precision.*/
	public abstract double getX();
	/**
	 * @return
	 * Returns the Y coordinate of this Point3D in double precision.*/
	public abstract double getY();
	/**
	 * @return
	 * Returns the Z coordinate of this Point3D in double precision.*/
	public abstract double getZ();
	
	/**
	 * Sets the location of this Point2D to the specified double coordinates.
	 * @param x - the X coordinate of the newly constructed Point3D.
	 * @param y - the Y coordinate of the newly constructed Point3D.
	 * @param z - the Z coordinate of the newly constructed Point3D.
	 */
	public abstract void setLocation(double x, double y, double z);
	
	/**
	 * The Float class defines a 3d point specified in float precision.
	 * 
	 * @author Pedro B. Pascoal
	 */
	@SuppressWarnings("serial")
	public static class Float extends Point3D implements Serializable {
		/** The X coordinate of this Point3D.*/
		public float x;
		/** The Y coordinate of this Point3D.*/
		public float y;
		/** The Z coordinate of this Point3D.*/
		public float z;
		
		/** Constructs and initializes a Point3D with coordinates (0, 0, 0).*/
		public Float() {
			super();
		}
		protected Float(float value) {
			this(value, value, value);
		}
		/**
		 * Constructs and initializes a Point3D with the specified coordinates.
		 * @param x - the X coordinate of the newly constructed Point3D.
		 * @param y - the Y coordinate of the newly constructed Point3D.
		 * @param z - the Z coordinate of the newly constructed Point3D.
		 */
		public Float(float x, float y, float z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public double getX() { return this.x; }
		@Override
		public double getY() { return this.y; }
		@Override
		public double getZ() { return this.z; }
		@Override
		public void setLocation(double x, double y, double z) {
			this.x = (float) x;
			this.y = (float) y;
			this.z = (float) z;
		}
	}
	
	/**
	 * The Double class defines a point specified in double precision.
	 * 
	 * @author Pedro B. Pascoal
	 */
	public static class Double extends Point3D implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		/** The X coordinate of this Point3D.*/
		public double x;
		/** The Y coordinate of this Point3D.*/
		public double y;
		/** The Z coordinate of this Point3D.*/
		public double z;

		/** Constructs and initializes a Point3D with coordinates (0, 0, 0).*/
		public Double() {
			this(0.0);
		}
		protected Double(double value) {
			this(value, value, value);
		}
		/**
		 * Constructs and initializes a Point3D with the specified coordinates.
		 * @param x - the X coordinate of the newly constructed Point3D.
		 * @param y - the Y coordinate of the newly constructed Point3D.
		 * @param z - the Z coordinate of the newly constructed Point3D.
		 */
		public Double(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public double getX() { return this.x; }
		@Override
		public double getY() { return this.y; }
		@Override
		public double getZ() { return this.z; }
		@Override
		public void setLocation(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
	}
	
	/**
	 * Returns the distance from this Point3D to a specified Point3D. Also known as the euclidean distance (L-2).
	 * @param pt - the specified point to be measured against this Point3D
	 * @return
	 * the distance between this Point3D and the specified Point3D.
	 */
	public double distance(Point3D pt) {
		return Point3D.distance(this.getX(), this.getY(), this.getZ(), pt.getX(), pt.getY(), pt.getZ());
	}
	/**
	 * Returns the distance between two points. Also known as the euclidean distance (L-2).
	 * @param x1 - the X coordinate of the first specified point
	 * @param y1 - the Y coordinate of the first specified point
	 * @param z1 - the Z coordinate of the first specified point
	 * @param x2 - the X coordinate of the second specified point
	 * @param y2 - the Y coordinate of the second specified point
	 * @param z2 - the Z coordinate of the second specified point
	 * @return
	 * The distance between the two sets of specified coordinates.
	 */
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Math.sqrt(distanceSq(x1, y1, z1, x2, y2, z2));
	}
	/**
	 * Returns the square of the distance from this Point3D to a specified Point3D.
	 * @param pt - the specified point to be measured against this Point3D
	 * @return
	 * the square of the distance between this Point3D to a specified Point3D.
	 */
	public double distanceSq(Point3D pt) {
		return this.distanceSq(pt.getX(), pt.getY(), pt.getZ());
	}
	/**
	 * Returns the square of the distance from this Point3D to a specified point.
	 * @param px - the X coordinate of the specified point to be measured against this Point3D
	 * @param py - the Y coordinate of the specified point to be measured against this Point3D
	 * @param py - the Z coordinate of the specified point to be measured against this Point3D
	 * @return
	 * the square of the distance between this Point3D and the specified point.
	 */
	public double distanceSq(double px, double py, double pz) {
		return Point3D.distanceSq(this.getX(), this.getY(), this.getZ(), px, py, pz);
	}
	private static double distanceSq(double x1, double y1, double z1, double x2, double y2, double z2) {
		return ((x2-x1) * (x2-x1) + (y2-y1) * (y2-y1) + (z2-z1) * (z2-z1));
	}
	
	/**
	 * Adds a Point3D coordinates values to this Point3D. 
	 * @param pt - the specified point to add to this Point3D
	 */
	public void add(Point3D pt) {
		this.setLocation(this.getX() + pt.getX(),
						 this.getY() + pt.getY(),
						 this.getZ() + pt.getZ());
	}
	/**
	 * Subs a Point3D coordinates values to this Point3D. 
	 * @param pt - the specified point to sub to this Point3D
	 */
	public void sub(Point3D pt) {
		this.setLocation(this.getX() - pt.getX(),
						 this.getY() - pt.getY(),
						 this.getZ() - pt.getZ());
	}
	/**
	 * Multiples each coordinate with a value.
	 * @param value - value to multiple each coordinate with
	 * @return
	 */
	public void mul(double value) {
		this.setLocation(this.getX() * value,
						 this.getY() * value,
						 this.getZ() * value);
	}
	/**
	 * Divides each coordinate of the Point3D with a specific value.
	 * @param value
	 */
	public void div(int value) {
		this.setLocation(this.getX() / value,
						 this.getY() / value,
						 this.getZ() / value);
	}
}






