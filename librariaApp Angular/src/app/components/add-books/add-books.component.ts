import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormArray } from '@angular/forms';
import { patternValidator } from 'src/app/Validation/myform.validator';
import { RegistrationService } from 'src/app/Services/registration.service';
import { ApiService } from 'src/app/Services/api.service';
import { errorHandler } from '@angular/platform-browser/src/browser';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'app-addBooks',
  templateUrl: './add-books.component.html',
  styleUrls: ['./add-books.component.css']
})
export class AddBooksComponent implements OnInit {

  get title() {
    return this.addBookForm.get('title');
  }
  get isbn() {
    return this.addBookForm.get('isbn');
  }
  get author() {
    return this.addBookForm.get('author');
  }
  get sector() {
    return this.addBookForm.get('sector');
  }
  get publisher() {
    return this.addBookForm.get('publisher');
  }
  get publishDate() {
    return this.addBookForm.get('publishDate');
  }
  get pages() {
    return this.addBookForm.get('pages');
  }
  // errorMsg = '';
  get alternateAuthors() {
    return this.addBookForm.get('alternateAuthors') as FormArray;
  }

  constructor(private fb: FormBuilder, private _registrationService: RegistrationService, private apiService: ApiService) { }

  sectors = [{ 'name': 'Fiction' }, { 'name': 'Novel' },
  { 'name': 'Fantasy' }, { 'name': 'Science Fiction' },
  { 'name': 'Mystery' }, { 'name': 'Non-Fiction' },
  { 'name': 'Thriller' }, { 'name': 'Short story' },
  { 'name': 'Horror' }, { 'name': 'Literature' },
  { 'name': 'Fairy tale' }, { 'name': 'Biography' }
  ];

  addBookForm = this.fb.group({
    title: ['', Validators.required],
    isbn: ['', [Validators.required, Validators.pattern(/^(97(8|9))?\d{9}(\d|X)$/)]],
    author: ['', Validators.required],
    alternateAuthors: this.fb.array([]),
    sector: ['', Validators.required],
    publisher: ['', Validators.required],
    publishDate: ['', Validators.required],
    pages: ['', Validators.required]
  });

  addAlternateAuthor() {
    this.alternateAuthors.push(this.fb.control(''));
  }
  deleteField(i) {
    this.alternateAuthors.removeAt(i);
  }

  ngOnInit() {
    // this.getResponseAddDvd();
  }

  loadApiData() {
    this.addBookForm.setValue({
      title: 'Harry Potter',
      isbn: '123556485',
      author: 'J.K. Rolling',
      sector: 'Fiction',
      publisher: 'James',
      publishDate: '2010-04-27',
      pages: 251,
      alternateAuthors: null
    });
  }

  onSubmit() {
    console.log('submit ran');
    console.log(this.addBookForm.value);
    this._registrationService.addBookService(this.addBookForm.value)
      .subscribe(
        response => console.log('Success!', response),
        error => console.error('Error!', error),
        // error => this.errorMsg = error.statusText
      );
    alert(this.addBookForm.value.title + ' is Successfully Added to Libraria !');
  }
}
