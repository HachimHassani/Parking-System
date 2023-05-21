import { Injectable, inject } from '@angular/core';
import { ActivatedRoute, CanActivateFn, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class PermissionService {
	constructor (private router: Router, private cookieService: CookieService) {}

	canActive() {
		return this.cookieService.check('token');
	}

	navigateToLogin() {
		this.router.navigate(['/login']);
	}
}

export const authGuard: CanActivateFn = (route, state) => {
  	const isLogged = inject(PermissionService).canActive();
	if (isLogged)
		return true;

	inject(PermissionService).navigateToLogin();
	return false;
};
