import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminReviewCardComponent } from './admin-review-card.component';

describe('AdminReviewCardComponent', () => {
  let component: AdminReviewCardComponent;
  let fixture: ComponentFixture<AdminReviewCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminReviewCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminReviewCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
