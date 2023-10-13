import { NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormComponent } from './components/form/form.component';
import localeFr from '@angular/common/locales/fr';
import { PostsRoutingModule } from './posts.routing.module';
import { FeedComponent } from './components/feed/feed.component';
import { DetailComponent } from './components/detail/detail.component';
registerLocaleData(localeFr);


@NgModule({
  declarations: [
    FeedComponent,
    FormComponent,
    DetailComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PostsRoutingModule
  ]
})
export class PostsModule { }
