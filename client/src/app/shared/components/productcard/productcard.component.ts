import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-productcard',
  templateUrl: './productcard.component.html',
  styles: [],
})
export class ProductcardComponent {
  @Input() img: String = '';
  @Input() name: String = '';
  @Input() brand: String = '';
  @Input() code: String = '';
  @Input() content: String = '';
  @Input() seeLink: String = '';
  @Input() editLink: String = '';
  @Input() isAdmin: Boolean = false;

  //output for buttons
  @Output() deleteProductEvent: EventEmitter<any> = new EventEmitter<any>();

  onDelete() {
    this.deleteProductEvent.emit();
  }
}
