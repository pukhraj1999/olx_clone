import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserResponse } from '../model/User';

export class SecurityTokenInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (localStorage.getItem('user') == null) return next.handle(req);
    let token = (
      JSON.parse(localStorage.getItem('user') as string) as UserResponse
    ).token;
    let modifiedReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ` + token,
      },
    });
    return next.handle(modifiedReq);
  }
}
