package com.example.demo.entity;

import java.io.Serializable;

public class Weather implements Serializable {

	private String city;
	private Double temprature;
	private Integer humidity;

	public Weather() {
	}

	public Weather(String city, Double temprature, Integer humidity) {
		this.city = city;
		this.humidity = humidity;
		this.temprature = temprature;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getTemprature() {
		return temprature;
	}

	public void setTemprature(Double temprature) {
		this.temprature = temprature;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

}
