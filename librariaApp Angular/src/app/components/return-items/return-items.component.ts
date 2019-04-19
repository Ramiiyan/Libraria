import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-return-items',
  templateUrl: './return-items.component.html',
  styleUrls: ['./return-items.component.css']
})
export class ReturnItemsComponent implements OnInit {

  get isbn() {
    return this.returnForm.get('isbn');
  }
  get readerId() {
    return this.returnForm.get('readerId');
  }

  constructor(private fb: FormBuilder, private _apiService: ApiService) { }

  returnMsg: any;
  return: boolean;
  nofee: boolean;

  returnForm = this.fb.group({
    isbn: ['', [Validators.required, Validators.pattern(/^(97(8|9))?\d{9}(\d|X)$/)]],
    readerId: ['', Validators.required]

  });

  ngOnInit() {
  }

  onSubmit() {

    this._apiService.CheckReturnItem(this.returnForm.value.isbn, this.returnForm.value.readerId).subscribe((getdata: any) => {
      this.returnMsg = getdata;
      console.log(this.returnMsg);
      if (getdata === 'NOFEE') {
        this.nofee = true;
      } else {
        this.return = true;
      }
    });

  }

  reset() {

    this.nofee = false;
    this.return = false;
  }

}
