import {Component, ViewChild, AfterViewInit, inject} from '@angular/core';
import { Avatar } from 'primeng/avatar';
import { Drawer } from 'primeng/drawer';
import {Router, RouterLink} from '@angular/router';
import { MusicaComponent } from '../musica/musica.component';
import {AuthService} from '../../service/auth.service';

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
  visible = false;

  user: any = {};

  @ViewChild(MusicaComponent)
  musicaComponent!: MusicaComponent;

  private authService = inject(AuthService);
  private router = inject(Router);

  ngAfterViewInit(): void {
    if (this.musicaComponent) {
      console.log('🎶 Componente de música disponible');
    }

    // ✅ Traer usuario actual desde el backend
    this.authService.getCurrentUser().subscribe({
      next: (user) => {
        this.user = user;
        console.log('Usuario actual:', this.user);
      },
      error: (err) => {
        console.error('Error al obtener usuario:', err);
      }
    });
  }

  logout() {
    localStorage.removeItem('token');
    sessionStorage.removeItem('token');
    console.log('Sesión cerrada');
    this.router.navigate(['/inicio']);
  }

  toggleMusicaPopup() {
    if (this.musicaComponent) {
      this.musicaComponent.mostrarVentana();
    }
  }
}
