import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import {UsuarioLogin, AuthResponse, Usuario} from '../model/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8081/auth';

  constructor(private http: HttpClient) {}

  login(user: UsuarioLogin): Observable<AuthResponse> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, user, { headers }).pipe(
      tap(response => {
        sessionStorage.setItem('token', response.token);
        sessionStorage.setItem('userRole', response.role);
        console.log('Token guardado:', response.token);
      })
    );
  }

  getCurrentUser(): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get('http://localhost:8081/auth/actual', { headers });
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  hasRole(role: string): boolean {
    const userRole = this.getUserRole();
    return userRole === role;
  }

  hasAnyRole(roles: string[]): boolean {
    const userRole = this.getUserRole();
    return roles.includes(userRole || '');
  }

  register(user: UsuarioLogin): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user, {
      responseType: 'text'
    });
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userRole');
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    const isLogged = !!token;
    console.log('¿Está logueado?:', isLogged);
    return isLogged;
  }

  getUserInfo() {
    return {
      token: this.getToken(),
      role: this.getUserRole(),
      isLoggedIn: this.isLoggedIn()
    };
  }


  isTokenValid(): boolean {
    const token = this.getToken();
    if (!token) return false;
    return token.length > 10;
  }

}
