import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EquiposService } from '../../service/equipos.service';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Button } from 'primeng/button';
import { Dialog } from 'primeng/dialog';

interface Movimiento {
  nombre: string;
  tipo?: string;
  poder?: number | string;
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

  mostrandoMenu = false;
  mostrandoAtaques = false;
  mostrandoCambio = false;
  movimientos: Movimiento[] = [];

  mostrandoMenuInvitado = false;
  mostrandoAtaquesInvitado = false;
  mostrandoCambioInvitado = false;
  movimientosInvitado: Movimiento[] = [];


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
      this.mostrarDialogo('⚠️ Debes elegir ambos equipos primero.');
      setTimeout(() => this.router.navigate(['/eleccion']), 2000);
      return;
    }

    this.iniciarCombate();
  }

  obtenerGif(poke: any): string {
    const id = poke.id || poke.url?.split('/').filter(Boolean).pop();
    return id
      ? `https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/${id}.gif`
      : poke.imagen || '';
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


    this.pokemonJugador = { ...this.equipoJugador.find(p => p.vivo) || this.equipoJugador[0] };
    this.pokemonInvitado = { ...this.equipoInvitado.find(p => p.vivo) || this.equipoInvitado[0] };

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
          ? { ...p, hp: this.pokemonJugador.hp, colorHp: this.pokemonJugador.colorHp, vivo: this.pokemonJugador.hp! > 0 }
          : p
      );
    } else if (team === 'invitado' && this.pokemonInvitado) {
      this.equipoInvitado = this.equipoInvitado.map(p =>
        p.nombre === this.pokemonInvitado.nombre
          ? { ...p, hp: this.pokemonInvitado.hp, colorHp: this.pokemonInvitado.colorHp, vivo: this.pokemonInvitado.hp! > 0 }
          : p
      );
    }
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
          d.names?.find((n: any) => n.language.name === 'es')?.name ||
          d.name.replace(/-/g, ' ');

        return {
          nombre: nombreEsp,
          tipo: d.type?.name,
          poder: d.power ?? '—',
        } as Movimiento;
      });

      if (esJugador) this.movimientos = movs;
      else this.movimientosInvitado = movs;
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

    // ✨ efecto visual: shake + flash
    this.shakingInvitado = true;
    this.flashInvitado = true;
    setTimeout(() => {
      this.shakingInvitado = false;
    }, 500); // duración del shake
    setTimeout(() => {
      this.flashInvitado = false;
    }, 200); // flash más corto

    // guardar estado persistente en el equipo
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
      this.mostrandoMenuInvitado = true;
    }, 1500);
  }

  derrotarPokemon(pokemon: Pokemon, esJugador: boolean) {
    pokemon.vivo = false;
    pokemon.hp = 0;

    // actualizar el equipo correspondiente para persistir la muerte
    if (esJugador) {
      this.equipoJugador = this.equipoJugador.map(p => p.nombre === pokemon.nombre ? {...p, vivo: false, hp: 0} : p);
    } else {
      this.equipoInvitado = this.equipoInvitado.map(p => p.nombre === pokemon.nombre ? {...p, vivo: false, hp: 0} : p);
    }

    this.mensaje = `💥 ${pokemon.nombre} fue derrotado!`;

    // buscar siguiente vivo
    const equipo = esJugador ? this.equipoJugador : this.equipoInvitado;
    const siguiente = equipo.find(p => p.vivo);

    setTimeout(() => {
      if (siguiente) {
        if (esJugador) {
          this.pokemonJugador = {...siguiente};
          this.cargarMovimientos(siguiente.nombre, true);
          this.mensaje = `¡Has enviado a ${siguiente.nombre}!`;
          this.turnoJugador = false;
          this.mostrandoMenuInvitado = true;
        } else {
          this.pokemonInvitado = {...siguiente};
          this.cargarMovimientos(siguiente.nombre, false);
          this.mensaje = `¡El invitado envió a ${siguiente.nombre}!`;
          this.turnoJugador = true;
          this.mostrandoMenu = true;
        }
      } else {
        const ganador = esJugador ? 'invitado' : 'jugador';
        const equipoGanador = ganador === 'jugador' ? this.equipoJugador : this.equipoInvitado;

        // ✅ Corregido: asegurar que siempre haya un Pokémon ganador con imagen
        let pokemonGanador = equipoGanador.find(p => p.vivo);
        if (!pokemonGanador && equipoGanador.length > 0) {
          pokemonGanador = equipoGanador[0];
        }
        if (pokemonGanador) {
          pokemonGanador.imagen = this.obtenerGif(pokemonGanador);
        }

        this.equiposService.establecerResultadoCombate({
          ganador,
          pokemonGanador
        });

        this.router.navigate(['/premiacion']);
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

    this.guardarEstadoActualEnEquipo('jugador');

    setTimeout(() => {
      this.mensaje = 'Turno del invitado';
      this.mostrandoMenuInvitado = true;
    }, 800);
  }

  escapar() {
    // Si escapa el jugador
    const ganador = 'invitado';
    const equipoGanador = this.equipoInvitado;
    let pokemonGanador = equipoGanador.find(p => p.vivo) || equipoGanador[0];

    if (pokemonGanador) {
      pokemonGanador.imagen = this.obtenerGif(pokemonGanador);
    }

    this.equiposService.establecerResultadoCombate({
      ganador,
      pokemonGanador
    });

    this.mostrarDialogo('¡Has escapado del combate!');
    setTimeout(() => this.router.navigate(['/premiacion']), 2500);
  }
  escaparInvitado() {
    const ganador = 'jugador';
    const equipoGanador = this.equipoJugador;
    let pokemonGanador = equipoGanador.find(p => p.vivo) || equipoGanador[0];

    if (pokemonGanador) {
      pokemonGanador.imagen = this.obtenerGif(pokemonGanador);
    }

    this.equiposService.establecerResultadoCombate({
      ganador,
      pokemonGanador
    });

    this.mostrarDialogo('¡El invitado ha escapado del combate!');
    setTimeout(() => this.router.navigate(['/premiacion']), 2500);
  }

  mostrarMenuInvitado() {
    this.mostrandoMenuInvitado = true;
  }

  usarMovimientoInvitado(movimiento: Movimiento) {
    const daño = Math.floor(Math.random() * 20) + 10;
    this.pokemonJugador.hp = Math.max(0, (this.pokemonJugador.hp ?? 0) - daño);
    this.actualizarColorHp(this.pokemonJugador);

    // ✨ efecto visual: shake + flash
    this.shakingJugador = true;
    this.flashJugador = true;
    setTimeout(() => { this.shakingJugador = false; }, 500);
    setTimeout(() => { this.flashJugador = false; }, 200);

    // guardar estado del jugador tras recibir daño
    this.guardarEstadoActualEnEquipo('jugador');

    this.mensaje = `${this.pokemonInvitado.nombre} usó ${movimiento.nombre}!`;
    this.mostrandoAtaquesInvitado = false;
    this.turnoJugador = true;

    setTimeout(() => {
      if ((this.pokemonJugador.hp ?? 0) <= 0) {
        this.derrotarPokemon(this.pokemonJugador, true);
        return;
      }
      this.mensaje = 'Turno del jugador';
      this.mostrandoMenu = true;
    }, 1500);
  }

  cambiarPokemonInvitado(p: Pokemon) {
    if (!p || !p.vivo || p.nombre === this.pokemonInvitado.nombre) return;

    // Guardar estado actual del invitado antes de cambiar
    this.guardarEstadoActualEnEquipo('invitado');

    const entrada = this.equipoInvitado.find(e => e.nombre === p.nombre);
    this.pokemonInvitado = {
      ...p,
      imagen: this.obtenerGif(p),
      hp: entrada?.hp ?? 100,
      colorHp: entrada?.colorHp ?? 'verde',
      vivo: entrada?.vivo ?? true
    };

    this.actualizarColorHp(this.pokemonInvitado);

    this.mensaje = `¡El invitado cambió a ${p.nombre}!`;
    this.cargarMovimientos(p.nombre, false);
    this.mostrandoCambioInvitado = false;

    // Guardar estado inmediatamente
    this.guardarEstadoActualEnEquipo('invitado');

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

  volverAlMenu() {
    this.mostrandoAtaques = false;
    this.mostrandoCambio = false;
    this.mostrandoMenu = true;
  }

  volverAlMenuInvitado() {
    this.mostrandoAtaquesInvitado = false;
    this.mostrandoCambioInvitado = false;
    this.mostrandoMenuInvitado = true;
  }
}
