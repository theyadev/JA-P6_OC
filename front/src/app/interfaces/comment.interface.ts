export default interface Comment {
  id?: number;
  postId: number;
  content: string;
  userId: number;
  createdAt?: string;
  updatedAt?: string;
}
