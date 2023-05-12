import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { ReservationCardComponent } from '../reservation-card/reservation-card.component';
import { LoadingComponent } from '../loading/loading.component';

@Component({
	selector: 'app-reservations-page',
	templateUrl: './reservations-page.component.html',
	styleUrls: ['./reservations-page.component.css']
})
export class ReservationsPageComponent extends ProgressiveCardsLoaderComponent<ReservationCardComponent> implements AfterViewInit{
	@ViewChild(LoadingComponent) loadingComponenet!: LoadingComponent;


	constructor() {
		super([], ReservationCardComponent);
	}

	async fetchReservations() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponenet.show();
		await new Promise(r => setTimeout(r, 1000));
		this.loadingComponenet.hide();
		this.addMultipleCards(10);
	}

	override ngAfterViewInit(): void {
		this.fetchReservations();
	}

}
