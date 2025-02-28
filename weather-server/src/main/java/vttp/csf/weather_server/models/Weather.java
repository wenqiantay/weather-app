package vttp.csf.weather_server.models;

import java.time.Instant;

import org.springframework.stereotype.Component;

@Component
public class Weather {
    
    private String city;
    private String main;
    private String description;
    private String icon;
    private Double temp;
    private Double feels_like;
    private Double temp_min;
    private Double temp_max;
    private int pressure;
    private int humidity;
    private Instant timeStamp;

    public String getMain() {
        return main;
    }
    public void setMain(String main) {
        this.main = main;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Double getTemp() {
        return temp;
    }
    public void setTemp(Double temp) {
        this.temp = temp;
    }
    public Double getFeels_like() {
        return feels_like;
    }
    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }
    public Double getTemp_min() {
        return temp_min;
    }
    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }
    public Double getTemp_max() {
        return temp_max;
    }
    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }
    public int getPressure() {
        return pressure;
    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    public int getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public Instant getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    @Override
    public String toString() {
        return "Weather [city=" + city + ", main=" + main + ", description=" + description + ", icon=" + icon
                + ", temp=" + temp + ", feels_like=" + feels_like + ", temp_min=" + temp_min + ", temp_max=" + temp_max
                + ", pressure=" + pressure + ", humidity=" + humidity + ", timeStamp=" + timeStamp + "]";
    }

   
    
    
}
