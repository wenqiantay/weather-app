import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchComponent } from './components/search.component';
import { DisplayComponent } from './components/display.component';
import { provideHttpClient } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { WeatherService } from './WeatherService';
import { CityListService } from './CityListService';

const appRoutes: Routes = [
  { path: '', component: SearchComponent},
  { path: 'weather/:city', component: DisplayComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    DisplayComponent
  ],
  imports: [
    BrowserModule, ReactiveFormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [ provideHttpClient(), WeatherService, CityListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
