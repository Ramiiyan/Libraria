import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  loginFormModalEmail = new FormControl('', Validators.required);
  loginFormModalPassword = new FormControl('', Validators.required);

}
