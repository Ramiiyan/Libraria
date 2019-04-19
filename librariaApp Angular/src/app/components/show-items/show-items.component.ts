import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-show-items',
  templateUrl: './show-items.component.html',
  styleUrls: ['./show-items.component.css']
})

export class ShowItemsComponent implements OnInit {

  public bookList = [] ;
  public dvdList = [] ;
  public itemList = [] ;

  constructor(private _apiService: ApiService) { }

  ngOnInit() {

    this.ShowAvailableItems();
    this.itemList = this.bookList.concat(this.dvdList);
  }

  ShowAvailableItems() {
    this._apiService.showBooks().subscribe(data => {
      this.bookList = data;
      console.log(data);
    });

    this._apiService.showDvds().subscribe(data => {
      this.dvdList = data;
      console.log(data);
    });
  }

}
