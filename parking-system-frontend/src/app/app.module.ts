import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NavbarAccountComponent } from './navbar-account/navbar-account.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { NavbarFiltersComponent } from './navbar-filters/navbar-filters.component';
import { ParkingsPageComponent } from './parkings-page/parkings-page.component';
import { ReservationsPageComponent } from './reservations-page/reservations-page.component';
import { PaymentsPageComponent } from './payments-page/payments-page.component';
import { ChangePasswordPageComponent } from './change-password-page/change-password-page.component';
import { ParkingCardComponent } from './parking-card/parking-card.component';
import { ReservationCardComponent } from './reservation-card/reservation-card.component';
import { PaymentCardComponent } from './payment-card/payment-card.component';
import { CardComponent } from './card/card.component';
import { NgxEchartsModule } from 'ngx-echarts';
import { RouterModule, RouterOutlet } from '@angular/router';
import { ParkingInfoComponent } from './parking-info/parking-info.component';
import { ReservationAddComponent } from './reservation-add/reservation-add.component';
import { NotificationComponent } from './notification/notification.component';
import { LoadingComponent } from './loading/loading.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
@NgModule({
  declarations: [
    AppComponent,
    NavbarAccountComponent,
    NavbarComponent,
    LoginPageComponent,
    NavbarFiltersComponent,
    ParkingsPageComponent,
    ReservationsPageComponent,
    PaymentsPageComponent,
    ChangePasswordPageComponent,
    ParkingCardComponent,
    ReservationCardComponent,
    PaymentCardComponent,
    CardComponent,
    ParkingInfoComponent,
    ReservationAddComponent,
    NotificationComponent,
    LoadingComponent,
    PageNotFoundComponent,
  ],
  imports: [
    RouterModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
