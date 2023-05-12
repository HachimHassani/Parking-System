import { AfterViewInit, Component, ContentChild, OnInit, ViewChild, ViewContainerRef, ViewRef } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';
import { LoadingComponent } from '../loading/loading.component';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit{
	@ViewChild(LoadingComponent) loadingComponent!:LoadingComponent;
	@ViewChild('notificationContainer', {read: ViewContainerRef}) notificationContainer!:ViewContainerRef;

	constructor(private router: Router) {}

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
		email: '',
		password: '',
		confirmPassword: ''
	}

	ngOnInit(): void {

	}
	
	//submit
	//authentification
	async checkUserAuthentification() {
		//artifcial wait to fetch data
		await new Promise(r => setTimeout(r, 1000));
		return this.loginData.email == "haitamksiks2001@gmail.com" && this.loginData.password == "123456789";
	}

	//on login click
	async onLoginClicked() {
		//set loading
		this.loadingComponent.show();
		//verify user authentification
		const connected = await this.checkUserAuthentification();
		//set loading back to normal
		this.loadingComponent.hide();
		if (connected) {
			await new Promise(r => setTimeout(r, 550));
			this.router.navigate(['/']);
			return;
		}
		//notify for bad passowrd
		let notification = this.notificationContainer.createComponent<NotificationComponent>(NotificationComponent);
		await notification.instance.show("error", "Password Error", 3);
		notification.destroy();
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
