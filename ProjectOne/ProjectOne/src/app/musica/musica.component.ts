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
  isPlaying = true;         // estado del botón

  constructor(public musicService: MusicService) {
    this.musicService.play(); // empieza a sonar la música de fondo
  }

  toggleMusic() {
    this.isPlaying = !this.isPlaying;
    if (this.isPlaying) {
      this.musicService.unmute();
      this.musicService.play();
    } else {
      this.musicService.mute();
    }
  }
  mostrarVentana() {
    this.mostrarControles = true;
  }

  cerrarVentana() {
    this.mostrarControles = false;
  }

  reproducir(index: number) {
    this.musicService.play(index);
  }

  cambiarVolumen(event: any) {
    const vol = parseFloat(event.target.value);
    this.musicService.setVolume(vol);
  }

  toggleMute() {
    this.musicService.toggleMute();
  }
}
