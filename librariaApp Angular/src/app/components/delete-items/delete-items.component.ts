import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { RegistrationService } from 'src/app/Services/registration.service';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-delete-items',
  templateUrl: './delete-items.component.html',
  styleUrls: ['./delete-items.component.css']
})
export class DeleteItemsComponent implements OnInit {


  get isbn() {
    return this.deleteForm.get('isbn');
  }

  msgInfo = '';
  errorMsg = '';
  constructor(private fb: FormBuilder, private _apiService: ApiService) { }

  deleteForm = this.fb.group({
    isbn : ['', [Validators.required, Validators.pattern(/^(97(8|9))?\d{9}(\d|X)$/)]],

  });
  ngOnInit() {
  }
  onSubmit() {
    console.log(this.deleteForm.value);
    this._apiService.deleteService(this.deleteForm.value.isbn).subscribe((getdata: any) => {
      alert(getdata);
    });
  }

}
