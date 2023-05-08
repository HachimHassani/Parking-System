import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
	selector: 'app-navbar-filters',
	templateUrl: './navbar-filters.component.html',
	styleUrls: ['./navbar-filters.component.css']
})
export class NavbarFiltersComponent implements OnInit{
	//visibility
	visible = true;
	
	//cities
	cities = Array(32).fill("Marrakech");
	//mouse inside nav
	mouseInside = false;

	constructor(private router: Router) { }

	
	ngOnInit() {
		this.router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				this.visible = event.url == '/';
			}
		});
	}
	onMouseEnterNavbar() {
		this.mouseInside = true;
	}

	onMouseLeaveNavbar() {
		this.mouseInside = false;
	}
}
