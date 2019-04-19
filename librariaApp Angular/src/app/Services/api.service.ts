import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  _URL  = 'http://localhost:9000';
  API_URL = 'http://localhost:9000/book';
  _book_URL = 'http://localhost:9000/allBook';
  _addBook_URL = 'http://localhost:9000/addDvds';
  _itemFound_URL = 'http://localhost:9000/itemFound';
  _reserve_URL = 'http://localhost:9000/reserve';
  _validateReader_URL = 'http://localhost:9000/validateReader';
  _bookCount_URL = 'http://localhost:9000/bookCount';
  _dvdCount_URL = 'http://localhost:9000/dvdCount';
  _newReaderId_URL = 'http://localhost:9000/newReaderId';
  _checkReturn_URL = 'http://localhost:9000/returnItem';
  _displayAllBook_URL = 'http://localhost:9000/displayAllBook';
  _displayAllDvd_URL = 'http://localhost:9000/displayAllDvd';
  _displayBorrow_URL = 'http://localhost:9000/displayBorrow';
  _delete_url = 'http://localhost:9000/deleteItems';

  constructor(private httpClient: HttpClient) {}

  getBookCount() {
    return this.httpClient.get<number>(this._bookCount_URL);
  }
  getDvdCount() {
    return this.httpClient.get<number>(this._dvdCount_URL);
  }

  getResponseAddDvd() {
    return this.httpClient.get(this._addBook_URL);
  }

  getItemAvailable(isbn: string, readerId: number) {
    return this.httpClient.get(`${this._itemFound_URL}/${isbn}/${readerId}`);
  }
  reserveItem(isbn: string, readerId: number) {
    return this.httpClient.get(`${this._reserve_URL}/${isbn}/${readerId}`);
  }
  validateReader(readerId: number) {
    return this.httpClient.get(`${this._validateReader_URL}/${readerId}`);
  }
  createReader(name, email, mobile) {
    return this.httpClient.get<any>(`${this._newReaderId_URL}/${name}/${email}/${mobile}`);
  }

  CheckReturnItem(isbn: string, readerId: number) {
    return this.httpClient.get<any>(`${this._checkReturn_URL}/${isbn}/${readerId}`);
  }
  deleteService(isbn: string) {
    return this.httpClient.get<any>(`${this._delete_url}/${isbn}`);
  }

  showBooks(): Observable<Object[]> {
    return this.httpClient.get<Object[]>(this._displayAllBook_URL);
  }
  showDvds(): Observable<Object[]> {
    return this.httpClient.get<Object[]>(this._displayAllDvd_URL);
  }
  borrowDetail(): Observable<Object[]> {
    return this.httpClient.get<Object[]>(this._displayBorrow_URL);
  }

}
