import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss'],
})
export class MessageComponent implements OnInit {
  @Input() isError: Boolean = true;
  @Input() msg: String =
    'Lorem ipsum dolor, sit amet consectetur adipisicing elit.';
  @Input() show: Boolean = false;

  ngOnInit(): void {
    let modal = document.getElementById('modal');
    if (modal !== null) modal.style.height = window.outerHeight + 'px';
  }
}
