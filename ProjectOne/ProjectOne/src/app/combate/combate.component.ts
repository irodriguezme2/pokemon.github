import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquiposService } from '../equipos.service';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Movimiento {
  nombre: string;
  tipo?: string;
  poder?: number;
}

interface Pokemon {
  id: number;
  nombre: string;
  imagen: string;
  tipo?: string[];
  movimientos?: Movimiento[];
  hp?: number;
}

@Component({
  selector: 'app-combate',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './combate.component.html',
  styleUrls: ['./combate.component.css'],
})
export class CombateComponent implements OnInit {
  equipoJugador: Pokemon[] = [];
  equipoInvitado: Pokemon[] = [];

  pokemonJugador!: Pokemon;
  pokemonInvitado!: Pokemon;

  turnoJugador = true;
  mensaje = '';

  // Menús jugador
  mostrandoMenu = false;
  mostrandoAtaques = false;
  mostrandoCambio = false;
  movimientos: Movimiento[] = [];

  // Menús invitado
  mostrandoMenuInvitado = false;
  mostrandoAtaquesInvitado = false;
  mostrandoCambioInvitado = false;
  movimientosInvitado: Movimiento[] = [];

  constructor(
    private equiposService: EquiposService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.equipoJugador = this.equiposService.obtenerEquipoTemporalJugador();
    this.equipoInvitado = this.equiposService.obtenerEquipoTemporalInvitado();

    if (!this.equipoJugador.length || !this.equipoInvitado.length) {
      alert('⚠️ Debes elegir ambos equipos primero.');
      this.router.navigate(['/eleccion']);
      return;
    }

    this.iniciarCombate();
  }

  iniciarCombate() {
    // Primer Pokémon de cada equipo
    this.pokemonJugador = { ...this.equipoJugador[0], hp: 100 };
    this.pokemonInvitado = { ...this.equipoInvitado[0], hp: 100 };

    // Sprites 3D modernos
    const obtenerGif = (poke: any) => {
      const id = poke.id || poke.url?.split('/').filter(Boolean).pop();
      if (id)
        return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif`;
      return poke.imagen;
    };

    this.pokemonJugador.imagen = obtenerGif(this.pokemonJugador);
    this.pokemonInvitado.imagen = obtenerGif(this.pokemonInvitado);

    this.turnoJugador = true;
    this.mensaje = '¡Es tu turno!';
    this.mostrandoMenu = true;

    this.cargarMovimientos(this.pokemonJugador.nombre, true);
    this.cargarMovimientos(this.pokemonInvitado.nombre, false);

  }

  /** Cargar movimientos reales desde la PokéAPI */
  cargarMovimientos(nombre: string, esJugador: boolean) {
    if (!nombre) return;

    const nombreFormateado = nombre.toLowerCase().replace(/\s+/g, '-');

    this.http.get<any>(`https://pokeapi.co/api/v2/pokemon/${nombreFormateado}`).subscribe({
      next: (data) => {
        const movs = data.moves.slice(0, 4).map((m: any) => ({
          nombre: m.move.name
        }));
        if (esJugador) this.movimientos = movs;
        else this.movimientosInvitado = movs;
      },
      error: (err) => {
        console.error('❌ Error al obtener movimientos:', err);
        this.mensaje = '⚠️ No se pudieron cargar los movimientos desde la PokéAPI.';
        if (esJugador) this.movimientos = [];
        else this.movimientosInvitado = [];
      }
    });
  }


  // ===== Menús jugador =====
  mostrarMenu() {
    this.mostrandoMenu = true;
    this.mostrandoAtaques = false;
    this.mostrandoCambio = false;
  }

  elegirAtacar() {
    this.mostrandoMenu = false;
    this.mostrandoAtaques = true;
    this.mostrandoCambio = false;
  }

  elegirCambiar() {
    this.mostrandoMenu = false;
    this.mostrandoAtaques = false;
    this.mostrandoCambio = true;
  }

  usarMovimiento(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 20) + 10;
    this.pokemonInvitado.hp = Math.max(0, (this.pokemonInvitado.hp ?? 0) - daño);
    this.mensaje = `${this.pokemonJugador.nombre} usó ${movimiento.nombre}!`;

    this.mostrandoAtaques = false;
    this.turnoJugador = false;

    // 👉 Ahora pasa el turno al invitado
    setTimeout(() => {
      this.mensaje = 'Turno del invitado';
      this.mostrandoMenuInvitado = true;
    }, 2000);
  }

  cambiarPokemon(p: Pokemon) {
    if (!p) return;
    if (p.nombre === this.pokemonJugador?.nombre) return;

    // Cambia al nuevo Pokémon y asegura HP por defecto
    this.pokemonJugador = { ...p, hp: p.hp ?? 100 };
    this.mensaje = `¡Has cambiado a ${p.nombre}!`;

    // Cargar movimientos del nuevo (cargarMovimientos espera nombre:string)
    this.cargarMovimientos(p.nombre, true);

    // Cerrar vista de cambio y pasar turno MANUAL al invitado (humano)
    this.mostrandoCambio = false;
    this.turnoJugador = false;

    // Mostrar menú del invitado para que él/elija su acción
    // (pequeño retardo para que el cambio se vea en pantalla)
    setTimeout(() => {
      this.mensaje = 'Turno del invitado';
      this.mostrandoMenuInvitado = true;
    }, 800);
  }

  escapar() {
    alert('Has escapado del combate.');
    this.router.navigate(['/eleccion']);
  }

  // ===== Menús invitado =====
  mostrarMenuInvitado() {
    this.mostrandoMenuInvitado = true;
    this.mostrandoAtaquesInvitado = false;
    this.mostrandoCambioInvitado = false;
  }

  elegirAtacarInvitado() {
    this.mostrandoMenuInvitado = false;
    this.mostrandoAtaquesInvitado = true;
  }

  elegirCambiarInvitado() {
    this.mostrandoMenuInvitado = false;
    this.mostrandoCambioInvitado = true;
  }

  usarMovimientoInvitado(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 15) + 5;
    this.pokemonJugador.hp = Math.max(0, (this.pokemonJugador.hp ?? 0) - daño);
    this.mensaje = `${this.pokemonInvitado.nombre} usó ${movimiento.nombre}!`;

    this.mostrandoAtaquesInvitado = false;
    this.turnoJugador = true;

    setTimeout(() => {
      this.mensaje = 'Turno del jugador';
      this.mostrandoMenu = true;
    }, 2000);
  }
  cambiarPokemonInvitado(p: Pokemon) {
    if (!p) return;
    if (p.nombre === this.pokemonInvitado?.nombre) return;

    this.pokemonInvitado = { ...p, hp: p.hp ?? 100 };
    this.mensaje = `¡El invitado cambió a ${p.nombre}!`;

    this.cargarMovimientos(p.nombre, false);
    this.mostrandoCambioInvitado = false;

    setTimeout(() => {
      this.mensaje = 'Turno del jugador';
      this.turnoJugador = true;
      this.mostrandoMenu = true;
    }, 800);
  }
}
