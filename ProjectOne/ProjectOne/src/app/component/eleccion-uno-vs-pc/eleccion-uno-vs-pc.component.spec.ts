import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EleccionUnoVsPcComponent } from './eleccion-uno-vs-pc.component';

describe('EleccionUnoVsPcComponent', () => {
  let component: EleccionUnoVsPcComponent;
  let fixture: ComponentFixture<EleccionUnoVsPcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EleccionUnoVsPcComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EleccionUnoVsPcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
