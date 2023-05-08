import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NavbarAccountComponent } from './navbar-account/navbar-account.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { NavbarFiltersComponent } from './navbar-filters/navbar-filters.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    NavbarAccountComponent,
    LoginPageComponent,
    NavbarFiltersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
