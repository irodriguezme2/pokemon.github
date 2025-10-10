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

  // 🔹 Aquí guardamos temporalmente un equipo sin persistirlo
  private equipoTemporal: any[] = [];

  getEquipos(): Observable<Equipo[]> {
    return this.equiposSubject.asObservable();
  }

  agregarEquipo(equipo: Equipo) {
    this.equipos = [...this.equipos, equipo];
    this.equiposSubject.next(this.equipos);
  }

  // 🔹 Métodos nuevos para guardar equipo temporalmente
  guardarEquipoTemporal(equipo: any[]) {
    this.equipoTemporal = equipo;
  }

  obtenerEquipoTemporal(): any[] {
    return this.equipoTemporal;
  }
}
