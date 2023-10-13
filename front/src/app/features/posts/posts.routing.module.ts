import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './components/feed/feed.component';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';


const routes: Routes = [
  { path: '', title: 'Posts', component: FeedComponent },
  { path: 'create', title: 'Sessions - create', component: FormComponent },
  { path: ':id', title: 'Sessions - detail', component: DetailComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PostsRoutingModule {
}
