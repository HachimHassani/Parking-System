import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarFiltersComponent } from './navbar-filters.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('NavbarFiltersComponent', () => {
  let component: NavbarFiltersComponent;
  let fixture: ComponentFixture<NavbarFiltersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarFiltersComponent],
      imports: [RouterTestingModule]
    });
    fixture = TestBed.createComponent(NavbarFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('add order to filter', () => {
  component.addOrderToFilter("new");
  expect(component.filter.order).toEqual("new");
  });

  it('add city to filter', () => {
    component.addCityToFilter("Marrakech");
    expect(component.filter.city).toEqual("Marrakech");
  });
});
