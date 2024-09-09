import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { ProductComponent } from './product/product.component';
import { ProductsComponent } from './products/products.component';
import { ReviewComponent } from './admin/review/review.component';
import { AddProductComponent } from './admin/add-product/add-product.component';
import { EditProductComponent } from './admin/edit-product/edit-product.component';
import { ProductListComponent } from './admin/product-list/product-list.component';
import { AcceptComponent } from './admin/review/accept/accept.component';
import { RejectComponent } from './admin/review/reject/reject.component';
import { PendingComponent } from './admin/review/pending/pending.component';
import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from '../app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
  declarations: [
    HomeComponent,
    SignupComponent,
    LoginComponent,
    ProductComponent,
    ProductsComponent,
    ReviewComponent,
    AddProductComponent,
    EditProductComponent,
    ProductListComponent,
    AcceptComponent,
    RejectComponent,
    PendingComponent,
    PageNotFoundComponent,
  ],
  imports: [CommonModule, SharedModule, AppRoutingModule, ReactiveFormsModule],
  exports: [
    HomeComponent,
    SignupComponent,
    LoginComponent,
    ProductComponent,
    ProductsComponent,
    ReviewComponent,
    AddProductComponent,
    EditProductComponent,
    ProductListComponent,
    AcceptComponent,
    RejectComponent,
    PendingComponent,
    PageNotFoundComponent,
  ],
})
export class PagesModule {}
