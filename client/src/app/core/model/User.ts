export interface User {
  userId: Number;
  firstname: String;
  lastname: String;
  email: String;
  password: String;
  role: String;
}

export interface UserResponse {
  user: User;
  token: String;
}
