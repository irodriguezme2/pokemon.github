import { Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { RegistroComponent } from './registro/registro.component';

export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent }
];
