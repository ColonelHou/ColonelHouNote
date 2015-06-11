package com.hn.java.算法.mechina.cluster.MST算法;

/**
 * 城市
 * 
 * @author duyf
 * 
 */
class City {

	private String name;
	// 经度
	private double x;

	// 纬度
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		City other = (City) obj;
		if (this.getX() == other.getX() && this.getY() == other.getY()) {
			return true;
		}
		return false;
	}
}