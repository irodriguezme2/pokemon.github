import { Component } from '@angular/core';
import { DatePickerModule } from 'primeng/datepicker';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [DatePickerModule, FormsModule, RouterModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  date2: Date | null = null;
}
