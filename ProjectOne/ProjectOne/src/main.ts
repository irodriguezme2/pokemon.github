import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';

import { AppComponent } from './app/app.component';
import { InicioComponent } from './app/inicio/inicio.component';
import { RegistroComponent } from './app/registro/registro.component';
import { PrincipalComponent } from './app/principal/principal.component';
import { UsuarioComponent } from './app/usuario/usuario.component';
import { PokemonesComponent } from './app/pokemones/pokemones.component';
import { MusicaComponent } from './app/musica/musica.component';
import { EquiposComponent } from './app/equipos/equipos.component';
import { EleccionComponent } from './app/eleccion/eleccion.component'
import { EleccionUnoVsPcComponent } from './app/eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import {EleccionInvitadoComponent} from './app/eleccion-invitado/eleccion-invitado.component';
import {CombateComponent} from './app/combate/combate.component';
import {BatallaComponent} from './app/batalla/batalla.component';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'principal', component: PrincipalComponent },
  { path: 'usuario', component: UsuarioComponent },
  { path: 'pokemones', component: PokemonesComponent },
  { path: 'equipos', component: EquiposComponent },
  { path: 'musica', component: MusicaComponent },
  {path : 'eleccion', component:EleccionComponent},
  { path: 'eleccionuno', component: EleccionUnoVsPcComponent },
  { path: 'eleccion-invitado', component: EleccionInvitadoComponent },
  { path: 'combate', component: CombateComponent },
  { path: 'batalla', component: BatallaComponent }
];

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    providePrimeNG({
      theme: {
        preset: Aura,
        options: {
          darkModeSelector: false
        }
      }
    }),
    provideAnimations()
  ]
});
