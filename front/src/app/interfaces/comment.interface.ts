import User from "./user.interface";

export default interface Comment {
  id?: number;
  postId: number;
  content: string;
  userId?: number;
  user?: User;
  createdAt?: string;
  updatedAt?: string;
}
