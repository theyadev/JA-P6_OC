import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Post from '../interfaces/post.interface';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private pathService = 'api/post';

  constructor(private httpClient: HttpClient, private userService: UserService) {}

  public feed(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(`${this.pathService}/feed`);
  }

  public detail(id: string): Observable<Post> {
    return this.httpClient.get<Post>(`${this.pathService}/${id}`);
  }

  public create(post: Post): Observable<Post> {
    return this.httpClient.post<Post>(`${this.pathService}`, post);
  }
}
