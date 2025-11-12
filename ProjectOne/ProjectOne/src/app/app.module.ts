import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RegistroComponent } from './component/registro/registro.component';
import { InicioComponent } from './component/inicio/inicio.component';
import { FormsModule } from '@angular/forms';
import { PrincipalComponent } from './component/principal/principal.component';
import { DrawerModule } from 'primeng/drawer';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AvatarModule } from 'primeng/avatar';
import { UsuarioComponent } from './component/usuario/usuario.component';
import { PokemonesComponent } from './component/pokemones/pokemones.component';
import { EquiposComponent } from './component/equipos/equipos.component';
import { MusicaComponent } from './component/musica/musica.component';
import { EleccionComponent } from './component/eleccion/eleccion.component';
import { EleccionUnoVsPcComponent } from './component/eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import { EleccionInvitadoComponent } from './component/eleccion-invitado/eleccion-invitado.component';
import {SplitButton} from 'primeng/splitbutton';
import { CombateComponent } from './component/combate/combate.component';
import { CombatePCComponent } from './component/combate-pc/combate-pc.component';
import { CommonModule } from '@angular/common';
// @ts-ignore
import { MisEquiposComponent } from './component/mis-equipos/mis-equipos.component';
import { PremiacionComponent } from './component/premiacion/premiacion.component';
import {RouterLink} from '@angular/router';
import { EquiposPcComponent } from './component/equipos-pc/equipos-pc.component';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    FormsModule,
    DrawerModule,
    ButtonModule,
    RippleModule,
    AvatarModule,
    EleccionComponent,
    EleccionInvitadoComponent,
    PrincipalComponent,
    MusicaComponent,
    EquiposComponent,
    CombateComponent,
    CombatePCComponent,
    PokemonesComponent,
    UsuarioComponent,
    PremiacionComponent,
    EquiposPcComponent,
    InicioComponent,
    RegistroComponent,
    EleccionUnoVsPcComponent,
    SplitButton,
    CommonModule,
    RouterLink,
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
