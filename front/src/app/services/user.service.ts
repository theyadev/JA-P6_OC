import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import User from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'api/user';

  constructor(private httpClient: HttpClient) {}

  public detail(id: string): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public me(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public update(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.pathService}/`, {
      email: user.email,
      username: user.username,
    });
  }
}
