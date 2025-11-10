import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Usuario, UsuarioDTO} from '../model/usuario.model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class UsuarioService {
  constructor(private http: HttpClient) {}
  private baseUrl  = 'http://localhost:8081/usuario';

  crearUsuario(usuario: Usuario): Observable<any> {
    const params = new HttpParams()
      .set('correo', usuario.correo)
      .set('nombre', usuario.nombre)
      .set('contrasenia', usuario.contrasenia)
      .set('fechaNacimiento', usuario.fechaNacimiento.toISOString().split('T')[0])
      .set('esHombre', usuario.esHombre.toString());

    return this.http.post(`${this.baseUrl}/crear`, null, {
      params: params,
      responseType: 'text'
    });
  }

  crearUsuarioJSON(usuario: UsuarioDTO): Observable<any> {
    return this.http.post(`${this.baseUrl}/createjson`, usuario, {
      responseType: 'text'
    });
  }

  login(correo: string, contrasenia: string): Observable<any> {
    const params = new HttpParams()
      .set('username', correo)
      .set('password', contrasenia);

    return this.http.post(`${this.baseUrl}/checklogin`, null, {
      params: params,
      responseType: 'text'
    });
  }

  verificarCuenta(token: number): Observable<any> {
    const params = new HttpParams().set('token', token.toString());
    return this.http.get(`${this.baseUrl}/verificar`, {
      params: params,
      responseType: 'text'
    });
  }

  obtenerTodosUsuarios(): Observable<UsuarioDTO[]> {
    return this.http.get<UsuarioDTO[]>(`${this.baseUrl}/getall`);
  }

  eliminarUsuario(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/deletebyid/${id}`, {
      responseType: 'text'
    });
  }
}
