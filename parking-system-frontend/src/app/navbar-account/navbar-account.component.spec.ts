import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarAccountComponent } from './navbar-account.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('NavbarAccountComponent', () => {
  let component: NavbarAccountComponent;
  let fixture: ComponentFixture<NavbarAccountComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavbarAccountComponent],
      imports: [RouterTestingModule],
    });
    fixture = TestBed.createComponent(NavbarAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
