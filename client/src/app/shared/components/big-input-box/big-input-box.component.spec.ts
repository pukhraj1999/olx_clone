import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BigInputBoxComponent } from './big-input-box.component';

describe('BigInputBoxComponent', () => {
  let component: BigInputBoxComponent;
  let fixture: ComponentFixture<BigInputBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BigInputBoxComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BigInputBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
