import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {RegistroComponent} from './registro/registro.component';
import {InicioComponent} from './inicio/inicio.component';
import {FormsModule} from '@angular/forms';

import { PrincipalComponent } from './principal/principal.component';

@NgModule({
  declarations: [

  

         PrincipalComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
