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

    if (this.pokemonGanador && !this.pokemonGanador.imagen) {
      const id = this.pokemonGanador.id;
      if (id) {
        this.pokemonGanador.imagen = `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif`;
      }
    }
  }
  volverAlMenu() {
    this.router.navigate(['/principal']);
  }
}
