import { AfterViewInit, Component } from '@angular/core';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { ReservationCardComponent } from '../reservation-card/reservation-card.component';

@Component({
	selector: 'app-reservations-page',
	templateUrl: './reservations-page.component.html',
	styleUrls: ['./reservations-page.component.css']
})
export class ReservationsPageComponent extends ProgressiveCardsLoaderComponent<ReservationCardComponent> implements AfterViewInit{

	constructor() {
		super([], ReservationCardComponent);
	}

	override ngAfterViewInit(): void {
		
	}

}
