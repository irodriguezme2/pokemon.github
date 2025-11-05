import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EleccionInvitadoComponent } from './eleccion-invitado.component';

describe('EleccionInvitadoComponent', () => {
  let component: EleccionInvitadoComponent;
  let fixture: ComponentFixture<EleccionInvitadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EleccionInvitadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EleccionInvitadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
