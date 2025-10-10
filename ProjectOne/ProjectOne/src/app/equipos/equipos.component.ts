import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquiposService, Equipo } from '../equipos.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];
  movimientos?: string[];
}

@Component({
  selector: 'app-equipos',
  standalone: true,
  templateUrl: './equipos.component.html',
  styleUrls: ['./equipos.component.css'],
  imports: [CommonModule, FormsModule]
})
export class EquiposComponent implements OnInit {
  nombreEquipo = '';
  equipoSeleccionado: Pokemon[] = [];

  constructor(
    private equiposService: EquiposService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const equipoGuardado = this.equiposService.obtenerEquipoTemporal();
    if (equipoGuardado?.length) {
      this.equipoSeleccionado = equipoGuardado;
    }
  }

  crearEquipo(): void {
    if (!this.nombreEquipo.trim()) return;

    const nuevoEquipo: Equipo = {
      id: Date.now(),
      nombre: this.nombreEquipo,
      pokemones: this.equipoSeleccionado
    };

    this.equiposService.agregarEquipo(nuevoEquipo);
    this.nombreEquipo = '';
  }

  cancelar(): void {
    this.equiposService.guardarEquipoTemporal(this.equipoSeleccionado);
    this.router.navigate(['/eleccion']);
  }


  siguiente(): void {

    this.equiposService.guardarEquipoTemporal(this.equipoSeleccionado);
    this.router.navigate(['/eleccion-invitado']);
  }
}
