import { Component, EventEmitter, Output } from '@angular/core';

@Component({
	selector: 'app-payment-card',
	templateUrl: './payment-card.component.html',
	styleUrls: ['./payment-card.component.css']
})
export class PaymentCardComponent {
	visible = false;
	id = 0;

	show() {
		this.visible = true;
	}

	hide() {
		this.visible = false;
	}

	onDeleteClicked() {
		this.hide();
		setTimeout(() => {
			this.deleteCard.emit({
				id: this.id
			});
		}, 1000);
	}

	@Output() deleteCard = new EventEmitter();

}
