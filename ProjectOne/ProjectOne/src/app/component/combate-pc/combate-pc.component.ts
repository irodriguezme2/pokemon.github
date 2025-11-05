import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquiposService } from '../../service/equipos.service';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

interface Movimiento {
  nombre: string;
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
  templateUrl: './combate-pc.component.html',
  styleUrls: ['./combate-pc.component.css']
})
export class  CombatePCComponent implements OnInit {
  equipoJugador: Pokemon[] = [];
  equipoInvitado: Pokemon[] = [];

  pokemonJugador!: Pokemon;
  pokemonInvitado!: Pokemon;

  turnoJugador = true;
  mensaje = '';
  mostrandoMenu = false;
  mostrandoAtaques = false;
  mostrandoCambio = false;
  movimientos: Movimiento[] = [];

  constructor(
    private equiposService: EquiposService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit() {
    // Obtener ambos equipos desde el servicio
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

    // Cargar sprites animados
    const obtenerGif = (poke: any) => {
      if (poke.id) {
        return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/versions/generation-v/black-white/animated/${poke.id}.gif`;
      } else if (poke.url) {
        const id = poke.url.split('/').filter(Boolean).pop();
        return `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/versions/generation-v/black-white/animated/${id}.gif`;
      }
      return poke.imagen;
    };

    this.pokemonJugador.imagen = obtenerGif(this.pokemonJugador);
    this.pokemonInvitado.imagen = obtenerGif(this.pokemonInvitado);

    this.turnoJugador = true;
    this.mensaje = '¡Es tu turno!';
    this.mostrandoMenu = true;

    this.cargarMovimientos(this.pokemonJugador.id);
  }

  /** Obtiene los 4 movimientos del Pokémon desde la PokéAPI */
  cargarMovimientos(id: number) {
    this.movimientos = [];
    this.http.get<any>(`https://pokeapi.co/api/v2/pokemon/${id}`).subscribe({
      next: (data) => {
        const movs = data.moves.slice(0, 4).map((m: any) => ({
          nombre: m.move.name
        }));
        this.movimientos = movs;
      },
      error: () => {
        this.movimientos = [
          { nombre: 'Tackle' },
          { nombre: 'Growl' },
          { nombre: 'Quick Attack' },
          { nombre: 'Leer' }
        ];
      }
    });
  }

  /** Menú principal (tu turno) */
  mostrarMenu() {
    this.mostrandoMenu = true;
    this.mostrandoAtaques = false;
    this.mostrandoCambio = false;
  }

  /** Al elegir atacar */
  elegirAtacar() {
    this.mostrandoMenu = false;
    this.mostrandoAtaques = true;
    this.mostrandoCambio = false;
  }

  /** Al elegir cambiar Pokémon */
  elegirCambiar() {
    this.mostrandoMenu = false;
    this.mostrandoAtaques = false;
    this.mostrandoCambio = true;
  }

  /** Atacar con un movimiento */
  usarMovimiento(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 20) + 10;
    this.pokemonInvitado.hp = Math.max(0, (this.pokemonInvitado.hp ?? 0) - daño);
    this.mensaje = `${this.pokemonJugador.nombre} usó ${movimiento.nombre} causando ${daño} de daño.`;

    this.mostrandoAtaques = false;
    this.turnoJugador = false;

    // Esperar 2 segundos antes del turno rival
    setTimeout(() => this.turnoInvitado(), 2000);
  }

  /** Cambiar Pokémon */
  cambiarPokemon(p: Pokemon) {
    if (p.nombre === this.pokemonJugador.nombre) return;

    this.pokemonJugador = { ...p, hp: p.hp ?? 100 };
    this.mensaje = `¡Has cambiado a ${p.nombre}!`;

    this.cargarMovimientos(p.id);
    this.mostrandoCambio = false;
    this.turnoJugador = false;

    setTimeout(() => this.turnoInvitado(), 2000);
  }

  /** Escapar (finaliza combate) */
  escapar() {
    alert('Has escapado del combate.');
    this.router.navigate(['/eleccion']);
  }

  /** Turno automático del invitado */
  turnoInvitado() {
    this.mensaje = `Turno del rival...`;
    const daño = Math.floor(Math.random() * 20) + 5;
    this.pokemonJugador.hp = Math.max(0, (this.pokemonJugador.hp ?? 0) - daño);

    setTimeout(() => {
      this.mensaje = `${this.pokemonInvitado.nombre} atacó causando ${daño} de daño!`;
      this.turnoJugador = true;
      this.mostrandoMenu = true;
    }, 1500);
  }
}
