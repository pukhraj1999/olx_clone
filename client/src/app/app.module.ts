import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutModule } from './layout/layout.module';
import { CoreModule } from './core/core.module';
import { PagesModule } from './pages/pages.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { SecurityTokenInterceptor } from './core/interceptors/security.token.interceptor';

@NgModule({
  declarations: [AppComponent],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SecurityTokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    CoreModule,
    PagesModule,
    HttpClientModule,
  ],
})
export class AppModule {}
