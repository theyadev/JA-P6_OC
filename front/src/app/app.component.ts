import { Component } from '@angular/core';
import { AuthService } from './features/auth/services/auth.service';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'front';

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public $showNavBar(): Observable<boolean> {
    // Show navbar everywhere EXCEPT on index page when not logged in
    const currentUrl = this.router.url;
    if (currentUrl !== '/') return of(true);


    return this.$isLogged();
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }
}
