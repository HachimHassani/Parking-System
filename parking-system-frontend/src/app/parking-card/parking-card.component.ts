import { Component } from '@angular/core';

@Component({
	selector: 'app-parking-card',
	templateUrl: './parking-card.component.html',
	styleUrls: ['./parking-card.component.css']
})
export class ParkingCardComponent {
	visible = false;

	//show
	show() {
		this.visible = true;
	}
}
