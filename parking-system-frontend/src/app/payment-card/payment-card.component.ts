import { Component, EventEmitter, Output } from '@angular/core';
import { CardComponent } from '../card/card.component';

export type PaymentData = any;

@Component({
	selector: 'app-payment-card',
	templateUrl: './payment-card.component.html',
	styleUrls: ['./payment-card.component.css']
})
export class PaymentCardComponent extends CardComponent<PaymentData> {
	id = 0

	onDeleteClicked() {
		this.hide();
		setTimeout(() => {
			this.deleteCard.emit();
		}, 1000);
	}

	@Output() deleteCard = new EventEmitter();

}
