import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { EquiposService } from '../../service/equipos.service';

@Component({
  selector: 'app-premiacion',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './premiacion.component.html',
  styleUrls: ['./premiacion.component.css']
})
export class PremiacionComponent implements OnInit {
  ganador!: string;
  pokemonGanador!: any;

  constructor(private equiposService: EquiposService, private router: Router) {}

  ngOnInit() {
    const resultado = this.equiposService.obtenerResultadoCombate();
    if (!resultado) {
      this.router.navigate(['/premiacion']);
      return;
    }

    this.ganador = resultado.ganador;
    this.pokemonGanador = resultado.pokemonGanador;
  }

  volverAlMenu() {
    this.router.navigate(['/principal']);
  }
}
