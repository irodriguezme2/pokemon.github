export interface Usuario {
  correo: string;
  nombreUsurio: string;
  contrasenia: string;
  fechaNacimiento: Date;
  esHombre: boolean;
}

export interface UsuarioDTO {
  correo: string;
  nombreUsurio: string;
  contrasenia: string;
  fechaNacimiento: Date;
  esHombre: boolean;
}

export interface UsuarioLogin {
  nombreUsuario: string;
  contrasenia?: string;
}

export interface AuthResponse {
  token: string;
  role: string;
}
