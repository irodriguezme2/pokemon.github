import { Component, OnInit } from '@angular/core';
import { MusicService } from '../music.service';
import { SliderModule } from 'primeng/slider';
import { FormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
@Component({
  selector: 'app-musica',
  standalone: true,
  imports: [SliderModule, FormsModule, DropdownModule],
  templateUrl: './musica.component.html',
  styleUrls: ['./musica.component.css']
})
export class MusicaComponent implements OnInit {
  songOptions: any[] = [];
  selectedSong: any = null;
  volume: number = 0.65;

  constructor(public musicService: MusicService) {}

  ngOnInit() {
    this.songOptions = this.musicService.songsList;
    this.selectedSong = this.songOptions[this.musicService.currentSongIndex];
    this.volume = this.musicService.volume;
  }

  changeSong(song: any) {
    const idx = this.songOptions.indexOf(song);
    if (idx >= 0) {
      this.musicService.play(idx);
      this.selectedSong = song;
    }
  }

  changeVolume(vol: number) {
    this.musicService.setVolume(vol);
    this.volume = vol;
    if (this.musicService.muted && vol > 0) this.musicService.unmute();
  }

  toggleMute() {
    this.musicService.toggleMute();
  }
}
