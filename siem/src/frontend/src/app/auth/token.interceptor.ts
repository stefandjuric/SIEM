import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {LoggedUtils} from "../utils/logged.utils";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({


      setHeaders: {
        'x-auth-token': LoggedUtils.getToken()
      }

      //headers: request.headers.set('x-auth-token', LoggedUtils.getToken())
      //headers: request.headers.set(LoggedUtils.getToken(), LoggedUtils.getToken())
    });
    return next.handle(request);
  }
}
