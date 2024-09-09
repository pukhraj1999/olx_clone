import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styles: [],
})
export class ReviewComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  constructor() {}

  ngOnInit(): void {}
}
