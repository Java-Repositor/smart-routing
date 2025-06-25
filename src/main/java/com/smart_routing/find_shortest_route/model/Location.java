package com.smart_routing.find_shortest_route.model;

import com.opencsv.bean.CsvBindByName;
import com.smart_routing.find_shortest_route.util.CsvReader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
	
	private double parsedLat;
	private double parsedLon;

	@CsvBindByName(column = "circleme")
	private String circleName;
	@CsvBindByName(column = "regionme")
	private String regionName;
	@CsvBindByName(column = "divisionme")
	private String divisionName;
	@CsvBindByName(column = "officeme")
	private String officeName;
	@CsvBindByName(column = "pincode")
	private String pincode;
	@CsvBindByName(column = "officetype")
	private String officeType;
	@CsvBindByName(column = "delivery")
	private String delivery;
	@CsvBindByName(column = "district")
	private String district;
	@CsvBindByName(column = "stateme")
	private String stateName;
	@CsvBindByName(column = "latitude")
	private String latitude;
	@CsvBindByName(column = "longitude")
	private String longitude;
	
	public void computeParsedCoordinates() {
	    this.parsedLat = CsvReader.parseCoordinate(this.latitude);
	    this.parsedLon = CsvReader.parseCoordinate(this.longitude);
	}

	public double getParsedLat() {
	    return parsedLat;
	}

	public double getParsedLon() {
	    return parsedLon;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getOfficeType() {
		return officeType;
	}

	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
