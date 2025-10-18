import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RegistroComponent } from './registro/registro.component';
import { InicioComponent } from './inicio/inicio.component';
import { FormsModule } from '@angular/forms';
import { PrincipalComponent } from './principal/principal.component';
import { DrawerModule } from 'primeng/drawer';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { AvatarModule } from 'primeng/avatar';
import { UsuarioComponent } from './usuario/usuario.component';
import { PokemonesComponent } from './pokemones/pokemones.component';
import { EquiposComponent } from './equipos/equipos.component';
import { MusicaComponent } from './musica/musica.component';
import { EleccionComponent } from './eleccion/eleccion.component';
import { EleccionUnoVsPcComponent } from './eleccion-uno-vs-pc/eleccion-uno-vs-pc.component';
import { EleccionInvitadoComponent } from './eleccion-invitado/eleccion-invitado.component';
import {SplitButton} from 'primeng/splitbutton';
import { BatallaComponent } from './batalla/batalla.component';

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
    PrincipalComponent,
    MusicaComponent,
    EquiposComponent,
    PokemonesComponent,
    UsuarioComponent,
    InicioComponent,
    RegistroComponent,
    EleccionUnoVsPcComponent,
    SplitButton
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
