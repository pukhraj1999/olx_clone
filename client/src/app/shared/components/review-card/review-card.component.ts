import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-review-card',
  templateUrl: './review-card.component.html',
  styles: [''],
})
export class ReviewCardComponent {
  @Input() userName: String = '';
  @Input() rating: Number = 0;
  @Input() content: String = '';
}
