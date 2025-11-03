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
  equipoJugador: Pokemon[] = [];
  equipoInvitado: Pokemon[] = [];
  equiposGuardados: Equipo[] = [];

  constructor(
    private equiposService: EquiposService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.equipoJugador = this.equiposService.obtenerEquipoTemporalJugador();
    this.equipoInvitado = this.equiposService.obtenerEquipoTemporalInvitado();

    this.equiposService.getEquipos().subscribe(equipos => {
      this.equiposGuardados = equipos;
    });

  }

  cancelar(): void {
    this.router.navigate(['/eleccion-invitado']);
  }

  siguiente(): void {
    this.router.navigate(['/combate']);
  }

}
