// batalla.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Batalla } from '../model/batalla.model';
import { Equipo } from '../model/equipo.model';

@Injectable({ providedIn: 'root' })
export class BatallaService {
  private apiUrl = 'http://localhost:8081/batalla';

  private equipoJugador!: Equipo;
  private equipoInvitado!: Equipo;

  constructor(private http: HttpClient) {}

  setEquipoJugador(equipo: Equipo): void {
    this.equipoJugador = equipo;
  }

  getEquipoJugador(): Equipo {
    return this.equipoJugador;
  }

  setEquipoInvitado(equipo: Equipo): void {
    this.equipoInvitado = equipo;
  }

  getEquipoInvitado(): Equipo {
    return this.equipoInvitado;
  }

  limpiarTodo(): void {
    this.equipoJugador = undefined!;
    this.equipoInvitado = undefined!;
  }

  equiposListos(): boolean {
    return !!this.equipoJugador?.id && !!this.equipoInvitado?.id;
  }

  iniciarBatallaPorIds(id1: number, id2: number): Observable<Batalla> {
    const params = new HttpParams()
      .set('idEquipo1', id1.toString())
      .set('idEquipo2', id2.toString());

    return this.http.post<Batalla>(`${this.apiUrl}/iniciar`, null, { params });
  }
}
