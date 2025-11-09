import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';

import { AppComponent } from './app/app.component';
import { InicioComponent } from './app/component/inicio/inicio.component';
import { RegistroComponent } from './app/component/registro/registro.component';
import { PrincipalComponent } from './app/component/principal/principal.component';
import { UsuarioComponent } from './app/component/usuario/usuario.component';
import { PokemonesComponent } from './app/component/pokemones/pokemones.component';
import { MusicaComponent } from './app/component/musica/musica.component';
import { EquiposComponent } from './app/component/equipos/equipos.component';
import { EleccionComponent } from './app/component/eleccion/eleccion.component'
import { EleccionUnoVsPcComponent } from './app/component/eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import { EleccionInvitadoComponent } from './app/component/eleccion-invitado/eleccion-invitado.component';
import { CombateComponent } from './app/component/combate/combate.component';
import { BatallaComponent } from './app/component/batalla/batalla.component';
import { MisEquiposComponent } from './app/component/mis-equipos/mis-equipos.component';
import { PremiacionComponent} from './app/component/premiacion/premiacion.component';

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
  { path: 'batalla', component: BatallaComponent },
  { path: 'mis-equipos', component: MisEquiposComponent},
  { path: 'premiacion', component: PremiacionComponent}

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
