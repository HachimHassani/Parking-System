import { AfterViewInit, Component, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ParkingCardComponent, ParkingCardData } from '../parking-card/parking-card.component';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { LoadingComponent } from '../loading/loading.component';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

@Component({
	selector: 'app-parkings-page',
	templateUrl: './parkings-page.component.html',
	styleUrls: ['./parkings-page.component.css']
})
export class ParkingsPageComponent extends ProgressiveCardsLoaderComponent<ParkingCardComponent, ParkingCardData> implements OnInit, AfterViewInit{
	//get loading component
	@ViewChild(LoadingComponent) loadingComponent!: LoadingComponent;

	//constuctor
	constructor(private router: Router, private activatedRoute: ActivatedRoute, private http: HttpClient, private cookieService: CookieService) {
		super([], ParkingCardComponent);
	}

	order : string | undefined;
	city : string | undefined;

	collections :Array<string> = [];

	//on init
	ngOnInit() {
		//check query params
		this.activatedRoute.queryParams
			.subscribe(params => {
				this.order = params['order'];
				this.city = params['city'];
			})
		//look for changing link
		this.router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				this.deleteAllCards();
				this.fetchParkingData();
			}
		});
	}

	//fetch parking data
	fetchParkingData() {
		//get user token
		const token = this.cookieService.get('token');
		//define route
		let route = '/api/parking-lots';
		if (this.city && this.city != 'all') {
			route = '/api/parking-lots?city=' + this.city;
		}
		else if (this.order && this.order == 'collection') {
			route = '/api/users/favourites';
		}

		//fetch data from api
		const subscrition = this.http.get<Array<any>>(route, {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			//convert data
			let parkingList: Array<ParkingCardData> = [];
			//fetch fata
			if (res)
			res.forEach(data => {
				let parkingData :ParkingCardData = {
					id: data.id,
					name: data.name,
					city: data.city,
					inCollection: this.collections.indexOf(data.id) >= 0
				};
				parkingList.push(parkingData);
			});
			//set data
			this.setData(parkingList);
			this.loadingComponent.hide();
			this.addMultipleCards(10);
			subscrition.unsubscribe();
		});
		
	}

	async fetchCollection() {
		await new Promise(r => setTimeout(r, 0));
		//simulate loading time
		this.loadingComponent.show();
		//get user token
		const token = this.cookieService.get('token');
		//fetch data from api
		this.http.get<Array<any>>('/api/users/favourites', {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			res.forEach((data: any) => {
				this.collections.push(data.id);
			});
			this.fetchParkingData();
		});

	}

	//
	override ngAfterViewInit(): void {
		this.fetchCollection();
	}

}
