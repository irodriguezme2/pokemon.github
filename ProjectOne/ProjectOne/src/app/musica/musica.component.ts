import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MusicService } from '../music.service';

@Component({
  selector: 'app-musica',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './musica.component.html',
  styleUrls: ['./musica.component.css']
})
export class MusicaComponent {
  mostrarControles = false; // para mostrar u ocultar el panel completo
  isPlaying = true;         // estado del botón de play/pause

  constructor(public musicService: MusicService) {
    document.addEventListener('click', () => {
      this.musicService.play();
    }, { once: true });
  }
  // 🔹 Muestra/oculta el botón flotante principal
  toggleMusic() {
    this.isPlaying = !this.isPlaying;

    if (this.isPlaying) {
      this.musicService.unmute();
      this.musicService.play();
    } else {
      this.musicService.pause();
    }
  }
  // 🔹 Abre el panel emergente
  mostrarVentana() {
    this.mostrarControles = true;
  }

  // 🔹 Cierra el panel emergente
  cerrarVentana() {
    this.mostrarControles = false;
  }

  // 🔹 Reproducir una canción específica
  reproducir(index?: number) {
    this.musicService.play(index);
    this.isPlaying = true;
  }

  // 🔹 Pausar canción actual
  pausar() {
    this.musicService.pause();
    this.isPlaying = false;
  }

  // 🔹 Siguiente canción
  siguiente() {
    this.musicService.next();
  }

  // 🔹 Canción anterior
  anterior() {
    this.musicService.prev();
  }

  // 🔹 Cambiar volumen
  cambiarVolumen(event: any) {
    const vol = parseFloat(event.target.value);
    this.musicService.setVolume(vol);
  }

  // 🔹 Silenciar / activar sonido
  toggleMute() {
    this.musicService.toggleMute();
  }
}
