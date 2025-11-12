
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  console.log('🛜 INTERCEPTOR: URL=', req.url, 'Token=', token ? 'SÍ' : 'NO');

  if (token) {
    const authReq = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    console.log('Token añadido a:', req.url);
    return next(authReq);
  }

  console.log('Sin token para:', req.url);
  return next(req);
};
