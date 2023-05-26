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
	parkingSubscription: Subscription | undefined;

	parkinglots :any= {};

	async fetchParkinglots() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponenet.show();
		//get user token
		const token = this.cookieService.get('token');
		this.parkingSubscription = this.http.get<Array<any>>('api/parking-lots', {
		//fetch data from api
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			//print data
			res.forEach((data: any) => {
				this.parkinglots[data.id] = {
					name: data.name,
					city: data.name
				}
			});
			console.log(this.parkinglots);
			//fetch reservations
			this.fetchReservations();
		});
	}

	fetchReservations() {
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
					parkingId: data.spaceId,
					from: from,
					to: to
				};
				//parking info
				if (reservationData.parkingId) {
					reservationData.parkingName = this.parkinglots[reservationData.parkingId]?.name;
					reservationData.parkingCity = this.parkinglots[reservationData.parkingId]?.city;
				}
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
		this.fetchParkinglots();
	}

	ngOnDestroy(): void {
		this.parkingSubscription?.unsubscribe();
		this.subscription?.unsubscribe();
	}
}
