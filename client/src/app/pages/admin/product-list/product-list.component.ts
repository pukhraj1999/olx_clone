import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Product } from 'src/app/core/model/Products';
import { UserResponse } from 'src/app/core/model/User';
import { ProductService } from 'src/app/core/service/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styles: [],
})
export class ProductListComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.getProducts();
  }

  //getting products
  getProducts() {
    this.productService.getProducts().subscribe({
      next: (res) => {
        this.products = res;
      },
      error: (err) => {
        this.showMessage(err.error.error, true);
        console.log(err);
      },
    });
  }

  //searching
  productSearchForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    brand: new FormControl(''),
    code: new FormControl(''),
  });

  //searched products
  getProduct(): void {
    const { name, brand, code } = this.productSearchForm.value;
    this.productService.searchProduct({ name, brand, code }).subscribe({
      next: (res) => {
        this.products = res;
      },
      error: (err) => {
        console.log(err);
        this.showMessage(err.error.error, true);
      },
    });
  }

  //delete product
  deleteProduct(id: Number) {
    this.productService.deleteProduct(id).subscribe({
      next: (res) => {
        this.showMessage('Successfully deleted!!', false);
        this.getProducts();
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
  //getters
  get name() {
    return this.productSearchForm.get('name');
  }
  get brand() {
    return this.productSearchForm.get('brand');
  }
  get code() {
    return this.productSearchForm.get('code');
  }
}
