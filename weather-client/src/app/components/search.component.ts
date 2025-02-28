import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WeatherService } from '../WeatherService';
import { weatherDetails } from '../model';
import { Router } from '@angular/router';
import { CityListService } from '../CityListService';

@Component({
  selector: 'app-search',
  standalone: false,
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit{

  private fb = inject(FormBuilder)
  private weatherSvc = inject(WeatherService)
  protected form !: FormGroup
  private route = inject(Router)
  private cityListSvc = inject(CityListService)

  protected cityList: string[] = []
  protected weatherDetail !: weatherDetails;

  ngOnInit(): void {
  
    this.form = this.fb.group({
      city: this.fb.control<string>('')
    })

    this.cityList = this.cityListSvc.getCityList()

  }

  addToCityList() {
    const cityName: string = this.form.get('city')?.value
    console.info(">> City: ", cityName)

    if (cityName) {
      
      this.cityListSvc.addCity(cityName)
      this.cityList = this.cityListSvc.getCityList()
      this.form.reset();
    }
    this.form.reset()

  }

  getWeatherOnClick(city: string){
    console.info('City choosen: ', city)
  
    this.route.navigate(['/weather', city], {
      state: {cityList: this.cityList}
    })
  }


}
