import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Equipo {
  id: number;
  nombre: string;
  pokemones: any[];
}

@Injectable({
  providedIn: 'root'
})
export class EquiposService {
  private equiposSubject = new BehaviorSubject<Equipo[]>([]);
  private equipos: Equipo[] = [];

  // Equipos temporales (antes de guardarlos oficialmente)
  private equipoTemporalJugador: any[] = [];
  private equipoTemporalInvitado: any[] = [];

  constructor() {
    // ✅ Cargar equipos guardados al iniciar
    const guardados = localStorage.getItem('equipos');
    if (guardados) {
      this.equipos = JSON.parse(guardados);
      this.equiposSubject.next(this.equipos);
    }

    // ✅ Cargar equipos temporales si existen
    const jugadorTemp = localStorage.getItem('equipoTemporalJugador');
    const invitadoTemp = localStorage.getItem('equipoTemporalInvitado');
    if (jugadorTemp) this.equipoTemporalJugador = JSON.parse(jugadorTemp);
    if (invitadoTemp) this.equipoTemporalInvitado = JSON.parse(invitadoTemp);
  }

  // 🔹 Obtener todos los equipos guardados
  getEquipos(): Observable<Equipo[]> {
    return this.equiposSubject.asObservable();
  }

  // 🔹 Agregar un nuevo equipo
  agregarEquipo(equipo: Equipo): void {
    equipo.id = this.generarId();
    this.equipos = [...this.equipos, equipo];
    this.actualizarEquipos();
  }

  // 🔹 Eliminar un equipo por id
  eliminarEquipo(id: number): Observable<void> {
    this.equipos = this.equipos.filter(e => e.id !== id);
    this.actualizarEquipos();
    return new Observable<void>(observer => {
      observer.next();
      observer.complete();
    });
  }

  // =============================
  // 🧩 EQUIPO TEMPORAL DEL JUGADOR
  // =============================

  guardarEquipoTemporalJugador(equipo: any[]): void {
    this.equipoTemporalJugador = equipo;
    localStorage.setItem('equipoTemporalJugador', JSON.stringify(equipo));
  }

  obtenerEquipoTemporalJugador(): any[] {
    if (this.equipoTemporalJugador.length) return this.equipoTemporalJugador;

    const guardado = localStorage.getItem('equipoTemporalJugador');
    return guardado ? JSON.parse(guardado) : [];
  }

  // =============================
  // 🧩 EQUIPO TEMPORAL DEL INVITADO
  // =============================

  guardarEquipoTemporalInvitado(equipo: any[]): void {
    this.equipoTemporalInvitado = equipo;
    localStorage.setItem('equipoTemporalInvitado', JSON.stringify(equipo));
  }

  obtenerEquipoTemporalInvitado(): any[] {
    if (this.equipoTemporalInvitado.length) return this.equipoTemporalInvitado;

    const guardado = localStorage.getItem('equipoTemporalInvitado');
    return guardado ? JSON.parse(guardado) : [];
  }

  // =============================
  // 🧹 LIMPIAR EQUIPOS TEMPORALES
  // =============================

  limpiarEquiposTemporales(): void {
    this.equipoTemporalJugador = [];
    this.equipoTemporalInvitado = [];
    localStorage.removeItem('equipoTemporalJugador');
    localStorage.removeItem('equipoTemporalInvitado');
  }

  // =============================
  // 🔧 MÉTODOS AUXILIARES PRIVADOS
  // =============================

  private actualizarEquipos(): void {
    this.equiposSubject.next(this.equipos);
    localStorage.setItem('equipos', JSON.stringify(this.equipos));
  }

  private generarId(): number {
    return this.equipos.length > 0
      ? Math.max(...this.equipos.map(e => e.id)) + 1
      : 1;
  }
}
