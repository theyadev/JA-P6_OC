import User from "./user.interface";

export default interface Post {
  id?: number;
  name: string;
  content: string;
  themeId: number;
  userId?: number;
  user?: User;
  createdAt?: string;
  updatedAt?: string;
}
