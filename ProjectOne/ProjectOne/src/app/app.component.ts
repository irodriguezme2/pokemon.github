import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs/operators';
import { MusicService } from './music.service';
import { MusicaComponent } from './musica/musica.component'; // 👈 AGREGA ESTA LÍNEA

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MusicaComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  mostrarBoton = false; // control del botón

  constructor(
    public musicService: MusicService,
    private router: Router
  ) {}

  ngOnInit() {
    this.musicService.play(); // música de fondo

    // Escuchar cambios de ruta
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      const url = event.urlAfterRedirects;

      // ✅ Mostrar solo en estas rutas:
      this.mostrarBoton = url.includes('/inicio') || url.includes('/registro');
    });
  }

  toggleMute() {
    this.musicService.toggleMute();
  }
}
