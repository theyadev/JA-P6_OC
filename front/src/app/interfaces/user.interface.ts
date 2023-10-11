export default interface User {
  id?: number;
  email: string;
  username: string;
  themes?: number[];
  createdAt?: string; 
  updatedAt?: string; 
}
