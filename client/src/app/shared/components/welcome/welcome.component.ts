import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styles: [''],
})
export class WelcomeComponent {
  @Input() bannerLink: string = ''; //act as prop of react
}
