import { Component, OnInit } from '@angular/core';
import {RouterModule} from '@angular/router';
import {MusicButtonComponent} from '../music-button/music-button.component';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [RouterModule, MusicButtonComponent],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent{}
