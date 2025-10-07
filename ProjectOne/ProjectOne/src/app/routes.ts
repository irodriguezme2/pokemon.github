import { Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { RegistroComponent } from './registro/registro.component';
import { PrincipalComponent } from './principal/principal.component';
import {UsuarioComponent} from './usuario/usuario.component';
export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'principal', component: PrincipalComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'pokemones', component: PokemonesComponent },
  { path: 'equipos', component: EquiposComponent },
  { path: 'musica', component: MusicaComponent }
];
