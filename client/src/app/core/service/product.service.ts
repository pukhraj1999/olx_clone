import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Status } from '../model/Status';
import { Product } from '../model/Products';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  baseUrl: String = 'http://localhost:9000';

  constructor(private http: HttpClient) {}

  getStatus() {
    return this.http.get<Status>(this.baseUrl + '/status');
  }

  getProducts() {
    return this.http.get<Product[]>(this.baseUrl + '/api/product/public/all');
  }

  getProduct(id: String) {
    return this.http.get<Product>(this.baseUrl + '/api/product/public/' + id);
  }

  createProduct(data: Object) {
    return this.http.post<Product>(this.baseUrl + '/api/product/create', data);
  }

  updateProduct(data: Object, id: Number) {
    return this.http.put(this.baseUrl + '/api/product/update/' + id, data);
  }

  deleteProduct(id: Number) {
    return this.http.delete(this.baseUrl + '/api/product/delete/' + id);
  }

  searchProduct(data: Object) {
    return this.http.post<Product[]>(
      this.baseUrl + '/api/product/public/search',
      data
    );
  }
}
