import { AfterViewInit, Component, ContentChild, OnInit, ViewChild, ViewContainerRef, ViewRef } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';
import { LoadingComponent } from '../loading/loading.component';
import { CookieService } from 'ngx-cookie-service';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit{
	@ViewChild(LoadingComponent) loadingComponent!:LoadingComponent;
	@ViewChild('notificationContainer', {read: ViewContainerRef}) notificationContainer!:ViewContainerRef;

	constructor(private router: Router, private cookieService: CookieService, private http: HttpClient) {}

	//form information
	isLogin = true;
	isLoading = false;

	//login data
	loginData = {
		email: '',
		password: ''
	};

	//signin data
	signinData = {
		firstName: '',
		lastName: '',
		liscencePlate: '',
		phone: '',
		email: '',
		password: '',
		confirmPassword: ''
	}

	ngOnInit(): void {

	}
	
	//submit
	//on login click
	onLoginClicked() {
		//set loading
		this.loadingComponent.show();

		//authentifictaion
		const authRequest = this.http.post('/api/auth/authenticate', {
			email: this.loginData.email,
			password: this.loginData.password
		});

		//error
		const handleAuthError = (error: HttpErrorResponse) => {
			this.loadingComponent.hide();
			let notification = this.notificationContainer.createComponent<NotificationComponent>(NotificationComponent);
			notification.instance.show("error", "Password Error", 3)
				.then(() => {
					notification.destroy();
				});
			return throwError(() => new Error('bad auth!'));
		};

		//subscrible
		const authSubscription = authRequest.pipe(catchError(handleAuthError))
		.subscribe((data: any) => {
			//get token
			const token = data.access_token;
			//add token to cookies
			this.cookieService.set('token', token);
			//end
			this.loadingComponent.hide();
			this.router.navigate(['/']);
			authSubscription.unsubscribe();
		});
	}

	//signin
	async createAccount() {
		//artifcial wait to fetch data
		await new Promise(r => setTimeout(r, 1000));
		return this.signinData.password == this.signinData.confirmPassword;
	}

	//signin clicked
	async onSigninClicked() {
		//set loading
		this.loadingComponent.show();
		//verify user authentification
		const created = await this.createAccount();
		//set loading back to normal
		this.loadingComponent.hide()
		//create notification
		let notification = this.notificationContainer.createComponent<NotificationComponent>(NotificationComponent);
		if (created) {
			this.isLogin = true;
			//notify for creation
			await notification.instance.show("correct", "Account Created", 3);
		}
		else {
			//notify for bad passowrd
			await notification.instance.show("error", "Password confirm incorrect", 3);
		}
		notification.destroy();
	}
	
	//buttons events
	//switch button
	switchLoginMode() {
		//reverse login mode
		this.isLogin = !this.isLogin;
	}
	//submit button
	async onPositiveClicked() {
		if (this.isLogin) {
			this.onLoginClicked();
		} else {
			this.onSigninClicked();
		}
	}
}
