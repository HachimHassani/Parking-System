import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationCardComponent } from './reservation-card.component';

describe('ReservationCardComponent', () => {
  let component: ReservationCardComponent;
  let fixture: ComponentFixture<ReservationCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReservationCardComponent]
    });
    fixture = TestBed.createComponent(ReservationCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
