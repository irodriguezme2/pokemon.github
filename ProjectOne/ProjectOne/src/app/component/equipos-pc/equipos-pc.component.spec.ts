import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EquiposPcComponent } from './equipos-pc.component';

describe('EquiposPcComponent', () => {
  let component: EquiposPcComponent;
  let fixture: ComponentFixture<EquiposPcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EquiposPcComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EquiposPcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
