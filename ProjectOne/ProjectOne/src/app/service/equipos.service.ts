import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Equipo} from '../model/equipo.model';

@Injectable({
  providedIn: 'root'
})
export class EquipoService {
  private equipoJugador!: Equipo;
  private equipoPC!: Equipo;
  private apiUrl = 'http://localhost:8081/equipo';

  constructor(private http: HttpClient) {}

  crearEquipo(nombre: string, id: number, pokemones: string[]): Observable<string> {
    let params = new HttpParams()
      .set('nombre', nombre)
      .set('id', id.toString());

    // Agrega cada pokémon al parámetro pokemones[]
    pokemones.forEach(p => {
      params = params.append('pokemones', p);
    });

    return this.http.post(`${this.apiUrl}/crear`, null, {
      params,
      responseType: 'text' // porque el backend devuelve texto
    });
  }
  setEquipoJugador(equipo: Equipo) {
    this.equipoJugador = equipo;
  }

  getEquipoJugador(): Equipo {
    return this.equipoJugador;
  }

  setEquipoPC(equipo: Equipo) {
    this.equipoPC = equipo;
  }

  getEquipoPC(): Equipo {
    return this.equipoPC;
  }
}

