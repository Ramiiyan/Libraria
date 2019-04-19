import { Component, OnInit } from '@angular/core';
// tslint:disable-next-line:import-spacing
import { FormBuilder, Validators } from '@angular/forms';
import { RegistrationService } from 'src/app/Services/registration.service';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-borrow-items',
  templateUrl: './borrow-items.component.html',
  styleUrls: ['./borrow-items.component.css']
})
export class BorrowItemsComponent implements OnInit {

  get isbn() {
    return this.borrowForm.get('isbn');
  }
  get readerId() {
    return this.borrowForm.get('readerId');
  }
  get name() {
    return this.readerForm.get('name');
  }
  get email() {
    return this.readerForm.get('email');
  }
  get mobile() {
    return this.readerForm.get('mobile');
  }

  reserve: string;
  readerCheck: string;
  noItem: boolean;
  available: boolean;
  borrowAlready: boolean;
  reserved: boolean;
  changedReaderId: number;
  itemStatus: string;
  newReaderId: any;

  borrowForm = this.fb.group({
    isbn: ['', [Validators.required, Validators.pattern(/^(97(8|9))?\d{9}(\d|X)$/)]],
    readerId: ['', Validators.required]
  });
  readerForm = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required,
    // tslint:disable-next-line:max-line-length
    Validators.pattern(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)]],
    mobile: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, private _apiservice: ApiService) { }

  ngOnInit() {
  }
  createReader() {
    this._apiservice.createReader(this.readerForm.value.name, this.readerForm.value.email,
      this.readerForm.value.mobile).subscribe((getNewId: any) => {
        console.log(this.readerForm.value);
        this.newReaderId = getNewId;
        console.log(getNewId);
        console.log(this.newReaderId);
      });
  }
  validateReader() {
    this._apiservice.validateReader(this.borrowForm.value.readerId).subscribe((getdata: string) => {
      this.readerCheck = getdata;
      // console.log(getdata);
      console.log(this.readerCheck);
      if (getdata === 'NOTREADER') {
        alert(this.borrowForm.value.readerId + ' is not valid Id.check Again. \n OR Create New ID');

      } else if (getdata === 'Bad') {
        alert('The Reader has not Returned Item yet !');

      } else if (getdata === 'HaveItem') {
        alert('The Reader Already Have an Item !');

      } else {
        alert('Reader can Borrow Libraria Item.');
      }

    });
  }
  checkReader() {
    if (this.readerCheck === 'Good') {
      return true;
    }
  }
  reserveItem() {
    this._apiservice.reserveItem(this.borrowForm.value.isbn,
      this.borrowForm.value.readerId).subscribe((getdata: string) => {
        this.reserve = getdata;
        console.log(this.reserve);
        console.log(getdata);
        alert(getdata);
      });
  }
  submit() {

    this._apiservice.getItemAvailable(this.borrowForm.value.isbn,
      this.borrowForm.value.readerId).subscribe((getdata: string) => {
        console.log(getdata);
        this.itemStatus = getdata;
        console.log('Status of Item : ' + this.itemStatus);
        if (getdata === 'NoItem') {
          this.noItem = true;
          console.log('itemStatus is noItem. this Item isn\'t in db');

        } else if (getdata === 'Available') {
          this.available = true;
          console.log('Item Successfully Borrowed !');

        } else if (getdata === 'NotAvailable') {
          this.borrowAlready = true;
          console.log('This Item was Already Borrowed');

        } else if (getdata === 'Reserved') {
          this.reserved = true;
          console.log('This Item was Already Reserved');
        }
      });
    console.log(this.borrowForm.value);

  }
  reset() {
    this.borrowAlready = false;
    this.available = false;
    this.noItem = false;
    this.reserved = false;
    this.readerCheck = 'Bad';
    // this.borrowForm.reset();
  }

}
