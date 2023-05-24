import { Component, OnInit } from '@angular/core';
import { CardComponent } from '../card/card.component';

export type ReservationData = any;

@Component({
	selector: 'app-reservation-card',
	templateUrl: './reservation-card.component.html',
	styleUrls: ['./reservation-card.component.css']
})
export class ReservationCardComponent extends CardComponent<ReservationData> implements OnInit{
	//numero
	parkingNumber = 0;
	//on init
	ngOnInit(): void {
		this.parkingNumber = Math.floor(Math.random() * 100);
	}
}
