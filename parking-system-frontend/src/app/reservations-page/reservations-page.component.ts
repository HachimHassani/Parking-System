import { AfterViewInit, Component, OnDestroy, ViewChild } from '@angular/core';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { ReservationCardComponent, ReservationData, ReservationState } from '../reservation-card/reservation-card.component';
import { LoadingComponent } from '../loading/loading.component';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';

@Component({
	selector: 'app-reservations-page',
	templateUrl: './reservations-page.component.html',
	styleUrls: ['./reservations-page.component.css']
})
export class ReservationsPageComponent extends ProgressiveCardsLoaderComponent<ReservationCardComponent, ReservationData> implements AfterViewInit, OnDestroy{
	@ViewChild(LoadingComponent) loadingComponenet!: LoadingComponent;

	constructor(private http: HttpClient, private cookieService: CookieService) {
		super([], ReservationCardComponent);
	}

	subscription: Subscription | undefined;

	async fetchReservations() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponenet.show();
		//get user token
		const token = this.cookieService.get('token');
		//fetch data from api
		this.subscription = this.http.get<Array<any>>('api/reservations', {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			//print data
			console.log(res);
			//convert data
			let reservationList: Array<ReservationData> = [];
			res.forEach(data => {
				//get time
				const currentTime = Date.now();
				const from = (new Date(data.from)).getTime();
				const to = (new Date(data.to)).getTime();
				//set data
				let reservationData: ReservationData = {
					parkingNumber: parseInt(data.spaceName.split(" ")[1]),
					state: ReservationState.FINISHED,
					from: from,
					to: to
				};
				//get current state
				if (currentTime < from) {
					reservationData.state = ReservationState.RESERVED;
				}
				else if (currentTime < to) {
					reservationData.state = ReservationState.AVAILABLE;
				}
				console.log(currentTime, to);
				//push data
				reservationList.push(reservationData);
			});
			//set data
			this.setData(reservationList);
			this.loadingComponenet.hide();
			this.addMultipleCards(10);
		});
		this.loadingComponenet.hide();
		this.addMultipleCards(10);
	}

	override ngAfterViewInit(): void {
		this.fetchReservations();
	}

	ngOnDestroy(): void {
		this.subscription?.unsubscribe();
	}
}
