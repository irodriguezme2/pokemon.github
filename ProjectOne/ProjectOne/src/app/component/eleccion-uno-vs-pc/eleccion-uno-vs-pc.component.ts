import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { SplitButtonModule } from 'primeng/splitbutton';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { EquiposService, Equipo } from '../../service/equipos.service';

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
    CommonModule,
    ButtonModule,
    HttpClientModule,
    SplitButtonModule,
    DialogModule,
    InputTextModule,
    FormsModule
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

  dialogVisible: boolean = false;
  nombreEquipo: string = '';

  dialogMensajeVisible: boolean = false;
  mensajeDialogo: string = '';

  busqueda: string = '';
  pokemonesFiltrados: Pokemon[] = [];

  constructor(
    private router: Router,
    private http: HttpClient,
    private equiposService: EquiposService
  ) {}

  ngOnInit() {
    const equipoGuardado = this.equiposService.obtenerEquipoTemporalJugador();
    if (equipoGuardado?.length) {
      this.equipoSeleccionado = [];
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
    this.equiposService.guardarEquipoTemporalJugador(equipo.pokemones);
    this.router.navigate(['/equipo-pc']);
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
            tipo: false,
            movimientos: yaSeleccionado?.movimientos || []
          };
        });
        this.pokemones = nuevosPokemones;
        this.pokemonesFiltrados = [...this.pokemones];
      },
      error: (err) => console.error('Error al cargar los Pokémon:', err)
    });
  }

  obtenerIdDesdeUrl(url: string): number {
    const partes = url.split('/');
    return parseInt(partes[partes.length - 2]);
  }

  seleccionarPokemon(pokemon: Pokemon) {
    const yaSeleccionado = this.equipoSeleccionado.find(p => p.nombre === pokemon.nombre);

    if (!yaSeleccionado && this.equipoSeleccionado.length >= 6) {
      this.mostrarMensaje('⚠️ Solo puedes seleccionar hasta 6 Pokémon para tu equipo.');
      return;
    }

    pokemon.seleccionado = !pokemon.seleccionado;

    if (pokemon.seleccionado) {
      this.equipoSeleccionado.push(pokemon);
    } else {
      this.equipoSeleccionado = this.equipoSeleccionado.filter(p => p.nombre !== pokemon.nombre);
    }

    this.pokemonActual = pokemon;

    if (pokemon.tipo?.length && pokemon.movimientos?.length) return;

    const apiUrl = `https://pokeapi.co/api/v2/pokemon/${pokemon.nombre}`;
    this.http.get<any>(apiUrl).subscribe({
      next: (data) => {
        const tipos = data.types.map((t: any) => t.type.name);

        // 🎲 Selecciona 5 movimientos aleatorios únicos
        const totalMovimientos = data.moves.length;
        let movimientosAleatorios: string[] = [];

        if (totalMovimientos > 0) {
          const mezclados = data.moves.sort(() => Math.random() - 0.5);
          movimientosAleatorios = mezclados.slice(0, 5).map((m: any) => m.move.name);
        }

        pokemon.tipo = tipos;
        pokemon.movimientos = movimientosAleatorios;

        if (this.pokemonActual?.nombre === pokemon.nombre) {
          this.pokemonActual = { ...pokemon };
        }
      },
      error: (err) => console.error('Error al cargar detalles del Pokémon:', err)
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

  filtrarPokemones(): void {
    const texto = this.busqueda.toLowerCase().trim();

    if (!texto) {
      this.pokemonesFiltrados = [...this.pokemones];
      return;
    }

    const filtradosLocales = this.pokemones.filter((p: Pokemon) =>
      p.nombre.toLowerCase().includes(texto)
    );

    this.pokemonesFiltrados = [...filtradosLocales];

    this.http.get<any>('https://pokeapi.co/api/v2/pokemon?limit=2000').subscribe({
      next: (data: any) => {
        const coincidencias = data.results.filter((poke: any) =>
          poke.name.toLowerCase().includes(texto)
        );
        const nuevosPokemones: Pokemon[] = coincidencias.map((poke: any) => {
          const id = this.obtenerIdDesdeUrl(poke.url);
          return {
            nombre: poke.name,
            imagen: `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png`,
            seleccionado: false,
            tipo: [],
            movimientos: []
          };
        });

        const nombresExistentes = new Set(this.pokemonesFiltrados.map(p => p.nombre));
        this.pokemonesFiltrados = [
          ...this.pokemonesFiltrados,
          ...nuevosPokemones.filter(p => !nombresExistentes.has(p.nombre))
        ];
      },
      error: (err: any) => console.error('Error al buscar Pokémon similares:', err)
    });
  }

  cambiarEquipo(): void {
    console.log('🔁 Cambiar equipo');
  }

  mostrarDialogoNombre(): void {
    if (this.equipoSeleccionado.length < 6) {
      this.mostrarMensaje('⚠️ Debes seleccionar exactamente 6 Pokémon para continuar.');
      return;
    }
    this.dialogVisible = true;
  }

  cerrarDialogo(): void {
    this.dialogVisible = false;
    this.nombreEquipo = '';
  }

  guardarNombreEquipo(): void {
    const nuevoEquipo: Equipo = {
      id: Date.now(),
      nombre: this.nombreEquipo.trim(),
      pokemones: this.equipoSeleccionado
    };

    this.equiposService.agregarEquipo(nuevoEquipo);
    this.dialogVisible = false;

    this.equiposService.guardarEquipoTemporalJugador(this.equipoSeleccionado);
    this.router.navigate(['/equipo-pc']);
  }

  mostrarMensaje(texto: string): void {
    this.mensajeDialogo = texto;
    this.dialogMensajeVisible = true;
  }
}
