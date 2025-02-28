import { Component, inject, OnInit } from '@angular/core';
import { WeatherService } from '../WeatherService';
import { ActivatedRoute, Router } from '@angular/router';
import { weatherDetails } from '../model';

@Component({
  selector: 'app-display',
  standalone: false,
  templateUrl: './display.component.html',
  styleUrl: './display.component.css'
})
export class DisplayComponent implements OnInit{

  private activatedRoute = inject(ActivatedRoute)
  private router = inject(Router)
  private weatherSvc = inject(WeatherService)

  protected weatherDetail!: weatherDetails 
  
  protected city : string = ''

  ngOnInit(): void {
    this.city = this.activatedRoute.snapshot.paramMap.get('city') || ''

    if(this.city){
      this.getWeatherDetails(this.city)
    }
    

  }

  getWeatherDetails(city: string){

    this.weatherSvc.searchWeatherAsPromise(city)
      .then ( result => {
        this.weatherDetail = result
        console.info('>>> weather details: ', this.weatherDetail)
      }).catch (error => {

        console.error('>>>> error:' , error)
      })

  }

}
