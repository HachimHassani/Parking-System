import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressiveCardsLoaderComponent } from './progressive-cards-loader.component';

describe('ProgressiveCardsLoaderComponent', () => {
  let component: ProgressiveCardsLoaderComponent;
  let fixture: ComponentFixture<ProgressiveCardsLoaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgressiveCardsLoaderComponent]
    });
    fixture = TestBed.createComponent(ProgressiveCardsLoaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
