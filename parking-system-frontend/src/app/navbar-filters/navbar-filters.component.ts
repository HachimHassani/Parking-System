import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import citiesJson from 'src/assets/cities.json';

//parking order buttons
export interface FilterOrderButtonData {
	name: string,
	code: string,
	imageSrc: string 
}

//filter
export interface ParkingFilter {
	order?: string,
	city?: string
}

@Component({
	selector: 'app-navbar-filters',
	templateUrl: './navbar-filters.component.html',
	styleUrls: ['./navbar-filters.component.css']
})
export class NavbarFiltersComponent implements OnInit{
	//visibility
	visible = true;
	//orders
	orderButtons: Array<FilterOrderButtonData> = [
		{
			name: 'Recent',
			code: 'recent',
			imageSrc: 'assets/history.png'
		},
		{
			name: 'Collection',
			code: 'collection',
			imageSrc: 'assets/heart.png'
		},
		{
			name: 'Popular',
			code: 'popular',
			imageSrc: 'assets/fire.png'
		},
		{
			name: 'New',
			code: 'new',
			imageSrc: 'assets/shopping-bag.png'
		},
	];
	//cities
	cities = citiesJson;
	//fitler
	filter: ParkingFilter = {
		
	};
	//mouse inside nav
	mouseInside = false;

	constructor(private router: Router, private activatedRoute: ActivatedRoute) { }

	//init
	ngOnInit() {
		//on url change
		this.router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				this.visible = event.url != '/login';
				//update filter
				this.filter = {};
				//check for route to be '/'
				if (event.url.split('?')[0] != '/') {
					return;
				}
				//check query params
				this.activatedRoute.queryParams
				.subscribe( params => {
					this.filter.order = params['order'];
					this.filter.city = params['city'];
				})
			}
		});
		
	}
	//navbar mouse enter/leave
	onMouseEnterNavbar() {
		this.mouseInside = true;
	}
	onMouseLeaveNavbar() {
		this.mouseInside = false;
	}

	//add order to filter
	addOrderToFilter(order: string) {
		this.router.navigate(['/'], {
			queryParamsHandling: 'merge',
			queryParams: {
				'order': order
			},
			skipLocationChange: false
		});
		//set order
		this.filter.order = order;
	}

	//add city to filter 
	addCityToFilter(city: string) {
		this.router.navigate(['/'], {
			queryParamsHandling: 'merge',
			queryParams: {
				'city': city
			},
			skipLocationChange: false
		});
		//city
		this.filter.city = city;
	}


}
