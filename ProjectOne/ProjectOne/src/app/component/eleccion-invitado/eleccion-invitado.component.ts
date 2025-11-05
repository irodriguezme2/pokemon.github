import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { SplitButtonModule } from 'primeng/splitbutton';
import { Router } from '@angular/router';
import { Equipo, EquiposService } from '../equipos/equipos.service';

interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];
  movimientos?: string[];
}

@Component({
  selector: 'app-eleccion-invitado',
  standalone: true,
  templateUrl: './eleccion-invitado.component.html',
  styleUrl: './eleccion-invitado.component.css',
  imports: [
    CommonModule,
    ButtonModule,
    HttpClientModule,
    SplitButtonModule
  ]
})
export class EleccionInvitadoComponent implements OnInit {
  pokemones: Pokemon[] = [];
  equipoSeleccionado: Pokemon[] = [];
  pokemonActual: Pokemon | null = null;

  limit: number = 12;
  offset: number = 0;

  equipos: Equipo[] = [];
  splitButtonItems: any[] = [];
  selectedEquipo: Equipo | null = null;

  constructor(
    private router: Router,
    private http: HttpClient,
    private equiposService: EquiposService
  ) {}

  ngOnInit() {
    // Recupera el equipo temporal del INVITADO si existe
    const equipoGuardado = this.equiposService.obtenerEquipoTemporalInvitado();
    if (equipoGuardado?.length) {
      this.equipoSeleccionado = equipoGuardado;
    }

    this.cargarPokemones();

    this.equiposService.getEquipos().subscribe(equipos => {
      this.equipos = equipos;
      this.splitButtonItems = this.equipos.map(equipo => ({
        label: equipo.nombre,
        icon: 'pi pi-users',
        command: () => this.seleccionarEquipo(equipo)
      }));
    });
  }

  seleccionarEquipo(equipo: Equipo) {
    this.selectedEquipo = equipo;
    // Opcional: this.equipoSeleccionado = equipo.pokemones;
  }

  cargarPokemones() {
    const apiUrl = `https://pokeapi.co/api/v2/pokemon?limit=${this.limit}&offset=${this.offset}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        const nuevosPokemones = data.results.map((poke: any) => {
          const nombre = poke.name;
          const yaSeleccionado = this.equipoSeleccionado.find(p => p.nombre === nombre);

          return {
            nombre,
            imagen: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${this.obtenerIdDesdeUrl(poke.url)}.png`,
            seleccionado: !!yaSeleccionado,
            tipo: yaSeleccionado?.tipo || [],
            movimientos: yaSeleccionado?.movimientos || []
          };
        });

        this.pokemones = nuevosPokemones;
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
    const yaSeleccionado = this.equipoSeleccionado.find(p => p.nombre === pokemon.nombre);

    // Evitar seleccionar más de 6
    if (!yaSeleccionado && this.equipoSeleccionado.length >= 6) {
      alert('⚠️ Solo puedes seleccionar hasta 6 Pokémon para tu equipo.');
      return;
    }

    // Alternar estado seleccionado
    pokemon.seleccionado = !pokemon.seleccionado;

    if (pokemon.seleccionado) {
      this.equipoSeleccionado.push(pokemon);
    } else {
      this.equipoSeleccionado = this.equipoSeleccionado.filter(p => p.nombre !== pokemon.nombre);
    }

    // Mostrar info en el panel lateral
    this.pokemonActual = pokemon;

    // Si ya tiene tipo y movimientos, no volver a pedir
    if (pokemon.tipo?.length && pokemon.movimientos?.length) return;

    const apiUrl = `https://pokeapi.co/api/v2/pokemon/${pokemon.nombre}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        const tipos = data.types.map((t: any) => t.type.name);
        const movimientos = data.moves.slice(0, 5).map((m: any) => m.move.name);

        pokemon.tipo = tipos;
        pokemon.movimientos = movimientos;

        // Asegurar que el panel lateral se actualiza
        if (this.pokemonActual?.nombre === pokemon.nombre) {
          this.pokemonActual = { ...pokemon };
        }
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
    if (this.equipoSeleccionado.length < 6) {
      alert('⚠️ Debes seleccionar exactamente 6 Pokémon para continuar.');
      return;
    }
    this.equiposService.guardarEquipoTemporalInvitado(this.equipoSeleccionado);
    this.router.navigate(['/equipos']);
  }
}
