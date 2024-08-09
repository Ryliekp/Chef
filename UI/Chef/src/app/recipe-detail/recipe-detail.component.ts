import { Component, Input } from '@angular/core';
import { NgIf, UpperCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Recipe } from '../recipe';

@Component({
  standalone: true,
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: [ './recipe-detail.component.css' ],
  imports: [FormsModule, NgIf, UpperCasePipe],
})
export class RecipeDetailComponent{
  @Input() recipe?: Recipe;
}
