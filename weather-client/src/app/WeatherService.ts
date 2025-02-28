import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { weatherDetails } from "./model";

@Injectable()
export class WeatherService {
    
    private http = inject(HttpClient)

    searchWeatherAsPromise(q: string): Promise<weatherDetails> {
        const params = new HttpParams()
            .set('q', q)
        
        return firstValueFrom(
            this.http.get<weatherDetails>('/api/weather', { params })
        )
    }
}