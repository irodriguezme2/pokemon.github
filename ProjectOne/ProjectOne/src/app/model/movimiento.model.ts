export class Movimiento {
  id!: number;              // Identificador único
  nombre!: string;          // Nombre del movimiento (Ej: "Lanzallamas")
  tipo!: string;            // Tipo elemental (Ej: "Fuego", "Agua")
  potencia!: number;        // Daño base del movimiento
  puntos_poder!: number;    // Power Points (PP)
  descripcion?: string;     // (Opcional) Descripción del movimiento

  constructor(data?: Partial<Movimiento>) {
    Object.assign(this, data);
  }
}
