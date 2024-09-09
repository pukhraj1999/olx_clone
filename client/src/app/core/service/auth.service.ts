import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { UserResponse } from '../model/User';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  baseUrl: String = 'http://localhost:9000/api/auth';

  constructor(private http: HttpClient) {}

  login(data: Object) {
    return this.http.post<UserResponse>(this.baseUrl + '/login', data);
  }

  signup(data: Object) {
    return this.http.post<UserResponse>(this.baseUrl + '/signup', data);
  }
}
