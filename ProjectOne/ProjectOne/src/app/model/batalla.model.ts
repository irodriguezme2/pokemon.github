import { Equipo } from './equipo.model';

export class Batalla {
  id?: number;                 // ID de la batalla (si se guarda en backend)
  equipo1!: Equipo;            // Equipo del jugador
  equipo2!: Equipo;            // Equipo CPU/invitado
  turnoActual?: number;        // Número de turno actual
  ganador?: string;            // Nombre del equipo ganador (si ya terminó)

  constructor(data?: Partial<Batalla>) {
    Object.assign(this, data);
  }
}
