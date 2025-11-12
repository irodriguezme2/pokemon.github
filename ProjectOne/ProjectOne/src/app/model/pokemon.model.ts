import { Movimiento } from './movimiento.model';
import { Ability } from './ability.model';
import { Estadistica } from './estadistica.model';

export class Pokemon {
  id!: number;
  nombre!: string;
  tipo!: string[];
  imagen!: string;
  movimientos!: Movimiento[];
  habilidades!: Ability[];
  estadisticas!: Estadistica[];

  constructor(data?: Partial<Pokemon>) {
    Object.assign(this, {
      movimientos: [],
      habilidades: [],
      estadisticas: [],
      ...data
    });
  }
}


export enum Estado {
  NORMAL = 'NORMAL',
  BURN = 'BURN',
  POISON = 'POISON',
  BADLY_POISONED = 'BADLY_POISONED',
  PARALYSIS = 'PARALYSIS',
  SLEEP = 'SLEEP',
  FREEZE = 'FREEZE',
  CONFUSION = 'CONFUSION',
  FLINCH = 'FLINCH',
  CURSED = 'CURSED',
  BOUND = 'BOUND',
  INFATUATION = 'INFATUATION'
}
