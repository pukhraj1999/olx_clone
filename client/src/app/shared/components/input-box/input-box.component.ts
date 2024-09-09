import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-input-box',
  templateUrl: './input-box.component.html',
  styles: [''],
})
export class InputBoxComponent {
  @Input() label: String = '';
  @Input() placeholder: String = '';
  @Input() type: String = 'text';
  @Input() value: String = '';
  @Input() errorMsg: String = '';
  @Input() isError: Boolean = false;
}
