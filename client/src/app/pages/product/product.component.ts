import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/core/model/Products';
import { ReviewDetail } from 'src/app/core/model/Review';
import { UserResponse } from 'src/app/core/model/User';
import { ProductService } from 'src/app/core/service/product.service';
import { ReviewService } from 'src/app/core/service/review.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styles: [],
})
export class ProductComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  product: Product = {
    productId: 0,
    name: '',
    brand: '',
    code: '',
    content: '',
    img: '',
  };

  rating: number = 3;

  reviews: ReviewDetail[] = [];

  constructor(
    private productService: ProductService,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    //getting products
    let id = this.route.snapshot.paramMap.get('id') as String;
    this.productService.getProduct(id).subscribe({
      next: (res) => {
        this.product = res;
      },
      error: (err) => {
        this.showMessage(err.error.error, true);
      },
    });

    //getting reviews
    this.reviewService.getAllReviews().subscribe({
      next: (res) => {
        res.map((item) => {
          if (
            this.product.productId === item.product.productId &&
            item.review.status === 'ACCEPTED'
          )
            this.reviews.push({
              reviewId: item.review.reviewId,
              userName: item.user.firstname + ' ' + item.user.lastname,
              productName: item.product.name,
              status: item.review.status,
              rating: parseInt(item.review.rating as string),
              content: item.review.content,
            });
        });
      },
      error: (err) => {
        console.log(err);
        this.showMessage(err.error.error, true);
      },
    });
  }

  changeRating(rated: number) {
    this.rating = rated;
  }

  reviewForm = new FormGroup({
    content: new FormControl('', [Validators.required]),
  });

  sendReviewRequest(): void {
    if (localStorage.getItem('user') === null) {
      this.router.navigate(['/signup']);
      return;
    }

    let userId: Number = (
      JSON.parse(localStorage.getItem('user') as string) as UserResponse
    ).user.userId;
    let productId: Number = parseInt(
      this.route.snapshot.paramMap.get('id') as string
    );

    let rating: Number = this.rating;
    let { content } = this.reviewForm.value;

    this.reviewService
      .sendRequest({ userId, productId, content, rating })
      .subscribe({
        next: (res) => {
          this.showMessage('Review Request Send Successfully!!', false);
          setTimeout(() => {
            this.router.navigate(['/products']);
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
  get content() {
    return this.reviewForm.get('content');
  }
}
