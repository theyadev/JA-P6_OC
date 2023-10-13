import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import Theme from '../interfaces/theme.interface';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = 'api/theme';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.pathService);
  }

  public detail(id: string): Observable<Theme> {
    return this.httpClient.get<Theme>(`${this.pathService}/${id}`);
  }

  public subscribe(id: string): Observable<Theme> {
    return this.httpClient.post<Theme>(`${this.pathService}/${id}/subscribe`, {});
  }

  public unsubscribe(id: string): Observable<Theme> {
    return this.httpClient.post<Theme>(`${this.pathService}/${id}/unsubscribe`, {});
  }

}
