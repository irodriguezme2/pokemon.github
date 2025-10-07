import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {RegistroComponent} from './registro/registro.component';
import {InicioComponent} from './inicio/inicio.component';
import {FormsModule} from '@angular/forms';
import { PrincipalComponent } from './principal/principal.component';
import {Drawer, DrawerModule} from 'primeng/drawer';
import {ButtonModule} from 'primeng/button';
import {RippleModule} from 'primeng/ripple';
import {AvatarModule} from 'primeng/avatar';
import { UsuarioComponent } from './usuario/usuario.component';
import { PokemonesComponent } from './pokemones/pokemones.component';
import { EquiposComponent } from './equipos/equipos.component';
import { MusicaComponent } from './musica/musica.component';

@NgModule({
  declarations: [


    UsuarioComponent,
        PokemonesComponent,
        EquiposComponent,
        MusicaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    Drawer,
    PrincipalComponent,
    FormsModule,
    DrawerModule,
    ButtonModule,
    RippleModule,
    AvatarModule,
  ],
  providers: [],
  bootstrap: []
})
export class AppModule { }
