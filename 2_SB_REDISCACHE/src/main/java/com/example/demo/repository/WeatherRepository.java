package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Weather;

@Repository
public class WeatherRepository {

	public Weather fetchWeatherFromApi(String city) {

		return new Weather(city, 31.5, 65);

	}
}
