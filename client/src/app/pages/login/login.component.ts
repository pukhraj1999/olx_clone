import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/core/model/User';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [],
})
export class LoginComponent {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern(
        '(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-zd$@$!%*?&].{8,20}'
      ),
    ]),
  });

  getUser(): void {
    const { email, password } = this.loginForm.value;
    if (email === '' || password === '') {
      this.showMessage('Please fill all fields!!', true);
      return;
    }

    this.authService.login({ email, password }).subscribe({
      next: (res) => {
        localStorage.setItem('user', JSON.stringify(res));
        this.showMessage('Successfully Login!!', false);
        setTimeout(() => {
          let role: String = (<UserResponse>(
            JSON.parse(<string>localStorage.getItem('user'))
          )).user.role;
          if (role === 'ADMIN') this.router.navigate(['/admin/products']);
          else this.router.navigate(['/products']);
        }, 1000);
      },
      error: (err) => {
        this.showMessage(err.error.error, true);
      },
    });
  }

  //show error msg
  showMessage(msg: String, error: Boolean): void {
    //showing message from backend
    this.isError = error;
    this.msg = msg;
    this.showMsg = true;
    //closing backend message
    setTimeout(
      () => {
        this.showMsg = false;
      },
      error ? 2000 : 1000
    );
  }

  //getters
  get email() {
    return this.loginForm.get('email');
  }
  get password() {
    return this.loginForm.get('password');
  }
}
