import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MusicButtonComponent } from '../music-button/music-button.component';
import { MusicaComponent } from '../musica/musica.component';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [RouterModule, MusicButtonComponent, MusicaComponent],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {}
