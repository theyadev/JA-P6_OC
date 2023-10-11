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

  ngOnInit(): void {
    const sessionInformation = sessionStorage.getItem('sessionInformation');

    if (sessionInformation) {
      this.sessionService.logIn(JSON.parse(sessionInformation));
    }
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$isLogged();
  }

  public $showNavBar(): boolean {
    if (this.router.url === '/') return false
    return true
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }
}
