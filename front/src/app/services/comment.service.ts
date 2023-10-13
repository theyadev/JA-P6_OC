import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Comment from '../interfaces/comment.interface';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private pathService = 'api/comment';

  constructor(private httpClient: HttpClient) {}

  public detail(id: number): Observable<Comment> {
    return this.httpClient.get<Comment>(`${this.pathService}/${id}`);
  }

  public postComments(id: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>(`${this.pathService}/post/${id}`);
  }

  public create(comment: Comment): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}`, comment);
  }
}
