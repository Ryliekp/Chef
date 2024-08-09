import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'; // <-- NgModel lives here

import { AppComponent } from './app.component';
import { RecipesComponent } from './recipes/recipes.component';
import { RecipeDetailComponent } from './recipe-detail/recipe-detail.component'; 

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    RecipeDetailComponent,
    RecipesComponent,
    AppComponent,
],
  providers: [],
  bootstrap: []
})
export class AppModule { }