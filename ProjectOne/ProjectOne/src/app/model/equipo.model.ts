import { Pokemon } from './pokemon.model';

export class Equipo {
  id?: number;                  // Solo se asigna si el equipo fue guardado en el backend
  nombre: string;              // Nombre del equipo (ej: "Equipo Deivid", "CPU")
  propietario: string;         // Nombre del dueño (ej: "Deivid", "CPU")
  pokemones: Pokemon[];        // Lista de Pokémon que forman el equipo
  invitado: boolean;           // true si es equipo CPU/invitado, false si es del jugador

  constructor(data: Partial<Equipo>) {
    this.nombre = data.nombre ?? '';
    this.propietario = data.propietario ?? '';
    this.pokemones = data.pokemones ?? [];
    this.invitado = data.invitado ?? false;
    this.id = data.id;
  }
}
