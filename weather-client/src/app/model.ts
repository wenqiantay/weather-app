export interface weatherList {
    weatherlist: string []
}

export interface weatherDetails {
    id: String
    city: String
    main: String
    description: String
    icon: String
    temp: Number
    feels_like: Number
    temp_min: Number
    temp_max: Number
    pressure: Number
    humidity: Number
    timeStamp: String
}