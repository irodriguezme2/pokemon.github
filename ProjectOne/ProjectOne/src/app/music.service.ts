import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';  // Para reactividad (ya incluido en Angular)

@Injectable({ providedIn: 'root' })
export class MusicService {
  private audio: HTMLAudioElement;  // Un solo Audio, creado una vez
  private _currentSongIndex = 0;
  private _volume = 0.65;
  private _muted = false;

  private songs = [
    { name: 'Canción 1', src: '/poke.mp3', image: 'assets/images/song1.png' },
    { name: 'Canción 2', src: '/audio.mp3', image: 'assets/images/song2.png' },
    { name: 'Canción 3', src: '/audio2.mp3', image: 'assets/images/song3.png' }
    // Agrega más si quieres
  ];

  // 🔹 Observables para reactividad en templates (usa | async)
  currentSongName$ = new BehaviorSubject<string>(this.songs[0]?.name || 'Sin canción');
  currentSongImage$ = new BehaviorSubject<string>(this.songs[0]?.image || '');
  muted$ = new BehaviorSubject<boolean>(false);
  volume$ = new BehaviorSubject<number>(this._volume);
  isPlaying$ = new BehaviorSubject<boolean>(false);  // Para estado play/pause si lo necesitas

  // 🔹 Getters legacy (mantengo para compatibilidad, pero usa observables en templates)
  get muted() { return this._muted; }
  get volume() { return this._volume; }
  get songsList() { return this.songs; }
  get currentSongIndex() { return this._currentSongIndex; }
  get currentSongName() { return this.songs[this._currentSongIndex]?.name || 'Sin canción'; }
  get currentSongSrc() { return this.songs[this._currentSongIndex]?.src || ''; }
  get currentSongImage() { return this.songs[this._currentSongIndex]?.image || ''; }

  constructor() {
    this.audio = new Audio();
    this.audio.volume = this._volume;
    this.audio.loop = true;

    // ✅ Evento para auto-siguiente al final (comenta si no quieres, ya que loop=true lo previene)
    this.audio.addEventListener('ended', () => {
      if (!this.audio.loop) {
        this.next();
      }
    });

    // Manejo de errores
    this.audio.addEventListener('error', (e) => {
      console.error('Error cargando audio:', e);
    });


    this.updateObservables();
  }

  // 🔹 Actualiza observables cuando cambie el índice o estado
  private updateObservables() {
    this.currentSongName$.next(this.currentSongName);
    this.currentSongImage$.next(this.currentSongImage);
  }

  // 🔹 Reproducir canción (usa load() en lugar de new Audio)
  play(index?: number) {
    if (index !== undefined && index >= 0 && index < this.songs.length) {
      this._currentSongIndex = index;
      this.updateObservables();  // Actualiza UI
    }

    const src = this.currentSongSrc;
    if (!src) {
      console.warn('No hay canción para reproducir');
      return;
    }

    // ✅ Pausa y resetea el Audio actual (un solo objeto)
    this.audio.pause();
    this.audio.currentTime = 0;
    this.audio.src = src;  // Cambia src
    this.applyVolume();  // Aplica volumen/mute actual
    this.audio.load();  // Recarga con nuevo src

    // Reproduce (maneja promesas para autoplay policies)
    this.audio.play().then(() => {
      this.isPlaying$.next(true);
      console.log('Reproduciendo:', this.currentSongName);
    }).catch(err => {
      console.error('Error al reproducir (quizás necesita clic usuario):', err);
    });
  }

  pause() {
    if (this.audio) {
      this.audio.pause();
      this.isPlaying$.next(false);
      console.log('Pausado');
    }
  }

  resume() {
    if (this.audio) {
      this.audio.play();
      this.isPlaying$.next(true);
    }
  }

  // 🔹 Siguiente canción
  next() {
    this._currentSongIndex = (this._currentSongIndex + 1) % this.songs.length;
    this.updateObservables();
    this.play();  // Reproduce la nueva automáticamente
    console.log('Siguiente:', this.currentSongName);
  }

  // 🔹 Canción anterior
  prev() {
    this._currentSongIndex = (this._currentSongIndex - 1 + this.songs.length) % this.songs.length;
    this.updateObservables();
    this.play();
    console.log('Anterior:', this.currentSongName);
  }

  // 🔹 Control de volumen (aplica inmediatamente)
  setVolume(vol: number) {
    this._volume = Math.max(0, Math.min(1, vol));  // Clamp entre 0 y 1
    this.volume$.next(this._volume);
    this.applyVolume();
    console.log('Volumen cambiado a:', vol);
  }

  // 🔹 Aplica volumen/mute al audio actual (método privado reutilizable)
  private applyVolume() {
    if (this.audio) {
      this.audio.muted = this._muted;  // Usa propiedad muted de HTML5
      this.audio.volume = this._muted ? 0 : this._volume;
    }
  }

  // 🔹 Silencio
  mute() {
    this._muted = true;
    this.muted$.next(true);
    this.applyVolume();
    console.log('Muted: true');
  }

  unmute() {
    this._muted = false;
    this.muted$.next(false);
    this.applyVolume();
    console.log('Muted: false');
  }

  // 🔹 Toggle mute (¡ahora funciona perfectamente con el botón!)
  toggleMute() {
    if (this._muted) {
      this.unmute();
    } else {
      this.mute();
    }
  }

  // 🔹 Inicializar (opcional, llama en ngOnInit del componente si quieres cargar primera canción)
  init() {
    this.play(0);  // Carga y reproduce la primera (o solo load sin play)
  }
}
