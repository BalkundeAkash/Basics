package com.example.demo.serviceimpl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Weather;
import com.example.demo.repository.WeatherRepository;
import com.example.demo.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WeatherRepository repository;

    private static final Logger log =
    LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Override
    public Weather getWeather(String city) {

        String key = "weather:" + city;

        log.info("Request received for city: {}", city);

        Weather cachedWeather =
        (Weather) redisTemplate.opsForValue().get(key);

        if (cachedWeather != null) {
            log.info("Cache HIT for key: {}", key);
            return cachedWeather;
        }

        log.warn("Cache MISS for key: {}", key);

        Weather weather =
        repository.fetchWeatherFromApi(city);

        log.info("Data fetched from API for city: {}", city);

        redisTemplate.opsForValue().set(
                key,
                weather,
                10,
                TimeUnit.MINUTES
        );

        log.info("Data stored in Redis with key: {}", key);

        return weather;
    }
}