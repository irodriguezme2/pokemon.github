import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { provideAnimations } from '@angular/platform-browser/animations';
import {InicioComponent} from './component/inicio/inicio.component';
import {RegistroComponent} from './component/registro/registro.component';
import {PrincipalComponent} from './component/principal/principal.component';
import {UsuarioComponent} from './component/usuario/usuario.component';
import {PokemonesComponent} from './component/pokemones/pokemones.component';
import {EquiposComponent} from './component/equipos/equipos.component';
import {MusicaComponent} from './component/musica/musica.component';
import {EleccionComponent} from './component/eleccion/eleccion.component';
import {EleccionUnoVsPcComponent} from './component/eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import {CombateComponent} from './component/combate/combate.component';
import {EquiposPcComponent} from './component/equipos-pc/equipos-pc.component';
import {CombatePCComponent} from './component/combate-pc/combate-pc.component';
import {PremiacionComponent} from './component/premiacion/premiacion.component';

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
  { path: 'combate', component: CombateComponent },
  { path: 'equipo-pc', component: EquiposPcComponent},
  { path: 'combate-pc', component: CombatePCComponent},
  { path: 'premiacion', component: PremiacionComponent}

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
