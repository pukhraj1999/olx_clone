import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/core/service/product.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: [],
})
export class HomeComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  reviews: Number = 0;
  products: Number = 0;
  users: Number = 0;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getStatus().subscribe({
      next: (res) => {
        this.users = res.users;
        this.products = res.products;
        this.reviews = res.reviews;
      },
      error: (err) => {
        this.showMessage(err.error.error, true);
      },
    });
  }

  showMessage(msg: String, error: Boolean): void {
    //showing message from backend
    this.isError = error;
    this.msg = msg;
    this.showMsg = true;
    //closing backend message
    setTimeout(
      () => {
        this.showMsg = false;
      },
      error ? 2000 : 1000
    );
  }
}
