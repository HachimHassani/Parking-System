import { Component } from '@angular/core';
import { CardComponent } from '../card/card.component';

@Component({
	selector: 'app-parking-card',
	templateUrl: './parking-card.component.html',
	styleUrls: ['./parking-card.component.css']
})
export class ParkingCardComponent  extends CardComponent{
	//data
	inCollection = false;
	isCollectionButtonAnimated = false;

	//click animation
	async collectionClickAninmate() {
		this.isCollectionButtonAnimated = true;
		await new Promise(r => setTimeout(r, 200 ));
		this.isCollectionButtonAnimated = false;
	}

	//on collection clikc
	onCollectionClick() {
		this.inCollection = !this.inCollection;
		this.collectionClickAninmate();
	}

}
