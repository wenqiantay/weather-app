package vttp.csf.weather_server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.csf.weather_server.models.Weather;
import vttp.csf.weather_server.services.WeatherService;

@RestController
@RequestMapping("/api")
public class WeatherRestController {

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping("/weather")
    public ResponseEntity<Weather> getWeatherFromApi(@RequestParam String q) {

        Weather weather = weatherSvc.getWeatherResult(q);

        return ResponseEntity.ok(weather);

    }
    
}
