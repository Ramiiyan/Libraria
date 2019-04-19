import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddBooksComponent } from './components/add-books/add-books.component';
import { AddDvdsComponent } from './components/add-dvds/add-dvds.component';
import { DeleteItemsComponent } from './components/delete-items/delete-items.component';
import { BorrowItemsComponent } from './components/borrow-items/borrow-items.component';
import { ReturnItemsComponent } from './components/return-items/return-items.component';
import { GenerateReportComponent } from './components/generate-report/generate-report.component';
import { ShowItemsComponent } from './components/show-items/show-items.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  {path:'', redirectTo:'/showItems', pathMatch:'full'},
  {path: 'addBooks', component:AddBooksComponent},
  {path: 'addDvds',component:AddDvdsComponent},
  {path: 'deleteItems',component:DeleteItemsComponent},
  {path: 'borrowItems',component:BorrowItemsComponent},
  {path: 'returnItems',component:ReturnItemsComponent},
  {path: 'generateReport',component:GenerateReportComponent},
  {path: 'showItems',component:ShowItemsComponent},
  {path:"**",component:PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [AddBooksComponent,AddDvdsComponent,DeleteItemsComponent,
  BorrowItemsComponent,ReturnItemsComponent,GenerateReportComponent,ShowItemsComponent,PageNotFoundComponent]