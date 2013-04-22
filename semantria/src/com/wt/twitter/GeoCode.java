package com.wt.twitter;

public class GeoCode {

	private String unit;
	
	private double latitude;
	
	private double longitude;
	
	private double radius;
	
	public GeoCode(double lat, double lo, double r, String u) {
		latitude = lat;
		longitude = lo;
		radius = r;
		unit = u;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}	
}
