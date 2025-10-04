import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio';

const routes: Routes = [
  { path: '', component: InicioComponent },   // ruta raíz
  { path: 'inicio', component: InicioComponent } // ruta /inicio
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
