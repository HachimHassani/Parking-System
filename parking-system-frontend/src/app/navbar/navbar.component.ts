import { Component, OnInit } from '@angular/core';
import { NavigationEnd,  Router } from '@angular/router';
import { NavbarAccountComponent } from '../navbar-account/navbar-account.component';

@Component({
	selector: 'app-navbar',
	templateUrl: './navbar.component.html',
	styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
	//visibility
	visible=true;
	titleHovered = false;

	constructor(private router: Router) {}
	
	ngOnInit() {
		this.router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				this.visible = event.url != '/login';
			}
		});
	}

	onSearchBarEnter(search: string) {
		this.router.navigate(['/'], {
			queryParamsHandling: 'merge',
			queryParams: {
				'search': search
			},
			skipLocationChange: false
		});
	}
	
}
