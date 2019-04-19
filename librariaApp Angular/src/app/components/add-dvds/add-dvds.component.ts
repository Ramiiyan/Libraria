import { Component, OnInit } from '@angular/core';

import {FormBuilder, FormGroup, Validators, FormArray, FormControl} from '@angular/forms';
import {patternValidator} from 'src/app/Validation/myform.validator';
import { RegistrationService } from 'src/app/Services/registration.service';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'app-addDvds',
  templateUrl: './add-dvds.component.html',
  styleUrls: ['./add-dvds.component.css']
})
export class AddDvdsComponent implements OnInit {
  get title() {
    return this.addDvdForm.get('title');
  }
  get isbn() {
    return this.addDvdForm.get('isbn');
  }
  get producer() {
    return this.addDvdForm.get('producer');
  }
  get sector() {
    return this.addDvdForm.get('sector');
  }
  get actors() {
    return this.addDvdForm.get('actors');
  }
  get publishDate() {
    return this.addDvdForm.get('publishDate');
  }
  get languageArray() {
    return <FormArray>this.addDvdForm.get('favLanguages');
  }
  get subtitleArray() {
    return <FormArray>this.addDvdForm.get('favSubtitles');
  }

    // errorMsg = '';

  sectors = [
    {'name': 'Fiction'}, {'name': 'Novel'},
    {'name': 'Fantasy'}, {'name': 'Science Fiction'},
    {'name': 'Mystery'}, {'name': 'Non-Fiction'},
    {'name': 'Thriller'}, {'name': 'Short story'},
    {'name': 'Horror'}, {'name': 'Literature'},
    {'name': 'Fairy tale'}, {'name': 'Biography'}
  ];

  languages = ['English', 'Tamil', 'Sinhala', 'Hindi', 'Malayalam'];
  selectedLanguages = [];
  favLangError: Boolean = true;

  subtitles = ['English', 'Tamil', 'Sinhala', 'Hindi', 'Malayalam', 'mandarin chinese'];
  selectedSubtitles = [];
  favSubtitleError: Boolean = true;


  constructor(private fb: FormBuilder, private _registrationService: RegistrationService) {}
    // const controls = this.languages.map(c => new FormControl(false));

  addDvdForm = this.fb.group({
    title: ['', Validators.required],
    isbn : ['', [Validators.required, Validators.pattern(/^(97(8|9))?\d{9}(\d|X)$/)]],
    producer : ['', Validators.required],
    sector: ['', Validators.required],
    actors: ['', Validators.required],
    publishDate: ['', Validators.required],
    favLanguages: this.addLangControls(),
    favSubtitles: this.addSubtitleControls()

  });

  addLangControls() {
    const arr = this.languages.map( element => {
      return this.fb.control(false);
    });
    return this.fb.array(arr);
  }

  getselectedLanguages() {
    this.selectedLanguages = [];
    this.languageArray.controls.forEach((control, i) => {
      if (control.value) {
        this.selectedLanguages.push(this.languages[i]);
      }
    });
    // validation checkbox
    this.favLangError = this.selectedLanguages.length > 0 ? false : true;
  }

  checkLanguageTouched() {
    let flag = false;
    this.languageArray.controls.forEach(control => {
      if (control.touched) {
        flag = true;
      }
    });
    return flag;
  }

  addSubtitleControls() {
    const arr = this.subtitles.map( element => {
      return this.fb.control(false);
    });
    return this.fb.array(arr);
  }
  getselectedSubtitles() {
    this.selectedSubtitles = [];
    this.subtitleArray.controls.forEach((control, i) => {
      if (control.value) {
        this.selectedSubtitles.push(this.subtitles[i]);
      }
    });
    // validation checkbox
    this.favSubtitleError = this.selectedSubtitles.length > 0 ? false : true;
  }
  checkSubtitleTouched() {
    let flag = false;
    this.subtitleArray.controls.forEach(control => {
      if (control.touched) {
        flag = true;
      }
    });
    return flag;
  }
  ngOnInit() {

  }

  loadApiData() {
    this.addDvdForm.setValue({
      title: 'Avatar',
      isbn: '1290765572',
      producer: 'Jon Landau',
      sector: 'Science Fiction',
      actors: 'Sam',
      publishDate: '2010-04-27',
      favLanguages: [''],
      favSubtitles: ['']
      // favLanguages: [ false, false, false, false, false ]
    });
  }


  onSubmit() {

    const selectedlanguage = this.selectedLanguages;
    const selectedSubtitle = this.selectedSubtitles;

    console.log('submit ran');
    console.log({...this.addDvdForm.value, selectedlanguage, selectedSubtitle});
    this._registrationService.addDvdService({...this.addDvdForm.value, selectedlanguage, selectedSubtitle})
    .subscribe(
      response => console.log('Success!', response),
      error => console.error('Error!', error),
      // error => this.errorMsg = error.statusText
    );
    alert(this.addDvdForm.value.title + ' is Successfully Added to Libraria !');
  }

}
