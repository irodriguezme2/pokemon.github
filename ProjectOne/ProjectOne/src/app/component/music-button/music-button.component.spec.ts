import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MusicButtonComponent } from './music-button.component';

describe('MusicButtonComponent', () => {
  let component: MusicButtonComponent;
  let fixture: ComponentFixture<MusicButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MusicButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MusicButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
