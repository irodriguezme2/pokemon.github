import { Component } from '@angular/core';
import { MusicService } from '../musica/music.service';
import { SliderModule } from 'primeng/slider';
import { FormsModule } from '@angular/forms';
import { DecimalPipe, NgClass } from '@angular/common';

@Component({
  selector: 'app-music-button',
  standalone: true,
  imports: [SliderModule, FormsModule, DecimalPipe, NgClass],
  templateUrl: './music-button.component.html',
  styleUrls: ['./music-button.component.css']
})
export class MusicButtonComponent {
  showSlider = false;
  volume = 0.65;

  constructor(public musicService: MusicService) {}

  ngOnInit() {
    this.musicService.setVolume(this.volume);
    this.musicService.play();
  }

  toggleMute() {
    this.musicService.toggleMute();
  }

  setVolume(value: number) {
    this.musicService.setVolume(value);
    if (this.musicService.muted && value > 0) this.musicService.unmute();
  }
}
