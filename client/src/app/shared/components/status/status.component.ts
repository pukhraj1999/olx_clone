import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styles: [],
})
export class StatusComponent {
  @Input() iconClass: String = '';
  @Input() label: String = '';
  @Input() count: String = '';
}
