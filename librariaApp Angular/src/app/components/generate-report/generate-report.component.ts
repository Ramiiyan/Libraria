import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/Services/api.service';

@Component({
  selector: 'app-generate-report',
  templateUrl: './generate-report.component.html',
  styleUrls: ['./generate-report.component.css']
})
export class GenerateReportComponent implements OnInit {

  public borrower = [] ;
  constructor(private _apiService: ApiService) { }

  ngOnInit() {
    this.borrowDetail();
  }

  borrowDetail() {
    this._apiService.borrowDetail().subscribe(data => {
      this.borrower = data;
      console.log(data);
    });
  }

}
