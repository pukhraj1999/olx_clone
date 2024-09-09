import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-star',
  templateUrl: './star.component.html',
  styles: [],
})
export class StarComponent {
  @Input() glow: Boolean = true;
}
