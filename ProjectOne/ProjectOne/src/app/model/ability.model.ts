export class Ability {
  id!: number;            // Identificador único de la habilidad
  nombre!: string;        // Nombre de la habilidad (ej: "Blaze")
  descripcion!: string;   // Descripción o efecto de la habilidad
  esOculta?: boolean;     // (opcional) indica si es una habilidad oculta

  constructor(data?: Partial<Ability>) {
    Object.assign(this, data);
  }
}
