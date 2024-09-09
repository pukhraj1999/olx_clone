import { UserResponse } from '../model/User';

export function isAdmin(): Boolean {
  let user = (<UserResponse>JSON.parse(<string>localStorage.getItem('user')))
    .user;
  if (user.role === 'ADMIN') return true;
  return false;
}
