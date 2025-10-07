import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalComponent } from './principal/principal.component';
import { UsuarioComponent } from './usuario/usuario.component';
import { PokemonesComponent } from './pokemones/pokemones.component';
import { EquiposComponent } from './equipos/equipos.component';
import { MusicaComponent } from './musica/musica.component';
import {InicioComponent} from './inicio/inicio.component';
import {RegistroComponent} from './registro/registro.component';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'principal', component: PrincipalComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'pokemones', component: PokemonesComponent },
  { path: 'equipos', component: EquiposComponent },
  { path: 'musica', component: MusicaComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
