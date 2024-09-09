import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-admin-review-card',
  templateUrl: './admin-review-card.component.html',
  styles: [],
})
export class AdminReviewCardComponent {
  @Input() userName: String = '';
  @Input() productName: String = '';
  @Input() rating: Number = 3;
  @Input() status: String = '';
  @Input() content: String = '';
  @Input() toAccepted: Boolean = false;
  @Input() toRejected: Boolean = false;

  //output for buttons
  @Output() acceptEventEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output() rejectEventEmitter: EventEmitter<any> = new EventEmitter<any>();
  @Output() deleteProductEvent: EventEmitter<any> = new EventEmitter<any>();

  onDelete() {
    this.deleteProductEvent.emit();
  }

  //creating buttons for calling them
  accept() {
    this.acceptEventEmitter.emit();
  }
  reject() {
    this.rejectEventEmitter.emit();
  }
}
