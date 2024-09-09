import { Component, DoCheck } from '@angular/core';
import { User, UserResponse } from 'src/app/core/model/User';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements DoCheck {
  userExist: boolean = false;
  userRole: String = '';

  ngDoCheck(): void {
    //checking user exist on local storage after every change
    if (localStorage.getItem('user')) {
      this.userExist = true;
      let userInfo: UserResponse = JSON.parse(
        localStorage.getItem('user') as string
      );
      this.userRole = userInfo.user.role;
    }
  }

  logout() {
    if (localStorage.getItem('user')) {
      localStorage.removeItem('user');
      this.userExist = false;
    }
    this.toggleNav();
  }

  toggleNav(): void {
    let menu = document.getElementById('menu');
    menu?.classList.toggle('active');
  }
}
