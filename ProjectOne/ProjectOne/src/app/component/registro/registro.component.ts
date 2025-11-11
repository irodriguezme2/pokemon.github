import { Component } from '@angular/core';
import { DatePickerModule } from 'primeng/datepicker';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MessageService } from 'primeng/api';
import { MusicaComponent } from '../musica/musica.component';
import { UsuarioService } from '../../service/usuario.service';
import { Usuario } from '../../model/usuario.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, DatePickerModule, FormsModule, RouterModule, MusicaComponent],
  providers: [MessageService],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  date2: Date | null = null;
  usuario: Usuario = {
    correo: '',
    nombre: '',
    contrasenia: '',
    fechaNacimiento: new Date(),
    esHombre: true
  };

  selectedAvatar: string | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private messageService: MessageService
  ) {}

  registrarUsuario() {
    if (!this.validarFormulario()) return;

    if (this.date2) {
      this.usuario.fechaNacimiento = this.date2;
    }

    this.usuarioService.crearUsuario(this.usuario).subscribe({
      next: () => {
        this.messageService.add({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Usuario registrado correctamente'
        });
        this.limpiarFormulario();
      },
      error: (error) => {
        let mensajeError = 'Error al registrar usuario';

        if (error.error) {
          if (error.error.includes('Usuario ya existente')) {
            mensajeError = 'El nombre de usuario ya existe';
          } else if (error.error.includes('Correo incorrecto')) {
            mensajeError = 'El correo electrónico no es válido';
          } else if (error.error.includes('correo ya existente')) {
            mensajeError = 'El correo electrónico ya está registrado';
          }
        }

        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: mensajeError
        });
      }
    });
  }

  private validarFormulario(): boolean {
    if (!this.usuario.nombre || !this.usuario.correo || !this.usuario.contrasenia || !this.date2) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Advertencia',
        detail: 'Por favor, complete todos los campos'
      });
      return false;
    }

    if (!this.validarCorreo(this.usuario.correo)) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Advertencia',
        detail: 'Por favor, ingrese un correo electrónico válido'
      });
      return false;
    }

    if (this.usuario.contrasenia.length < 6) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Advertencia',
        detail: 'La contraseña debe tener al menos 6 caracteres'
      });
      return false;
    }

    return true;
  }

  private validarCorreo(correo: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(correo);
  }

  private limpiarFormulario() {
    this.usuario = {
      correo: '',
      nombre: '',
      contrasenia: '',
      fechaNacimiento: new Date(),
      esHombre: true
    };
    this.date2 = null;
  }
}
