import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  _URL = 'http://localhost:9000';
  _addBook_url = 'http://localhost:9000/addBooks';
  _addDvd_url = 'http://localhost:9000/addDvds';
  _borrow_url = 'http://localhost:9000/borrowItems';
  _return_url = 'http://localhost:9000/returnItems';

  constructor(private _http: HttpClient) { }

  addBookService(bookData) {
    return this._http.post<any>(this._addBook_url, bookData);
  }

  addDvdService(DvdData) {
    return this._http.post<any>(this._addDvd_url, DvdData);
  }

  // borrowService(borrowInfo){
  //   return this._http.post<any>(this._borrow_url,borrowInfo);
  // }

  returnService(returnInfo) {
    return this._http.post<any>(this._return_url, returnInfo);
  }


}
