import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CombatePCComponent } from './combate-pc.component';

describe('CombatePCComponent', () => {
  let component: CombatePCComponent;
  let fixture: ComponentFixture<CombatePCComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CombatePCComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CombatePCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
