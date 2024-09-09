import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styles: [],
})
export class SignupComponent {
  isError: Boolean = false;
  msg: String = '';
  showMsg: Boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  signupForm = new FormGroup({
    firstname: new FormControl('', [Validators.required]),
    lastname: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern(
        '(?=.*[A-Z])(?=.*[0-9])(?=.*[$@$!%*?&])[A-Za-zd$@$!%*?&].{8,20}'
      ),
    ]),
  });

  getUser(): void {
    const { firstname, lastname, email, password } = this.signupForm.value;
    if (
      firstname === '' ||
      lastname === '' ||
      email === '' ||
      password === ''
    ) {
      this.showMessage('Please fill all fields!!', true);
      return;
    }

    this.authService
      .signup({ firstname, lastname, email, password })
      .subscribe({
        next: (res) => {
          this.showMessage('Successfully Signup!!', false);
          setTimeout(() => {
            this.router.navigate(['/login']);
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
  get firstname() {
    return this.signupForm.get('firstname');
  }
  get lastname() {
    return this.signupForm.get('lastname');
  }
  get email() {
    return this.signupForm.get('email');
  }
  get password() {
    return this.signupForm.get('password');
  }
}
