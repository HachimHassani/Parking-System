import { CSP_NONCE, Component, OnDestroy, OnInit } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { Subscribable, Subscription, timer } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';


export enum ReservationState {
	RESERVED,
	AVAILABLE,
	FINISHED
}

export interface ReservationData {
	parkingNumber: number,
	parkingId: string,
	parkingName?: string,
	parkingCity?: string,
	state: ReservationState,
	to: number,
	from: number
}

@Component({
	selector: 'app-reservation-card',
	templateUrl: './reservation-card.component.html',
	styleUrls: ['./reservation-card.component.css']
})
export class ReservationCardComponent extends CardComponent<ReservationData> implements OnInit, OnDestroy{
	
	constructor(private http: HttpClient, private cookieService: CookieService) {
		super();
	}

	private stateString = [
		"Before Available",
		"Before Finished",
		"After Finished"
	]
	getStateName() {
		if (!this.data?.state)
			return "Before Available";
		return this.stateString[this.data?.state];
	}


	time = '0 : 0 : 0';
	timerSubscription: Subscription | undefined;

	getTimerValue() {
		if (!this.data?.to){
			return;
		}
		let timer = 0;
		if (this.data.state == ReservationState.RESERVED) {
			timer = this.data.from - Date.now()
		}
		else {
			timer = this.data.to - Date.now()
		}
		

		const hours = Math.floor(timer / (1000 * 60 * 60));
		timer = timer % (1000 * 60 * 60);
		const minutes = Math.abs(Math.floor(timer / (1000 * 60)));
		timer = timer % (1000 * 60);
		const seconds = Math.abs(Math.floor(timer / 1000));
		this.time = `${hours} : ${minutes} : ${seconds}`;
	}

	//on init
	ngOnInit(): void {
		//this.parkingNumber = Math.floor(Math.random() * 100);
		this.timerSubscription = timer(100, 100).subscribe(() => {
			this.getTimerValue();
		});
	}


	ngOnDestroy(): void {
		this.timerSubscription?.unsubscribe();
	}
}
