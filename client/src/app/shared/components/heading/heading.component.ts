import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-heading',
  templateUrl: './heading.component.html',
  styles: [],
})
export class HeadingComponent {
  @Input() title: String = '';
  @Input() highlight: String = '';
  @Input() colorClass: String = 'primary';
}
