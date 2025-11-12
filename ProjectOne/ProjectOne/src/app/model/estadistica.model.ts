export class Estadistica {
  id!: number;           // Identificador único de la estadística
  nombre!: string;       // Nombre (ej: "ataque", "defensa", "velocidad")
  valor!: number;        // Valor numérico de la estadística
  esfuerzo?: number;     // (Opcional) Puntos de esfuerzo o nivel

  constructor(data?: Partial<Estadistica>) {
    Object.assign(this, data);
  }
}
