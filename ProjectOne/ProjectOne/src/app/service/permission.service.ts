import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class PermissionService {

  private endpointPermissions = {
    '/user/crear': ['ADMIN'],
    '/user/createjson': ['ADMIN'],
    '/user/checklogin': ['ADMIN'],
    '/user/verificar': ['ADMIN'],
    '/user/getall': ['ADMIN'],
    '/user/deletebyid': ['ADMIN'],
    '/user/login': ['ADMIN'],


    '/equipo/crear': ['ADMIN', 'USER'],
    '/equipo/crearswagger': ['ADMIN', 'USER'],
    '/equipo/deletebyname': ['ADMIN', 'USER'],


    '/batalla/turno': ['ADMIN', 'USER'],
    '/batalla/reiniciar': ['ADMIN', 'USER'],
    '/batalla/iniciar': ['ADMIN', 'USER'],
    '/batalla/estado': ['ADMIN', 'USER'],
    '/batalla/activa': ['ADMIN', 'USER'],

    '/pokemon/crear': ['ADMIN', 'USER'],
    '/pokemon/getall': ['ADMIN', 'USER'],
  };


  constructor(private authService: AuthService) {}

  canAccess(endpoint: string): boolean {
    const userRole = this.authService.getUserRole();
    if (!userRole) return false;

    const endpointKey = Object.keys(this.endpointPermissions).find(key =>
      endpoint.includes(key)
    );

    if (endpointKey) {
      const allowedRoles = this.endpointPermissions[endpointKey as keyof typeof this.endpointPermissions];
      return allowedRoles.includes(userRole);
    }

    return false;
  }

  getUserRole(): string | null {
    return this.authService.getUserRole();
  }
}
