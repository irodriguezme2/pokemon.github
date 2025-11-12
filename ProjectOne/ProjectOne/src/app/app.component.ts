import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MusicService } from './component/musica/music.service';
import { MusicaComponent } from './component/musica/musica.component';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MusicaComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  mostrarBoton = true;

  constructor(public musicService: MusicService) {}

  ngOnInit() {

    if (!this.musicService['audio']) {
      this.musicService.play();
    }
  }

  toggleMute() {
    this.musicService.toggleMute();
  }
}
