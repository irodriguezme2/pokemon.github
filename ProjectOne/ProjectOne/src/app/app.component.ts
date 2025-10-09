import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MusicService } from './music.service';
import { MusicaComponent } from './musica/musica.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MusicaComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  mostrarBoton = true; // ✅ Siempre visible

  constructor(public musicService: MusicService) {}

  ngOnInit() {
    // ✅ Inicia la música al entrar a la app
    if (!this.musicService['audio']) {
      this.musicService.play();
    }
  }

  toggleMute() {
    this.musicService.toggleMute();
  }
}
