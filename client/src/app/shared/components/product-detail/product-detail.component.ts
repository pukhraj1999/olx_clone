import { Component, Input } from '@angular/core';
import { ReviewDetail } from 'src/app/core/model/Review';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styles: [''],
})
export class ProductDetailComponent {
  @Input() img: String = '';
  @Input() name: String = '';
  @Input() brand: String = '';
  @Input() code: String = '';
  @Input() content: String = '';
  @Input() reviews: ReviewDetail[] = [];
}
