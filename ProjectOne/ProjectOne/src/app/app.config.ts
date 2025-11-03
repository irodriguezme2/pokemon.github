import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { provideAnimations } from '@angular/platform-browser/animations';
import {InicioComponent} from './inicio/inicio.component';
import {RegistroComponent} from './registro/registro.component';
import {PrincipalComponent} from './principal/principal.component';
import {UsuarioComponent} from './usuario/usuario.component';
import {PokemonesComponent} from './pokemones/pokemones.component';
import {EquiposComponent} from './equipos/equipos.component';
import {MusicaComponent} from './musica/musica.component';
import {EleccionComponent} from './eleccion/eleccion.component';
import {EleccionUnoVsPcComponent} from './eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import {CombateComponent} from './combate/combate.component';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'principal', component: PrincipalComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'pokemones', component: PokemonesComponent },
  { path: 'equipos', component: EquiposComponent },
  { path: 'musica', component: MusicaComponent },
  { path: 'eleccion-uno-vs-pc', component: EleccionUnoVsPcComponent },
  { path: 'eleccion', component: EleccionComponent },
  { path: 'combate', component: CombateComponent }

];

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    providePrimeNG({
      theme: {
        preset: Aura
      }
    }),
    provideAnimations()
  ]
};
