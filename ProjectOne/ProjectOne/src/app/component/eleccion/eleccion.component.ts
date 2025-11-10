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
  selector: 'app-eleccion',
  standalone: true,
  templateUrl: './eleccion.component.html',
  styleUrls: ['./eleccion.component.css'],
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
export class EleccionComponent implements OnInit {
  pokemones: Pokemon[] = [];
  equipoSeleccionado: Pokemon[] = [];
  pokemonActual: Pokemon | null = null;

  limit: number = 12;
  offset: number = 0;

  equipos: Equipo[] = [];
  splitButtonItems: any[] = [];
  selectedEquipo: Equipo | null = null;

  // 🟦 Panel para guardar equipo
  dialogVisible: boolean = false;
  nombreEquipo: string = '';

  // 💬 Diálogo de mensajes personalizados
  dialogMensajeVisible: boolean = false;
  mensajeDialogo: string = '';

  constructor(
    private router: Router,
    private http: HttpClient,
    private equiposService: EquiposService
  ) {}

  ngOnInit() {
    const equipoGuardado = this.equiposService.obtenerEquipoTemporalJugador();
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
    this.equiposService.guardarEquipoTemporalJugador(equipo.pokemones);
    this.router.navigate(['/eleccion-invitado']);
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
        const movimientos = data.moves.slice(0, 5).map((m: any) => m.move.name);

        pokemon.tipo = tipos;
        pokemon.movimientos = movimientos;

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

  cambiarEquipo(): void {
    console.log('🔁 Cambiar equipo');
  }

  // 🧾 Panel para ingresar nombre del equipo
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
    this.router.navigate(['/eleccion-invitado']);
  }

  // 💬 Mostrar mensaje bonito tipo ventana emergente
  mostrarMensaje(texto: string): void {
    this.mensajeDialogo = texto;
    this.dialogMensajeVisible = true;
  }
}
