import { Component, HostListener } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
	selector: 'app-navbar-account',
	templateUrl: './navbar-account.component.html',
	styleUrls: ['./navbar-account.component.css']
})
export class NavbarAccountComponent {
	active = false;
	clicked = false;
	
	constructor() {}

	//icon on clicked
	onIconClicked() {
		this.active = !this.active;
		this.clicked = true;

	}

	//on click everywhere 
	@HostListener('document:click', ['$event'])
	documentClick(event: MouseEvent) {
		if (!this.clicked)
			this.active = false;
	}
	//on released
	@HostListener('document:mouseup', ['$event'])
	documentMouseUp(event: MouseEvent) {
		this.clicked = false;
	}
}

