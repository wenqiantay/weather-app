package vttp.csf.weather_server.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.csf.weather_server.Repositories.CacheRepository;
import vttp.csf.weather_server.models.Weather;

@Service
public class WeatherService {
    
    @Value("${api.key}")
    private String API_KEY;

    @Autowired
    private CacheRepository cacheRepo;

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";
    

    public Weather getWeatherResult(String city) {


        // retrieve cached data if exists
        if( cacheRepo.isDataCached(city)) {

            Weather cachedWeather = cacheRepo.getCachedWeather(city);
            return cachedWeather;
        } 

        // If there is no cached data then save a new entry
        Weather newWeather = fetchWeatherFromAPI(city);

        cacheRepo.saveWeatherData(newWeather);
            
        return newWeather;

    }

    // Method to retrieve weather details from API
    public Weather fetchWeatherFromAPI(String city) {
        Weather weather = new Weather();
    
            String url = UriComponentsBuilder
                .fromUriString(API_URL)
                .queryParam("q", city )
                .queryParam("appid", API_KEY)
                .queryParam("units", "metric")
                .toUriString();
    
            RequestEntity req = RequestEntity.get(url).build();
    
            RestTemplate template = new RestTemplate();
    
            ResponseEntity<String> resp = template.exchange(req, String.class);
    
            String payload = resp.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject result = reader.readObject();
            JsonArray weatherArray = result.getJsonArray("weather");
    
            for (int i = 0; i < weatherArray.size(); i++) {
    
                JsonObject obj = weatherArray.getJsonObject(i);
              
                weather.setMain(obj.getString("main"));
                weather.setDescription(obj.getString("description"));
                weather.setIcon(obj.getString("icon"));
                   
            }
    
            JsonObject main = result.getJsonObject("main");
            weather.setCity(city);
            weather.setTemp(main.getJsonNumber("temp").doubleValue());
            weather.setFeels_like(main.getJsonNumber("feels_like").doubleValue());
            weather.setTemp_min(main.getJsonNumber("temp_min").doubleValue());
            weather.setTemp_max(main.getJsonNumber("temp_max").doubleValue());
            weather.setPressure(main.getInt("pressure"));
            weather.setHumidity(main.getInt("humidity"));
    
            return weather;
    }    
}
