import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquiposService, Equipo } from '../../service/equipos.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {ButtonDirective} from 'primeng/button';

interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];
  movimientos?: string[];
}

@Component({
  selector: 'app-equipos-pc',
  standalone: true,
  templateUrl: './equipos-pc.component.html',
  styleUrls: ['./equipos-pc.component.css'],
  imports: [CommonModule, FormsModule, ButtonDirective]
})
export class EquiposPcComponent implements OnInit {
  equipoJugador: Pokemon[] = [];
  equiposGuardados: Equipo[] = [];

  constructor(
    private equiposService: EquiposService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.equipoJugador = this.equiposService.obtenerEquipoTemporalJugador();
    this.equiposService.getEquipos().subscribe(equipos => {
      this.equiposGuardados = equipos;
    });

  }
  cancelar(): void {
    this.router.navigate(['/eleccion-uno-vs-pc']);
  }

  siguiente(): void {
    this.router.navigate(['/combate-pc']);
  }

}
