import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './login-page/login-page.component';
import { ChangePasswordPageComponent } from './change-password-page/change-password-page.component';
import { ParkingsPageComponent } from './parkings-page/parkings-page.component';
import { PaymentsPageComponent } from './payments-page/payments-page.component';
import { ReservationsPageComponent } from './reservations-page/reservations-page.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  //user
  {
    path: 'login',
    component: LoginPageComponent
  },
  {
    path: 'change-password',
    component: ChangePasswordPageComponent
  },
  //home pages
  {
    path: '',
    component: ParkingsPageComponent,
  },
  //historique
  {
    path: 'payments',
    component: PaymentsPageComponent
  },
  {
    path: 'reservations',
    component: ReservationsPageComponent
  },
  //page not found
  {
    path: '**',
    component: PageNotFoundComponent
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: "enabled"})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
