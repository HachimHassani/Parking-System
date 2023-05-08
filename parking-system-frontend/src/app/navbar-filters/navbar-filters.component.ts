import { Component } from '@angular/core';

@Component({
	selector: 'app-navbar-filters',
	templateUrl: './navbar-filters.component.html',
	styleUrls: ['./navbar-filters.component.css']
})
export class NavbarFiltersComponent {
	//cities
	cities = Array(32).fill("Marrakech");
	//mouse inside nav
	mouseInside = false;
	
	onMouseEnterNavbar() {
		this.mouseInside = true;
	}

	onMouseLeaveNavbar() {
		this.mouseInside = false;
	}
}
