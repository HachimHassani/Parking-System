import { AfterViewInit, Component, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ParkingCardComponent, ParkingCardData } from '../parking-card/parking-card.component';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { NavigationEnd, Router } from '@angular/router';
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
	constructor(private router: Router, private http: HttpClient, private cookieService: CookieService) {
		super([], ParkingCardComponent);
	}

	//on init
	ngOnInit() {
		//look for changing link
		this.router.events.subscribe(event => {
			if (event instanceof NavigationEnd) {
				//get params
				//let order = ;
				this.deleteAllCards();
				this.fetchParkingData();
			}
		});
	}

	//fetch parking data
	async fetchParkingData() {
		await new Promise(r => setTimeout(r, 0));
		//simulate loading time
		this.loadingComponent.show();
		//get user token
		const token = this.cookieService.get('token');
		//fetch data from api
		const subscrition = this.http.get<Array<any>>('api/parking-lots', {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			//convert data
			let parkingList: Array<ParkingCardData> = [];
			res.forEach(data => {
				let parkingData :ParkingCardData = {
					id: data.id,
					name: data.name,
					city: data.city,
					inCollection: false
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

	//
	override ngAfterViewInit(): void {
		this.fetchParkingData();
	}

}
