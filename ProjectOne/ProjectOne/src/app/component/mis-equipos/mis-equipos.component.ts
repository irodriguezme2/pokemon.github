import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EquiposService, Equipo } from "../../service/equipos.service";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { Dialog } from 'primeng/dialog';

interface Pokemon {
  nombre: string;
  imagen: string;
  seleccionado: boolean;
  tipo?: string[];
  movimientos?: string[];
}

@Component({
  selector: 'app-mis-equipos',
  standalone: true,
  templateUrl: './mis-equipos.component.html',
  styleUrls: ['./mis-equipos.component.css'],
  imports: [CommonModule, FormsModule, ButtonModule, Dialog]
})
export class MisEquiposComponent implements OnInit {
  equiposJugador: Equipo[] = [];

  // 💬 Diálogo de mensajes personalizados
  dialogMensajeVisible: boolean = false;
  mensajeDialogo: string = '';
  equipoAEliminar: Equipo | null = null; // 👈 guardará el equipo a eliminar

  constructor(
    private equiposService: EquiposService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.equiposService.getEquipos().subscribe(equipos => {
      this.equiposJugador = equipos;
    });
  }

  // 🗑️ Mostrar diálogo de confirmación (en vez del alert nativo)
  eliminarEquipo(equipo: Equipo): void {
    this.equipoAEliminar = equipo;
    this.mensajeDialogo = `¿Seguro que deseas eliminar el equipo "${equipo.nombre}"?`;
    this.dialogMensajeVisible = true;
  }

  // ✅ Si el usuario confirma, se elimina de la base de datos
  confirmarEliminacion(): void {
    if (this.equipoAEliminar) {
      this.equiposService.eliminarEquipo(this.equipoAEliminar.id).subscribe(() => {
        // Lo quitamos del arreglo local
        this.equiposJugador = this.equiposJugador.filter(
          e => e.id !== this.equipoAEliminar!.id
        );

        // Cerramos el diálogo
        this.dialogMensajeVisible = false;

        // Limpiamos la variable
        this.equipoAEliminar = null;
      });
    }
  }

  // ❌ Si el usuario cancela
  cancelarEliminacion(): void {
    this.dialogMensajeVisible = false;
    this.equipoAEliminar = null;
  }

  cancelar(): void {
    this.router.navigate(['/eleccion']);
  }

  siguiente(): void {
    this.router.navigate(['/batalla']);
  }

  // 💬 Mostrar mensaje tipo popup (para otros usos)
  mostrarMensaje(texto: string): void {
    this.mensajeDialogo = texto;
    this.dialogMensajeVisible = true;
  }
}
