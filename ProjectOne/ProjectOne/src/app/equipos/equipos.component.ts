import { Component } from '@angular/core';
import { EquiposService, Equipo } from './equipos.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-equipos',
  standalone: true,
  templateUrl: './equipos.component.html',
  styleUrls: ['./equipos.component.css'],
  imports: [CommonModule, FormsModule]
})
export class EquiposComponent {
  nombreEquipo = '';

  constructor(private equiposService: EquiposService) {}

  crearEquipo() {
    if (!this.nombreEquipo.trim()) return;
    const nuevoEquipo: Equipo = {
      id: Date.now(),
      nombre: this.nombreEquipo,
      pokemones: []
    };
    this.equiposService.agregarEquipo(nuevoEquipo);
    this.nombreEquipo = '';
  }
}
