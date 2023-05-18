import { AfterViewInit, Component, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ParkingCardComponent } from '../parking-card/parking-card.component';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { NavigationEnd, Router } from '@angular/router';
import { LoadingComponent } from '../loading/loading.component';
import { HttpClient } from '@angular/common/http';

@Component({
	selector: 'app-parkings-page',
	templateUrl: './parkings-page.component.html',
	styleUrls: ['./parkings-page.component.css']
})
export class ParkingsPageComponent extends ProgressiveCardsLoaderComponent<ParkingCardComponent> implements OnInit, AfterViewInit{
	//get loading component
	@ViewChild(LoadingComponent) loadingComponent!: LoadingComponent;

	//constuctor
	constructor(private router: Router, private http: HttpClient) {
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
		//fetch data from api
		/*this.http.get('api/parking-lots/645d7a9dcbf6044e7e3ffac3').subscribe(res => {
			console.log(res);
		});*/
		await new Promise(r => setTimeout(r, 2000));
		this.loadingComponent.hide();
		this.addMultipleCards(10);
		//show data
	}

	//
	override ngAfterViewInit(): void {
		this.fetchParkingData();
	}

}
