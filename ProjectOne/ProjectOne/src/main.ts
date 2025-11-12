import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, Routes } from '@angular/router';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { AppComponent } from './app/app.component';
import { InicioComponent } from './app/component/inicio/inicio.component';
import { RegistroComponent } from './app/component/registro/registro.component';
import { PrincipalComponent } from './app/component/principal/principal.component';
import { PokemonesComponent } from './app/component/pokemones/pokemones.component';
import { MusicaComponent } from './app/component/musica/musica.component';
import { EquiposComponent } from './app/component/equipos/equipos.component';
import { EleccionComponent } from './app/component/eleccion/eleccion.component'
import { EleccionUnoVsPcComponent } from './app/component/eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import { EleccionInvitadoComponent } from './app/component/eleccion-invitado/eleccion-invitado.component';
import { CombateComponent } from './app/component/combate/combate.component';
import { MisEquiposComponent } from './app/component/mis-equipos/mis-equipos.component';
import { PremiacionComponent} from './app/component/premiacion/premiacion.component';
import { EquiposPcComponent} from './app/component/equipos-pc/equipos-pc.component';
import { CombatePCComponent} from './app/component/combate-pc/combate-pc.component';
import {provideHttpClient} from '@angular/common/http';
import {authGuard} from './app/service/auth.guard';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'principal', component: PrincipalComponent, canActivate: [authGuard]  },
  { path: 'pokemones', component: PokemonesComponent, canActivate: [authGuard]   },
  { path: 'equipos', component: EquiposComponent, canActivate: [authGuard]   },
  { path: 'musica', component: MusicaComponent, canActivate: [authGuard]   },
  {path : 'eleccion', component:EleccionComponent ,canActivate: [authGuard]  },
  { path: 'eleccion-uno-vs-pc', component: EleccionUnoVsPcComponent, canActivate: [authGuard]   },
  { path: 'eleccion-invitado', component: EleccionInvitadoComponent, canActivate: [authGuard]   },
  { path: 'combate', component: CombateComponent, canActivate: [authGuard]   },
  { path: 'mis-equipos', component: MisEquiposComponent, canActivate: [authGuard]  },
  { path: 'premiacion', component: PremiacionComponent,canActivate: [authGuard]  },
  { path: 'equipo-pc', component: EquiposPcComponent,canActivate: [authGuard]  },
  { path: 'combate-pc', component: CombatePCComponent, canActivate: [authGuard]  }

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
    provideHttpClient(),
    provideAnimations(),
  ]
});
