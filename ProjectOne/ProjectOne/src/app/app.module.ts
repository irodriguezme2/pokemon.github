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
import { BatallaComponent } from './component/batalla/batalla.component';
import { CommonModule } from '@angular/common';

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
    InicioComponent,
    RegistroComponent,
    EleccionUnoVsPcComponent,
    SplitButton,
    BatallaComponent,
    CommonModule
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
