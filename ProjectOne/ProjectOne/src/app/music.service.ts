import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class MusicService {
  private audio: HTMLAudioElement | null = null;
  private _muted = false;
  private _volume = 0.65;
  private _currentSongIndex = 0;
  private songs = [
    { name: 'Canción 1', src: 'assets/song1.mp3' },
    { name: 'Canción 2', src: 'assets/song2.mp3' },
    { name: 'Canción 3', src: 'assets/song3.mp3' },
    { name: 'Canción 4', src: 'assets/song4.mp3' },
    { name: 'Canción 5', src: 'assets/song5.mp3' }
  ];

  get muted() { return this._muted; }
  get volume() { return this._volume; }
  get songsList() { return this.songs; }
  get currentSongIndex() { return this._currentSongIndex; }
  get currentSongName() { return this.songs[this._currentSongIndex].name; }
  get currentSongSrc() { return this.songs[this._currentSongIndex].src; }

  play(index?: number) {
    if (index !== undefined) this._currentSongIndex = index;
    if (this.audio) {
      this.audio.pause();
      this.audio.currentTime = 0;
    }
    this.audio = new Audio(this.songs[this._currentSongIndex].src);
    this.audio.loop = true;
    this.audio.volume = this._muted ? 0 : this._volume;
    this.audio.play();
  }

  setVolume(vol: number) {
    this._volume = vol;
    if (this.audio) this.audio.volume = this._muted ? 0 : vol;
  }

  mute() {
    this._muted = true;
    if (this.audio) this.audio.volume = 0;
  }

  unmute() {
    this._muted = false;
    if (this.audio) this.audio.volume = this._volume;
  }

  toggleMute() {
    this._muted ? this.unmute() : this.mute();
  }
}
