export interface Usuario {
  correo: string;
  nombre: string;
  contrasenia: string;
  fechaNacimiento: Date;
  esHombre: boolean;
}

export interface UsuarioDTO {
  correo: string;
  nombre: string;
  contrasenia: string;
  fechaNacimiento: Date;
  esHombre: boolean;
  role?: string;
}
