import { Product } from './Products';
import { User } from './User';

export interface Review {
  reviewId: Number;
  productId: Number;
  userId: Number;
  status: String;
  rating: String;
  content: String;
}

export interface ReviewResponse {
  review: Review;
  user: User;
  product: Product;
}

export interface ReviewDetail {
  reviewId: Number;
  userName: String;
  productName: String;
  status: String;
  rating: Number;
  content: String;
}
