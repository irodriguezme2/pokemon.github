import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { Avatar } from 'primeng/avatar';
import { Drawer } from 'primeng/drawer';
import { RouterLink } from '@angular/router';
import { MusicaComponent } from '../musica/musica.component';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  standalone: true,
  imports: [
    Avatar,
    Drawer,
    RouterLink,
    MusicaComponent
  ],
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements AfterViewInit {
  visible: boolean = false;

  user = {
    username: 'pikachu_master',
    nickname: 'Pikachu',
    photo: 'assets/usuario.png'
  };

  @ViewChild(MusicaComponent)
  musicaComponent!: MusicaComponent;

  ngAfterViewInit(): void {
    // Confirmamos que el componente se ha cargado correctamente
    if (this.musicaComponent) {
      console.log('🎶 Componente de música disponible');
    }
  }

  logout() {
    // lógica de cierre de sesión
  }

  toggleMusicaPopup() {
    if (this.musicaComponent) {
      this.musicaComponent.mostrarVentana();
    }
  }
}
