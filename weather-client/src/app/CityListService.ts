import { Injectable } from "@angular/core";

@Injectable()

export class CityListService {

    private cityList: string[] = []

    getCityList(): string[] {
      return this.cityList
    }
  
    addCity(city: string): void {
      if (this.cityList.indexOf(city) === -1) {
        this.cityList.push(city)
      }
    }
  
    clearCityList(): void {
      this.cityList = []
    }
}
