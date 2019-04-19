import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-navigation-panel',
  templateUrl: './navigation-panel.component.html',
  styleUrls: ['./navigation-panel.component.css']
})
export class NavigationPanelComponent implements OnInit {
  public books: number;
  public dvds: number;
  public remainBook: number;
  public remainDvd: number;
  public canAddBook = false;
  public canAddDvd = false;

  constructor(private _apiservice: ApiService) { }

  ngOnInit() {
    this.getBookCount();
    this.getDvdCount();
  }
  public getBookCount() {
    this._apiservice.getBookCount().subscribe((books: number) => {
      this.books = books;
      this.remainBook = 100 - this.books;
      if (this.books < 100) {
        this.canAddBook = true;
        console.log('You Can add Book');
      }
    console.log(this.remainBook);
    });
  }

  public getDvdCount() {
    this._apiservice.getDvdCount().subscribe((dvds: number) => {
      this.dvds = dvds;
      this.remainDvd = 50 - this.dvds;
      if (this.dvds < 50) {
        this.canAddDvd = true;
        console.log('You Can add Dvd');
      }
    console.log(this.remainDvd);
    });
  }


}

