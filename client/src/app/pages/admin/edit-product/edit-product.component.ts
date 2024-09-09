import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/core/model/Products';
import { UserResponse } from 'src/app/core/model/User';
import { ProductService } from 'src/app/core/service/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styles: [],
})
export class EditProductComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  prevProduct: Product = {
    productId: 0,
    name: '',
    brand: '',
    code: '',
    img: '',
    content: '',
  };

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    let productId: String = this.route.snapshot.paramMap.get('id') as String;

    this.productService.getProduct(productId).subscribe({
      next: (res) => {
        this.prevProduct = res;
      },
      error: (err) => {
        console.log(err);
        this.showMessage(err.error.error, true);
      },
    });
  }

  productForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    img: new FormControl('', [Validators.required]),
    brand: new FormControl('', [Validators.required]),
    code: new FormControl('', [Validators.required]),
    content: new FormControl('', [Validators.required]),
  });

  editProduct() {
    const { name, img, code, brand, content } = this.productForm.value;
    let productId: Number = parseInt(
      this.route.snapshot.paramMap.get('id') as string
    );

    this.productService
      .updateProduct({ name, img, code, brand, content }, productId)
      .subscribe({
        next: (res) => {
          this.showMessage('Successfully updated!!', false);
          setTimeout(() => {
            this.router.navigate(['/admin/products']);
          }, 1000);
        },
        error: (err) => {
          console.log(err);
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
