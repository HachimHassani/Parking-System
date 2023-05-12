import { Component } from '@angular/core';
import { CardComponent } from '../card/card.component';

export interface ParkingCardData {
	id: string,
	name: string,
	city: string,
	inCollection: boolean, 
}


@Component({
	selector: 'app-parking-card',
	templateUrl: './parking-card.component.html',
	styleUrls: ['./parking-card.component.css']
})
export class ParkingCardComponent  extends CardComponent{
	//data
	data: ParkingCardData = {
		id: "parkingid",
		name: "Parking's name",
		city: "Parking's city",
		inCollection: false
	}
	//collection
	isCollectionButtonAnimated = false;

	//click animation
	async collectionClickAninmate() {
		this.isCollectionButtonAnimated = true;
		await new Promise(r => setTimeout(r, 200 ));
		this.isCollectionButtonAnimated = false;
	}

	//on collection clikc
	onCollectionClick() {
		this.data.inCollection = !this.data.inCollection;
		this.collectionClickAninmate();
	}

}
