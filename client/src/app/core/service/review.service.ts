import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ReviewResponse } from '../model/Review';

@Injectable({
  providedIn: 'root',
})
export class ReviewService {
  baseUrl: String = 'http://localhost:9000/api/product/review';

  constructor(private http: HttpClient) {}

  sendRequest(data: Object) {
    return this.http.post<ReviewResponse>(this.baseUrl + '/send', data);
  }

  getAllReviews() {
    return this.http.get<ReviewResponse[]>(this.baseUrl + '/all');
  }

  getAcceptedReviews() {
    return this.http.get<ReviewResponse[]>(this.baseUrl + '/all/accepted');
  }

  getRejectedReviews() {
    return this.http.get<ReviewResponse[]>(this.baseUrl + '/all/rejected');
  }

  getPendingReviews() {
    return this.http.get<ReviewResponse[]>(this.baseUrl + '/all/pending');
  }

  acceptReview(id: Number) {
    return this.http.put(this.baseUrl + '/accept/' + id, {});
  }

  rejectReview(id: Number) {
    return this.http.put(this.baseUrl + '/reject/' + id, {});
  }

  deleteReview(id: Number) {
    return this.http.delete(this.baseUrl + '/delete/' + id);
  }
}
