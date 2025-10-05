import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // necesario para [(ngModel)]
import { RouterModule } from '@angular/router'; // necesario para router-outlet
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgOptimizedImage } from '@angular/common';

// Rutas y componentes propios
import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app.component';
import { InicioComponent } from './inicio/inicio';
import { RegistroComponent } from './registro/registro';

// PrimeNG 19
import { DatePickerModule } from 'primeng/datepicker';

@NgModule({
  declarations: [
    InicioComponent,
    RegistroComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule,
    DatePickerModule
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
