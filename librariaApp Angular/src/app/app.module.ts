import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavigationPanelComponent } from './components/navigation-panel/navigation-panel.component';
import { HeaderComponent } from './components/header/header.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HttpClientModule } from '@angular/common/http';
import { AddBooksComponent } from './components/add-books/add-books.component';
import { AddDvdsComponent } from './components/add-dvds/add-dvds.component';
import { BorrowItemsComponent } from './components/borrow-items/borrow-items.component';
import { ReturnItemsComponent } from './components/return-items/return-items.component';
import { GenerateReportComponent } from './components/generate-report/generate-report.component';
import { DeleteItemsComponent } from './components/delete-items/delete-items.component';
import { ShowItemsComponent } from './components/show-items/show-items.component';
import { ItemFilterPipe } from './components/show-items/item-filter.pipe';

// import { BootstrapModalModule } from 'ng6-bootstrap-modal';


@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    NavigationPanelComponent,
    HeaderComponent,
    PageNotFoundComponent,
    AddBooksComponent,
    AddDvdsComponent,
    BorrowItemsComponent,
    ReturnItemsComponent,
    GenerateReportComponent,
    DeleteItemsComponent,
    ShowItemsComponent,
    ItemFilterPipe

    // BootstrapModalModule

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

