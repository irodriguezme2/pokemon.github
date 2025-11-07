import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Usuario} from '../model/usuario.model';
import {Observable} from 'rxjs';

const baseUrl = 'http://localhost:8081/usuario';
@Injectable({
  providedIn: 'root',
})

export class UsuarioService {
  constructor(private http: HttpClient) {}

  getAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(baseUrl);
  }

  get(id: any): Observable<Usuario> {
    return this.http.get<Usuario>(`${baseUrl}/${id}`);
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  update(id: any, data: any): Observable<any> {
    return this.http.put(`${baseUrl}/${id}`, data);
  }

  delete(id: any): Observable<any> {
    return this.http.delete(`${baseUrl}/${id}`);
  }

  deleteAll(): Observable<any> {
    return this.http.delete(baseUrl);
  }

  findByTitle(title: any): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${baseUrl}?title=${title}`);
  }
}
