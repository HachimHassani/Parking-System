import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarAccountComponent } from './navbar-account.component';

describe('NavbarAccountComponent', () => {
  let component: NavbarAccountComponent;
  let fixture: ComponentFixture<NavbarAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarAccountComponent]
    });
    fixture = TestBed.createComponent(NavbarAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
