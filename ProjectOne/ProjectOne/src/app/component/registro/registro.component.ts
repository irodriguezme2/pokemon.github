import { Component } from '@angular/core';
import { DatePickerModule } from 'primeng/datepicker';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MusicButtonComponent } from '../music-button/music-button.component';
import { MusicaComponent } from '../musica/musica.component';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [DatePickerModule, FormsModule, RouterModule, MusicButtonComponent, MusicaComponent],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  // 🔹 Variables del formulario
  date2: Date | null = null;
  selectedAvatar: string = ''; // Guarda el avatar elegido

  // 🔹 Método para enviar el formulario
  onSubmit() {
    if (!this.selectedAvatar) {
      alert('Por favor selecciona un avatar antes de registrarte.');
      return;
    }

    // Aquí puedes continuar con el registro, incluyendo el avatar seleccionado
    console.log('Avatar elegido:', this.selectedAvatar);
  }
}
