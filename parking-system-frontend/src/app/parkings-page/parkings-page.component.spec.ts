import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ParkingsPageComponent } from './parkings-page.component';

describe('ParkingsPageComponent', () => {
  let component: ParkingsPageComponent;
  let fixture: ComponentFixture<ParkingsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParkingsPageComponent]
    });
    fixture = TestBed.createComponent(ParkingsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
