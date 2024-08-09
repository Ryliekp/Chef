import { Component } from '@angular/core';
import { NgFor } from '@angular/common';
import { Recipe } from '../recipe';
import { RECIPES } from '../mock-recipes';
import { RecipeDetailComponent } from "../recipe-detail/recipe-detail.component";

@Component({
  standalone: true,
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css'],
  imports: [RecipeDetailComponent, NgFor],
})
export class RecipesComponent {
  recipes = RECIPES;

  selectedRecipe?: Recipe;

  onSelect(recipe: Recipe): void {
    this.selectedRecipe = recipe;
  }
}

