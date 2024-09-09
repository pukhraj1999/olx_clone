import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-big-input-box',
  templateUrl: './big-input-box.component.html',
  styles: [],
})
export class BigInputBoxComponent {
  @Input() label: String = '';
  @Input() id: String = '';
  @Input() placeholder: String = '';
  @Input() value: String = '';
  @Input() errorMsg: String = '';
  @Input() isError: Boolean = false;
}
