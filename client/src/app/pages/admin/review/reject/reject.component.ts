import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ReviewDetail } from 'src/app/core/model/Review';
import { UserResponse } from 'src/app/core/model/User';
import { ReviewService } from 'src/app/core/service/review.service';

@Component({
  selector: 'app-reject',
  templateUrl: './reject.component.html',
  styles: [],
})
export class RejectComponent {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  reviews: ReviewDetail[] = [];

  constructor(private router: Router, private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.reviewService.getRejectedReviews().subscribe({
      next: (res) => {
        res.map((item) => {
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

  onAccept(id: Number) {
    this.reviewService.acceptReview(id).subscribe({
      next: (res) => {
        this.showMessage('Successfully Accepted Review!!', false);
        setTimeout(() => {
          this.router.navigate(['/admin/review/accept']);
        }, 1000);
      },
      error: (err) => {
        console.log(err);
        this.showMessage(err.error.error, true);
      },
    });
  }

  //deleting review
  onDelete(id: Number): void {
    this.reviewService.deleteReview(id).subscribe({
      next: (res) => {
        this.showMessage('Successfully Deleted Review!!', false);
        setTimeout(() => {
          this.router.navigate(['/admin/review/pending']);
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
}
