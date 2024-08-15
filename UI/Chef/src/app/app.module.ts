import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppComponent } from './app.component';
import { RecipesComponent } from './recipes/recipes.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component'; 
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { provideHttpClient } from '@angular/common/http';
import { RecipeSearchComponent } from "./recipe-search/recipe-search.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
],
  declarations: [
    AppComponent,
    DashboardComponent,
    RecipesComponent,
    RecipeDetailComponent,
    MessagesComponent,
    RecipeSearchComponent
  ],
  providers: [
    provideHttpClient(),
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }