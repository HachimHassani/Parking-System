import { AfterViewInit, Component, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ParkingCardComponent } from '../parking-card/parking-card.component';
import { delay } from 'rxjs';

@Component({
	selector: 'app-parkings-page',
	templateUrl: './parkings-page.component.html',
	styleUrls: ['./parkings-page.component.css']
})
export class ParkingsPageComponent implements OnInit, AfterViewInit{
	@ViewChild("parkingCardsContainer", { read: ViewContainerRef }) container!: ViewContainerRef;
	//constuctor
	constructor() {}

	//data
	//temp parking data
	parkings = Array(32).fill(0);
	//number of parkings created 
	parkingsCardCreateNumber = 7
	//parking cards
	parkingsCards :ParkingCardComponent[] = [];
	//create parking card
	addParkingCard() {
		let parking = this.container.createComponent<ParkingCardComponent>(ParkingCardComponent);
		this.parkingsCards.push(parking.instance);
		return parking.instance;
		
	}

	//create multiple cards
	addMultipleCards(count: number) {
		for (let i = 0; i < count; i++) {
			let parking = this.addParkingCard();
			setTimeout(() => {
				parking?.show();
			}, i * 100);
		}
	}

	//on init
	ngOnInit() {
	}
	
	//on after view init
	ngAfterViewInit(): void {
		setTimeout(() => {
			this.addMultipleCards(this.parkingsCardCreateNumber);
		}, 10);
	}

	//on scroll create other cards
	@HostListener('window:scroll', ['$event'])
	onWindowScroll($event: Event) {
		if (this.parkingsCards.length == 0)
			return;

		let limit = document.body.offsetHeight - window.innerHeight + 64;
		if (window.scrollY >= limit) {
			this.addMultipleCards(10);
		}
	}

}
