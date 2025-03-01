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
    public Weather isDataCached(String city) {

        Criteria criteria = Criteria.where("city").is(city);
        Query query = Query.query(criteria);

        Weather weather = template.findOne(query, Weather.class, "weather_cache");

        if(weather !=null ){
            
            // Check if cached data still in database after 15 mins
            Instant now = Instant.now();
            Duration duration = Duration.between(weather.getTimeStamp(), now);

            
            if(duration.toMinutes() <= 15) {

                return weather;

            } else { 

                removeCachedData(city);
            }
        }

        return null;
    }

    public void saveWeatherData(Weather weather) {
        weather.setTimeStamp(Instant.now()); 
        template.save(weather, "weather_cache"); 
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
