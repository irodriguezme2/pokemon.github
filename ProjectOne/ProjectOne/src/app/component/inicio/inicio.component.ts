import { Component } from '@angular/core';
import {Router, RouterModule} from '@angular/router';
import { MusicButtonComponent } from '../music-button/music-button.component';
import { MusicaComponent } from '../musica/musica.component';
import {AuthService} from '../../service/auth.service';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [RouterModule, MusicButtonComponent, MusicaComponent, FormsModule, CommonModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {
  loginData = {
    username: '',
    password: ''
  };
  isLoading = false;
  errorMessage = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}



  onSubmit() {
    if (!this.loginData.username || !this.loginData.password) {
      alert('Por favor, completa todos los campos');
      return;
    }

    if (this.isLoading) return;
    this.isLoading = true;
    this.errorMessage = '';
    const loginRequest = {
      nombreUsuario: this.loginData.username,
      contrasenia: this.loginData.password
    };

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        this.isLoading = false;
        console.log('Login exitoso:', response);
        this.router.navigate(['/principal']);

      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error en login:', error);
        if (error.status === 401) {
          this.errorMessage = 'Credenciales incorrectas';
        } else if (error.status === 0) {
          this.errorMessage = 'Error de conexión con el servidor';
        } else {
          this.errorMessage = error.error || 'Error al iniciar sesión';
        }
        console.log('Mensaje de error mostrado:', this.errorMessage);
      }
    });

  }

}
