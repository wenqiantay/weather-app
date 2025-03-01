package vttp.csf.weather_server.Repositories;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.csf.weather_server.models.Weather;

@Repository
public class CacheRepository {
    
    @Autowired
    private MongoTemplate template;

    // Check if the data is in Mongo
    public boolean  isDataCached(String city) {

        Criteria criteria = Criteria.where("city").is(city);
        Query query = Query.query(criteria);

        Weather weather = template.findOne(query, Weather.class, "weather_cache");

        if(weather !=null ){
            
            // Check if cached data still in database after 15 mins
            Instant now = Instant.now();
            Duration duration = Duration.between(weather.getTimeStamp(), now);

            
            if(duration.toMinutes() <= 15) {

                return true;

            } else { 

                removeCachedData(city);
            }
        }

        return false;
    }

    public Weather getCachedWeather(String city) {

        Criteria criteria = Criteria.where("city").is(city);
        Query query = Query.query(criteria);

        return template.findOne(query, Weather.class, "weather_cache");
    }

    public void saveWeatherData(Weather weather) {

        Criteria criteria = Criteria.where("city").is(weather.getCity());
        Query query = Query.query(criteria);

        Weather existingWeather = template.findOne(query, Weather.class, "weather_cache");


        if (existingWeather != null) {
    
            existingWeather.setMain(weather.getMain());
            existingWeather.setDescription(weather.getDescription());
            existingWeather.setIcon(weather.getIcon());
            existingWeather.setTemp(weather.getTemp());
            existingWeather.setFeels_like(weather.getFeels_like());
            existingWeather.setTemp_min(weather.getTemp_min());
            existingWeather.setTemp_max(weather.getTemp_max());
            existingWeather.setPressure(weather.getPressure());
            existingWeather.setHumidity(weather.getHumidity());
            existingWeather.setTimeStamp(Instant.now());


            template.save(existingWeather, "weather_cache");

        } else {
        
            weather.setTimeStamp(Instant.now()); 
            template.save(weather, "weather_cache");
        }
    }

    public void removeCachedData(String city) {
        
        Criteria criteria = Criteria.where("city").is(city);
        Query query = Query.query(criteria);
    

        Weather weather = template.findOne(query, Weather.class, "weather_cache");

        // Remove only when data exist
        if( weather != null) {
            template.remove(weather);
        } else {
            System.out.println("There is no cached data for city: " + city);
        }
    }
}
