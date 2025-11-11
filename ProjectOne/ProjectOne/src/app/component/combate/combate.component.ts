import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquiposService } from '../../service/equipos.service';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import {Button} from 'primeng/button';
import {Dialog} from 'primeng/dialog';

interface Movimiento {
  nombre: string;
  tipo?: string;
  poder?: number;
}

interface Pokemon {
  id?: number;
  nombre: string;
  imagen: string;
  tipo?: string[];
  movimientos?: Movimiento[];
  hp: number;
  url?: string;
  colorHp?: string;
  vivo?: boolean;
}

@Component({
  selector: 'app-combate',
  standalone: true,
  imports: [CommonModule, HttpClientModule, Button, Dialog],
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
      this.mostrarDialogo('⚠️ Debes elegir ambos equipos primero.');
      setTimeout(() => this.router.navigate(['/eleccion']), 2000);
      return;
    }

    this.iniciarCombate();
  }

  /** 🔹 Obtener sprite animado tipo GIF */
  obtenerGif(poke: any): string {
    const id = poke.id || poke.url?.split('/').filter(Boolean).pop();
    return id
      ? `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif`
      : poke.imagen || '';
  }

  iniciarCombate() {
    // Asignar GIFs, HP inicial y estado "vivo"
    this.equipoJugador = this.equipoJugador.map((p) => ({
      ...p,
      imagen: this.obtenerGif(p),
      hp: 100,
      colorHp: 'verde',
      vivo: true,
    }));

    this.equipoInvitado = this.equipoInvitado.map((p) => ({
      ...p,
      imagen: this.obtenerGif(p),
      hp: 100,
      colorHp: 'verde',
      vivo: true,
    }));

    // Seleccionar los primeros
    this.pokemonJugador = { ...this.equipoJugador[0] };
    this.pokemonInvitado = { ...this.equipoInvitado[0] };

    this.turnoJugador = true;
    this.mensaje = '¡Es tu turno!';
    this.mostrandoMenu = true;

    // Cargar movimientos
    this.cargarMovimientos(this.pokemonJugador.nombre, true);
    this.cargarMovimientos(this.pokemonInvitado.nombre, false);
  }

  actualizarColorHp(pokemon: Pokemon) {
    if (pokemon.hp > 60) pokemon.colorHp = 'verde';
    else if (pokemon.hp > 30) pokemon.colorHp = 'amarillo';
    else pokemon.colorHp = 'rojo';
  }


  async cargarMovimientos(nombre: string, esJugador: boolean) {
    if (!nombre) return;
    const nombreFormateado = nombre.toLowerCase().replace(/\s+/g, '-');

    try {
      const data: any = await this.http
        .get(`https://pokeapi.co/api/v2/pokemon/${nombreFormateado}`)
        .toPromise();

      const primeros4 = data.moves.slice(0, 4);

      const detalles = await Promise.all(
        primeros4.map((m: any) => this.http.get(m.move.url).toPromise())
      );

      const movs = detalles.map((d: any) => {
        const nombreEsp =
          d.names.find((n: any) => n.language.name === 'es')?.name ||
          d.name.replace(/-/g, ' ');

        return {
          nombre: nombreEsp,
          tipo: d.type.name,
          poder: d.power || '—',
        };
      });

      if (esJugador) this.movimientos = movs;
      else this.movimientosInvitado = movs;
    } catch (err) {
      console.error('❌ Error al obtener movimientos:', err);
      this.mensaje =
        '⚠️ No se pudieron cargar los movimientos desde la PokéAPI.';
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
    this.pokemonInvitado.hp = Math.max(0, this.pokemonInvitado.hp - daño);
    this.actualizarColorHp(this.pokemonInvitado);

    this.mensaje = `${this.pokemonJugador.nombre} usó ${movimiento.nombre}!`;
    this.mostrandoAtaques = false;
    this.turnoJugador = false;

    setTimeout(() => {
      if (this.pokemonInvitado.hp <= 0) {
        this.derrotarPokemon(this.pokemonInvitado, false);
        return;
      }
      this.mensaje = 'Turno del invitado';
      this.mostrandoMenuInvitado = true;
    }, 1500);
  }

  derrotarPokemon(pokemon: Pokemon, esJugador: boolean) {
    // 🔹 Marcar como muerto
    pokemon.vivo = false;
    pokemon.hp = 0;

    // 🔹 Actualizar el equipo para reflejar el cambio
    if (esJugador) {
      this.equipoJugador = this.equipoJugador.map((p) =>
        p.nombre === pokemon.nombre ? { ...p, vivo: false, hp: 0 } : p
      );
    } else {
      this.equipoInvitado = this.equipoInvitado.map((p) =>
        p.nombre === pokemon.nombre ? { ...p, vivo: false, hp: 0 } : p
      );
    }

    this.mensaje = `💥 ${pokemon.nombre} fue derrotado!`;

    // 🔹 Buscar siguiente Pokémon vivo
    const equipo = esJugador ? this.equipoJugador : this.equipoInvitado;
    const siguiente = equipo.find((p) => p.vivo);

    setTimeout(() => {
      if (siguiente) {
        if (esJugador) {

          this.pokemonJugador = { ...siguiente };
          this.cargarMovimientos(siguiente.nombre, true);
          this.mensaje = `¡Has enviado a ${siguiente.nombre}!`;
          this.turnoJugador = false;
          this.mostrandoMenuInvitado = true;
        } else {

          this.pokemonInvitado = { ...siguiente };
          this.cargarMovimientos(siguiente.nombre, false);
          this.mensaje = `¡El invitado envió a ${siguiente.nombre}!`;
          this.turnoJugador = true;
          this.mostrandoMenu = true;
        }
      } else {
        // 🏁 No quedan Pokémon vivos
        this.mostrarDialogo(
          esJugador ? ' ¡Has perdido el combate!' : ' ¡Has ganado el combate!'
        );
        setTimeout(() => this.router.navigate(['/eleccion']), 2500);


        setTimeout(() => this.router.navigate(['/eleccion']), 2500);
      }
    }, 1500);
  }

  cambiarPokemon(p: Pokemon) {
    if (!p || !p.vivo || p.nombre === this.pokemonJugador.nombre) return;

    this.pokemonJugador = {
      ...p,
      imagen: this.obtenerGif(p),
    };
    this.actualizarColorHp(this.pokemonJugador);

    this.mensaje = `¡Has cambiado a ${p.nombre}!`;
    this.cargarMovimientos(p.nombre, true);
    this.mostrandoCambio = false;
    this.turnoJugador = false;

    setTimeout(() => {
      this.mensaje = 'Turno del invitado';
      this.mostrandoMenuInvitado = true;
    }, 800);
  }

  escapar() {
    this.mostrarDialogo('¡Has escapado del combate!');
    setTimeout(() => this.router.navigate(['/premiacion']), 2500);
  }

  // ======== Menús Invitado ========
  mostrarMenuInvitado() {
    this.mostrandoMenuInvitado = true;
  }

  usarMovimientoInvitado(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 15) + 5;
    this.pokemonJugador.hp = Math.max(0, this.pokemonJugador.hp - daño);
    this.actualizarColorHp(this.pokemonJugador);

    this.mensaje = `${this.pokemonInvitado.nombre} usó ${movimiento.nombre}!`;
    this.mostrandoAtaquesInvitado = false;
    this.turnoJugador = true;

    setTimeout(() => {
      if (this.pokemonJugador.hp <= 0) {
        this.derrotarPokemon(this.pokemonJugador, true);
        return;
      }
      this.mensaje = 'Turno del jugador';
      this.mostrandoMenu = true;
    }, 1500);
  }

  cambiarPokemonInvitado(p: Pokemon) {
    if (!p || !p.vivo || p.nombre === this.pokemonInvitado.nombre) return;

    this.pokemonInvitado = {
      ...p,
      imagen: this.obtenerGif(p),
    };
    this.actualizarColorHp(this.pokemonInvitado);

    this.mensaje = `¡El invitado cambió a ${p.nombre}!`;
    this.cargarMovimientos(p.nombre, false);
    this.mostrandoCambioInvitado = false;

    setTimeout(() => {
      this.mensaje = 'Turno del jugador';
      this.turnoJugador = true;
      this.mostrandoMenu = true;
    }, 800);
  }

  elegirAtacarInvitado() {
    this.mostrandoMenuInvitado = false;
    this.mostrandoAtaquesInvitado = true;
  }

  elegirCambiarInvitado() {
    this.mostrandoMenuInvitado = false;
    this.mostrandoCambioInvitado = true;
  }
  mostrarDialogo(mensaje: string) {
    this.mensajeDialogo = mensaje;
    this.dialogMensajeVisible = true;
  }

  cerrarDialogo() {
    this.dialogMensajeVisible = false;
    this.router.navigate(['/premiacion']);
  }

}
