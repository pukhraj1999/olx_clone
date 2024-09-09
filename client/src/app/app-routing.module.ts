import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ProductsComponent } from './pages/products/products.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';
import { ProductListComponent } from './pages/admin/product-list/product-list.component';
import { AddProductComponent } from './pages/admin/add-product/add-product.component';
import { EditProductComponent } from './pages/admin/edit-product/edit-product.component';
import { ReviewComponent } from './pages/admin/review/review.component';
import { AcceptComponent } from './pages/admin/review/accept/accept.component';
import { RejectComponent } from './pages/admin/review/reject/reject.component';
import { PendingComponent } from './pages/admin/review/pending/pending.component';
import { ProductComponent } from './pages/product/product.component';
import { isAdmin } from './core/guards/checkers';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'products',
    component: ProductsComponent,
  },
  {
    path: 'product/:id',
    component: ProductComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'admin/products',
    component: ProductListComponent,
    canActivate: [isAdmin],
  },
  {
    path: 'admin/add/product',
    component: AddProductComponent,
    canActivate: [isAdmin],
  },
  {
    path: 'admin/edit/product/:id',
    component: EditProductComponent,
    canActivate: [isAdmin],
  },
  {
    path: 'admin/review',
    component: ReviewComponent,
    canActivate: [isAdmin],
    children: [
      {
        path: 'accept',
        component: AcceptComponent,
        canActivate: [isAdmin],
      },
      {
        path: 'reject',
        component: RejectComponent,
        canActivate: [isAdmin],
      },
      {
        path: 'pending',
        component: PendingComponent,
        canActivate: [isAdmin],
      },
    ],
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
