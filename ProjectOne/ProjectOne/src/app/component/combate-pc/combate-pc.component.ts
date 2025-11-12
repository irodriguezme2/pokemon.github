import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquiposService } from '../../service/equipos.service';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';
import {forkJoin, lastValueFrom} from 'rxjs';

interface Movimiento {
  nombre: string;
  tipo?: string;
  poder?: number |string;
}
interface Pokemon {
  id?: number;
  nombre: string;
  imagen: string;
  tipo?: string[];
  movimientos?: Movimiento[];
  hp?: number;
  url?: string;
  colorHp?: string;
  vivo?: boolean;
}

@Component({
  selector: 'app-combate-pc',
  standalone: true,
  imports: [CommonModule, HttpClientModule, Button, Dialog],
  templateUrl: './combate-pc.component.html',
  styleUrls: ['./combate-pc.component.css']
})
export class CombatePCComponent implements OnInit {
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

  shakingJugador = false;
  shakingInvitado = false;
  flashJugador = false;
  flashInvitado = false;

  dialogMensajeVisible: boolean = false;
  mensajeDialogo: string = '';

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
      this.router.navigate(['/eleccion-uno-vs-pc']);
      return;
    }

    this.iniciarCombate();
  }

  iniciarCombate() {
    const prepararEquipo = (equipo: Pokemon[]) =>
      equipo.map((p) => ({
        ...p,
        imagen: this.obtenerGif(p),
        hp: (p.hp ?? 100),
        colorHp: p.colorHp ?? 'verde',
        vivo: p.vivo ?? true,
      }));

    this.equipoJugador = prepararEquipo(this.equipoJugador);
    this.equipoInvitado = prepararEquipo(this.equipoInvitado);

    this.pokemonJugador = {...this.equipoJugador.find(p => p.vivo) || this.equipoJugador[0]};
    this.pokemonInvitado = {...this.equipoInvitado.find(p => p.vivo) || this.equipoInvitado[0]};

    this.turnoJugador = true;
    this.mensaje = '¡Es tu turno!';
    this.mostrandoMenu = true;

    this.cargarMovimientos(this.pokemonJugador.nombre, true);
    this.cargarMovimientos(this.pokemonInvitado.nombre, false);
  }

  actualizarColorHp(pokemon: Pokemon) {
    if ((pokemon.hp ?? 0) > 60) pokemon.colorHp = 'verde';
    else if ((pokemon.hp ?? 0) > 30) pokemon.colorHp = 'amarillo';
    else pokemon.colorHp = 'rojo';
  }

  private guardarEstadoActualEnEquipo(team: 'jugador' | 'invitado') {
    if (team === 'jugador' && this.pokemonJugador) {
      this.equipoJugador = this.equipoJugador.map(p =>
        p.nombre === this.pokemonJugador.nombre
          ? {...p, hp: this.pokemonJugador.hp, colorHp: this.pokemonJugador.colorHp, vivo: this.pokemonJugador.hp! > 0}
          : p
      );
    } else if (team === 'invitado' && this.pokemonInvitado) {
      this.equipoInvitado = this.equipoInvitado.map(p =>
        p.nombre === this.pokemonInvitado.nombre
          ? {
            ...p,
            hp: this.pokemonInvitado.hp,
            colorHp: this.pokemonInvitado.colorHp,
            vivo: this.pokemonInvitado.hp! > 0
          }
          : p
      );
    }
  }
  async cargarMovimientos(nombre: string, esJugador: boolean) {
    if (!nombre) return;

    const nombreFormateado = nombre.toLowerCase().replace(/\s+/g, '-');

    try {
      // Obtener datos del Pokémon
      const data: any = await lastValueFrom(
        this.http.get(`https://pokeapi.co/api/v2/pokemon/${nombreFormateado}`)
      );

      if (!data.moves || data.moves.length === 0) {
        console.warn('⚠️ No se encontraron movimientos para este Pokémon.');
        this.mensaje = '⚠️ No se pudieron cargar los movimientos.';
        return;
      }

      const primeros4 = data.moves.slice(0, 4);

      // Obtener detalles de movimientos
      const detalles = await Promise.all(
        primeros4.map((m: any) => lastValueFrom(this.http.get(m.move.url)))
      );

      // Procesar movimientos
      const movs = detalles.map((d: any) => {
        if (!d) {
          console.warn('Movimiento sin datos:', d);
          return { nombre: 'Movimiento desconocido', tipo: 'normal', poder: 0 } as Movimiento;
        }

        const nombreEsp =
          d.names?.find((n: any) => n?.language?.name === 'es')?.name ||
          d.name?.replace(/-/g, ' ') || 'Movimiento desconocido';

        return {
          nombre: nombreEsp,
          tipo: d.type?.name || 'normal',
          poder: d.power || 0,
        } as Movimiento;
      });

      // Asignar movimientos
      if (esJugador) {
        this.movimientos = movs;
      } else {
        this.pokemonInvitado.movimientos = movs;
      }
    } catch (err) {
      console.error('❌ Error al obtener movimientos:', err);
      this.mensaje = '⚠️ No se pudieron cargar los movimientos desde la PokéAPI.';
    }
  }


  mostrarMenu() {
    this.mostrandoMenu = true;
    this.mostrandoAtaques = false;
    this.mostrandoCambio = false;
  }

  elegirAtacar() {
    this.mostrandoMenu = false;
    this.mostrandoAtaques = true;
  }

  elegirCambiar() {
    this.mostrandoMenu = false;
    this.mostrandoCambio = true;
  }

  usarMovimiento(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 20) + 10;
    this.pokemonInvitado.hp = Math.max(0, (this.pokemonInvitado.hp ?? 0) - daño);
    this.actualizarColorHp(this.pokemonInvitado);


    this.shakingInvitado = true;
    this.flashInvitado = true;
    setTimeout(() => {
      this.shakingInvitado = false;
    }, 500);
    setTimeout(() => {
      this.flashInvitado = false;
    }, 200);

    this.guardarEstadoActualEnEquipo('invitado');

    this.mensaje = `${this.pokemonJugador.nombre} usó ${movimiento.nombre}!`;
    this.mostrandoAtaques = false;
    this.turnoJugador = false;

    setTimeout(() => {
      if ((this.pokemonInvitado.hp ?? 0) <= 0) {
        this.derrotarPokemon(this.pokemonInvitado, false);
        return;
      }
      this.mensaje = 'Turno del invitado';
      this.turnoInvitado();  // Llama al turno del invitado en lugar de mostrar menú
    }, 1500);
  }

  derrotarPokemon(pokemon: Pokemon, esJugador: boolean) {
    pokemon.vivo = false;
    pokemon.hp = 0;

    if (esJugador) {
      this.equipoJugador = this.equipoJugador.map(p => p.nombre === pokemon.nombre ? {...p, vivo: false, hp: 0} : p);
    } else {
      this.equipoInvitado = this.equipoInvitado.map(p => p.nombre === pokemon.nombre ? {...p, vivo: false, hp: 0} : p);
    }

    this.mensaje = `💥 ${pokemon.nombre} fue derrotado!`;

    const equipo = esJugador ? this.equipoJugador : this.equipoInvitado;
    const siguiente = equipo.find(p => p.vivo);

    setTimeout(() => {
      if (siguiente) {
        if (esJugador) {
          this.pokemonJugador = {...siguiente};
          this.cargarMovimientos(siguiente.nombre, true);
          this.mensaje = `¡Has enviado a ${siguiente.nombre}!`;
          this.turnoInvitado();  // Turno del invitado después de cambiar
        } else {
          this.pokemonInvitado = {...siguiente};
          this.cargarMovimientos(siguiente.nombre, false);
          this.mensaje = `¡El invitado envió a ${siguiente.nombre}!`;
          this.turnoJugador = true;
          this.mostrandoMenu = true;
        }
      } else {
        this.mostrarDialogo(esJugador ? '¡Has perdido el combate!' : '¡Has ganado el combate!');
        setTimeout(() => this.router.navigate(['/premiacion']), 2500);  // Unificado a /premiacion
      }
    }, 1500);
  }

  cambiarPokemon(p: Pokemon) {
    if (!p || !p.vivo || p.nombre === this.pokemonJugador.nombre) return;

    this.guardarEstadoActualEnEquipo('jugador');

    const entrada = this.equipoJugador.find(e => e.nombre === p.nombre);
    this.pokemonJugador = {
      ...p,
      imagen: this.obtenerGif(p),
      hp: entrada?.hp ?? 100,
      colorHp: entrada?.colorHp ?? 'verde',
      vivo: entrada?.vivo ?? true
    };

    this.actualizarColorHp(this.pokemonJugador);

    this.mensaje = `¡Has cambiado a ${p.nombre}!`;
    this.cargarMovimientos(p.nombre, true);
    this.mostrandoCambio = false;
    this.turnoJugador = false;

    setTimeout(() => {
      this.mensaje = 'Turno del invitado';
      this.turnoInvitado();  // Llama al turno del invitado
    }, 800);
  }

  escapar() {
    this.mostrarDialogo('¡Has escapado del combate!');
    setTimeout(() => this.router.navigate(['/premiacion']), 2500);
  }


  turnoInvitado() {
    this.mensaje = `Turno del rival...`;
    const daño = Math.floor(Math.random() * 20) + 5;
    this.pokemonJugador.hp = Math.max(0, (this.pokemonJugador.hp ?? 0) - daño);
    this.actualizarColorHp(this.pokemonJugador);

    // Efectos visuales para el jugador
    this.shakingJugador = true;
    this.flashJugador = true;
    setTimeout(() => {
      this.shakingJugador = false;
    }, 500);
    setTimeout(() => {
      this.flashJugador = false;
    }, 200);

    this.guardarEstadoActualEnEquipo('jugador');

    setTimeout(() => {
      this.mensaje = `${this.pokemonInvitado.nombre} atacó causando ${daño} de daño!`;
      if (this.pokemonJugador.hp! <= 0) {
        this.derrotarPokemon(this.pokemonJugador, true);
        return;
      }
      this.turnoJugador = true;
      this.mostrandoMenu = true;
    }, 1500);
  }

  mostrarDialogo(mensaje: string) {
    this.mensajeDialogo = mensaje;
    this.dialogMensajeVisible = true;
  }

  volverAlMenu() {
    this.mostrandoAtaques = false;
    this.mostrandoCambio = false;
    this.mostrandoMenu = true;
  }

  private obtenerGif(pokemon: Pokemon): string {
    // Asume que usas el ID de la PokéAPI para GIFs animados (showdown sprites)
    return pokemon.imagen || `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${pokemon.id}.gif`;
  }
}
