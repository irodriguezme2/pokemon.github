import {Component, OnInit} from '@angular/core';
import {Button} from 'primeng/button';
import {NgClass, NgForOf, NgIf, TitleCasePipe} from '@angular/common';
import {SplitButton} from 'primeng/splitbutton';
import {Equipo, EquiposService} from '../../service/equipos.service';
import {Router, RouterLink} from '@angular/router';
import {HttpClient} from '@angular/common/http';

interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];
  movimientos?: string[];
}

@Component({
  selector: 'app-eleccion-uno-vs-pc',
  standalone: true,
  templateUrl: './eleccion-uno-vs-pc.component.html',
  styleUrls: ['./eleccion-uno-vs-pc.component.css'],
  imports: [
    Button,
    NgForOf,
    NgIf,
    SplitButton,
    TitleCasePipe,
    NgClass,
    RouterLink
  ]
})
export class EleccionUnoVsPcComponent implements OnInit{
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
    private equiposService: EquiposService // <-- INYECTA EL SERVICIO
  ) {}

  ngOnInit() {
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
    // Aquí puedes cargar los pokemones del equipo si quieres
    // this.equipoSeleccionado = equipo.pokemones;
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

    this.pokemonActual = pokemon;

    const apiUrl = `https://pokeapi.co/api/v2/pokemon/${pokemon.nombre}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        const tipos = data.types.map((t: any) => t.type.name);
        const movimientos = data.moves.slice(0, 5).map((m: any) => m.move.name);

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
    if (this.equipoSeleccionado.length === 0) {
      alert('Selecciona al menos un Pokémon antes de continuar.');
      return;
    }

    // Guarda el equipo seleccionado temporalmente en el almacenamiento local
    localStorage.setItem('equipoJugador', JSON.stringify(this.equipoSeleccionado));

    // Redirige al componente de combate
    this.router.navigate(['/combate']);
  }

}
