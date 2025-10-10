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

  // Cambia: ahora tienes dos equipos temporales
  private equipoTemporalJugador: any[] = [];
  private equipoTemporalInvitado: any[] = [];

  getEquipos(): Observable<Equipo[]> {
    return this.equiposSubject.asObservable();
  }

  agregarEquipo(equipo: Equipo) {
    this.equipos = [...this.equipos, equipo];
    this.equiposSubject.next(this.equipos);
  }

  // Métodos para el equipo TEMPORAL DEL JUGADOR
  guardarEquipoTemporalJugador(equipo: any[]) {
    this.equipoTemporalJugador = equipo;
  }

  obtenerEquipoTemporalJugador(): any[] {
    return this.equipoTemporalJugador;
  }

  // Métodos para el equipo TEMPORAL DEL INVITADO
  guardarEquipoTemporalInvitado(equipo: any[]) {
    this.equipoTemporalInvitado = equipo;
  }

  obtenerEquipoTemporalInvitado(): any[] {
    return this.equipoTemporalInvitado;
  }
}
