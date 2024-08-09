import { Component } from '@angular/core';
import { RecipesComponent } from './recipes/recipes.component';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [RecipesComponent],
})
export class AppComponent {
  title = 'Chef';
}
