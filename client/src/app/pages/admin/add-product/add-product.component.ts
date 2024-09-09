import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/core/model/User';
import { ProductService } from 'src/app/core/service/product.service';
@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styles: [],
})
export class AddProductComponent {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  constructor(private productService: ProductService, private router: Router) {}

  productForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    img: new FormControl('', [Validators.required]),
    brand: new FormControl('', [Validators.required]),
    code: new FormControl('', [Validators.required]),
    content: new FormControl('', [Validators.required]),
  });

  addProduct() {
    const { name, img, brand, code, content } = this.productForm.value;

    this.productService
      .createProduct({ name, img, brand, code, content })
      .subscribe({
        next: (res) => {
          this.showMessage('Successfully Added!!', false);
          setTimeout(() => {
            this.router.navigate(['/admin/products']);
          }, 1000);
        },
        error: (err) => {
          this.showMessage(err.error.error, true);
        },
      });
  }

  //show error msg
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
    return this.productForm.get('name');
  }
  get img() {
    return this.productForm.get('img');
  }
  get brand() {
    return this.productForm.get('brand');
  }
  get code() {
    return this.productForm.get('code');
  }
  get content() {
    return this.productForm.get('content');
  }
}
