import {Component, inject, OnInit} from '@angular/core';
import { DatePickerModule } from 'primeng/datepicker';
import { FormsModule } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import { MusicButtonComponent } from '../music-button/music-button.component';
import { MusicaComponent } from '../musica/musica.component';
import {AuthService} from '../../service/auth.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [DatePickerModule, FormsModule, RouterModule, MusicButtonComponent, MusicaComponent, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  registerData = {
    username: '',
    password: '',
    email: '',
    fechaNacimiento: null,
    avatar: '' // se asigna según el booleano
  };
  errorMessage: string = '';
  successMessage: string = '';
  isLoading: boolean = false;
  isAvatar1Selected = true;

  constructor(private authService: AuthService, private router: Router) {
  }

  selectAvatar(isAvatar1: boolean): void {
    this.isAvatar1Selected = isAvatar1;
    this.registerData.avatar = isAvatar1 ? 'avatar1' : 'avatar2';
  }

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    // 🔹 Validaciones básicas en el front
    if (!this.registerData.username || !this.registerData.password || !this.registerData.email || !this.registerData.fechaNacimiento) {
      this.errorMessage = 'Todos los campos son obligatorios';
      return;
    }

    if (this.registerData.username.includes(">") || this.registerData.username.includes("<") || this.registerData.username.includes("/") || this.registerData.username.includes("*")) {
      this.errorMessage = 'El nombre de usuario no puede contener caracteres especiales';
      return;
    }

    if (!this.registerData.email.match("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
      this.errorMessage = 'La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo especial.';
      return;
    }

    if (!this.registerData.avatar) {
      this.errorMessage = 'Debes seleccionar un avatar';
      return;
    }

    const birthDate = new Date(this.registerData.fechaNacimiento);
    const today = new Date();
    const age =
      today.getFullYear() -
      birthDate.getFullYear() -
      (today.getMonth() < birthDate.getMonth() ||
      (today.getMonth() === birthDate.getMonth() && today.getDate() < birthDate.getDate())
        ? 1
        : 0);

    if (age < 18) {
      this.errorMessage = 'Debes tener al menos 18 años para registrarte.';
      return;
    }


    this.isLoading = true;

    // 🔹 Objeto que se envía al backend
    const userToRegister = {
      nombreUsuario: this.registerData.username,
      contrasenia: this.registerData.password,
      correo: this.registerData.email,
      fechaNacimiento: this.registerData.fechaNacimiento,
      avatar: this.registerData.avatar
    };

    console.log('Registrando usuario:', userToRegister);

    this.authService.register(userToRegister).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = 'Usuario registrado exitosamente 🎉';
        console.log('Registro exitoso:', response);
        setTimeout(() => {
          this.router.navigate(['/inicio']);
        }, 100);
      },
      error: (error) => {
        this.isLoading = false;
        console.error('Error en registro:', error);
        if (error.status === 409) {
          this.errorMessage = 'El nombre de usuario o correo ya existe';
        } else if (error.status === 400) {
          this.errorMessage = 'Datos inválidos. Verifica la información';
        } else if (error.status === 401) {
          this.errorMessage = 'No autorizado. Intenta de nuevo';
        } else {
          this.errorMessage = 'Error al registrar el usuario ' + this.getErrorMessage(error);
        }
      }
    });
  }

  private getErrorMessage(error: any): string {
    if (typeof error === 'string') return error;
    if (error.error instanceof ProgressEvent) {
      return 'Error de conexión con el servidor';
    }
    return error.error || error.message || 'Error desconocido';
  }


}
