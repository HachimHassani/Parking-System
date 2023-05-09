import { AfterViewInit, Component, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { ParkingCardComponent } from '../parking-card/parking-card.component';
import { delay } from 'rxjs';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';

@Component({
	selector: 'app-parkings-page',
	templateUrl: './parkings-page.component.html',
	styleUrls: ['./parkings-page.component.css']
})
export class ParkingsPageComponent extends ProgressiveCardsLoaderComponent<ParkingCardComponent> implements OnInit, AfterViewInit{
	//constuctor
	constructor() {
		super([], ParkingCardComponent);
	}

	//on init
	ngOnInit() {
		
	}

}
