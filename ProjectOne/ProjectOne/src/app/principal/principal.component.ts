import { Component } from '@angular/core';
import {Avatar} from 'primeng/avatar';
import {Drawer} from 'primeng/drawer';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  imports: [
    Avatar,
    Drawer,
    RouterLink
  ],
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent {
  visible: boolean = false;


  user = {
    username: 'pikachu_master',
    nickname: 'Pikachu',
    photo: 'assets/usuario.png' // Cambia por la ruta de la imagen real
  };

  logout() {

  }
}
