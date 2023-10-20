import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';

import { AuthGuard } from './guards/auth.guard';
import { UnauthGuard } from './guards/unauth.guard';
import { ThemesComponent } from './pages/themes/themes.component';
import { MeComponent } from './pages/me/me.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', canActivate: [UnauthGuard], component: HomeComponent },
  {
    path: '',
    canActivate: [UnauthGuard],
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'posts',
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/posts/posts.module').then(m => m.PostsModule)
  },
  {
    path: 'themes',
    canActivate: [AuthGuard],
    component: ThemesComponent
  },
  {
    path: 'me',
    canActivate: [AuthGuard],
    component: MeComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
