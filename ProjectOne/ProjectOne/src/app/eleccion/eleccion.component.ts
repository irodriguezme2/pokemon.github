import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import {SplitButton} from 'primeng/splitbutton';
import { ToastModule } from 'primeng/toast';
import { MenuItem, MessageService } from 'primeng/api';


interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];         // Nuevos campos
  movimientos?: string[]; // Nuevos campos
}

@Component({
  selector: 'app-eleccion',
  standalone: true,
  templateUrl: './eleccion.component.html',
  styleUrls: ['./eleccion.component.css'],

  imports: [
    CommonModule,
    ButtonModule,
    HttpClientModule

  ]
})
export class EleccionComponent implements OnInit {

  pokemones: Pokemon[] = [];
  equipoSeleccionado: Pokemon[] = [];
  pokemonActual: Pokemon | null = null;

  // Parámetros para la API
  limit: number = 12;
  offset: number = 0;

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit() {
    this.cargarPokemones();
  }

  cargarPokemones() {
    const apiUrl = `https://pokeapi.co/api/v2/pokemon?limit=${this.limit}&offset=${this.offset}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        this.pokemones = data.results.map((poke: any) => ({
          nombre: poke.name,
          imagen: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${this.obtenerIdDesdeUrl(poke.url)}.png`,
          seleccionado: false
        }));
      },
      error: (err) => {
        console.error('Error al cargar los Pokémon:', err);
      }
    });
  }

  obtenerIdDesdeUrl(url: string): number {
    const partes = url.split('/');
    return parseInt(partes[partes.length - 2]);
  }

  seleccionarPokemon(pokemon: Pokemon) {
    pokemon.seleccionado = !pokemon.seleccionado;

    if (pokemon.seleccionado) {
      this.equipoSeleccionado.push(pokemon);
    } else {
      this.equipoSeleccionado = this.equipoSeleccionado.filter(p => p !== pokemon);
    }

    // Establecer temporalmente el Pokémon actual (sin detalles)
    this.pokemonActual = pokemon;

    // Obtener detalles desde la API
    const apiUrl = `https://pokeapi.co/api/v2/pokemon/${pokemon.nombre}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        const tipos = data.types.map((t: any) => t.type.name);
        const movimientos = data.moves.slice(0, 5).map((m: any) => m.move.name); // Solo primeros 5

        // Actualizar el Pokémon actual con tipos y movimientos
        this.pokemonActual = {
          ...pokemon,
          tipo: tipos,
          movimientos: movimientos
        };
      },
      error: (err) => {
        console.error('Error al cargar detalles del Pokémon:', err);
      }
    });
  }

  anterior(): void {
    if (this.offset >= this.limit) {
      this.offset -= this.limit;
      this.cargarPokemones();
    }
  }

  siguiente(): void {
    this.offset += this.limit;
    this.cargarPokemones();
  }

  cambiarEquipo(): void {
    console.log('🔁 Cambiar equipo');
  }

  irSiguiente(): void {
    console.log('➡️ Ir a la siguiente pantalla con equipo:', this.equipoSeleccionado);
  }
}
