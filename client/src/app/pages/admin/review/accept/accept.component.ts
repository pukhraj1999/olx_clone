import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReviewDetail } from 'src/app/core/model/Review';
import { ReviewService } from 'src/app/core/service/review.service';

@Component({
  selector: 'app-accept',
  templateUrl: './accept.component.html',
  styles: [],
})
export class AcceptComponent implements OnInit {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  reviews: ReviewDetail[] = [];

  constructor(private router: Router, private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.reviewService.getAcceptedReviews().subscribe({
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

  onReject(id: Number) {
    this.reviewService.rejectReview(id).subscribe({
      next: (res) => {
        this.showMessage('Successfully Rejected Review!!', false);
        setTimeout(() => {
          this.router.navigate(['/admin/review/reject']);
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
