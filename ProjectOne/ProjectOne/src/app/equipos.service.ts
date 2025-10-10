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

  getEquipos(): Observable<Equipo[]> {
    return this.equiposSubject.asObservable();
  }

  agregarEquipo(equipo: Equipo) {
    this.equipos = [...this.equipos, equipo];
    this.equiposSubject.next(this.equipos);
  }
}
