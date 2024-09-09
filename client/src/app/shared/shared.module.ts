import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { StatusComponent } from './components/status/status.component';
import { HeadingComponent } from './components/heading/heading.component';
import { ProductcardComponent } from './components/productcard/productcard.component';
import { AppRoutingModule } from '../app-routing.module';
import { InputBoxComponent } from './components/input-box/input-box.component';
import { ReviewCardComponent } from './components/review-card/review-card.component';
import { ProductDetailComponent } from './components/product-detail/product-detail.component';
import { BigInputBoxComponent } from './components/big-input-box/big-input-box.component';
import { AdminReviewCardComponent } from './components/admin-review-card/admin-review-card.component';
import { MessageComponent } from './components/message/message.component';
import { StarComponent } from './components/star/star.component';

@NgModule({
  declarations: [
    WelcomeComponent,
    StatusComponent,
    HeadingComponent,
    ProductcardComponent,
    InputBoxComponent,
    ReviewCardComponent,
    ProductDetailComponent,
    BigInputBoxComponent,
    AdminReviewCardComponent,
    MessageComponent,
    StarComponent,
  ],
  imports: [CommonModule, AppRoutingModule],
  exports: [
    WelcomeComponent,
    StatusComponent,
    HeadingComponent,
    ProductcardComponent,
    InputBoxComponent,
    ReviewCardComponent,
    ProductDetailComponent,
    BigInputBoxComponent,
    AdminReviewCardComponent,
    MessageComponent,
    StarComponent,
  ],
})
export class SharedModule {}
