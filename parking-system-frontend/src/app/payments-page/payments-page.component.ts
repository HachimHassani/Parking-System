import { Component, ComponentRef, HostListener, ViewChild, ViewContainerRef } from '@angular/core';
import { PaymentCardComponent } from '../payment-card/payment-card.component';
import { take, takeUntil } from 'rxjs';

@Component({
	selector: 'app-payments-page',
	templateUrl: './payments-page.component.html',
	styleUrls: ['./payments-page.component.css']
})
export class PaymentsPageComponent {
	@ViewChild("paymentCardsContainer", { read: ViewContainerRef }) container!: ViewContainerRef;
	//constuctor
	constructor() { }

	//data
	//temp payments data
	payments = Array(32).fill(0);
	//number of payments created 
	paymentsCardCreateNumber = 7
	//payments cards
	paymentsCards: ComponentRef<PaymentCardComponent>[] = [];
	//create payments card
	addPaymentCard() {
		let payment = this.container.createComponent<PaymentCardComponent>(PaymentCardComponent);
		this.paymentsCards.push(payment);
		return payment;

	}

	//create multiple cards
	addMultipleCards(count: number) {
		for (let i = 0; i < count; i++) {
			let payment = this.addPaymentCard();
			//bind event
			payment.instance.deleteCard
				.pipe(take(1))
				.subscribe(event => { this.onDeleteCard(event, payment) });
			//show
			setTimeout(() => {
				payment?.instance.show();
			}, i * 100);
		}
	}

	//on delete cards
	onDeleteCard(event: Event, _payment: ComponentRef<PaymentCardComponent>) {
		_payment.destroy();
		const index = this.paymentsCards.indexOf(_payment);
		if (index >= 0) {
			this.paymentsCards.splice(index, 1);
			//check length
			if (this.paymentsCards.length < 5) {
				this.addMultipleCards(5);
			};
		}
	};

	//on init
	ngOnInit() {
	}

	//on after view init
	ngAfterViewInit(): void {
		setTimeout(() => {
			this.addMultipleCards(this.paymentsCardCreateNumber);
		}, 10);
	}

  	//on scroll create other cards
  	@HostListener('window:scroll', ['$event'])
  	onWindowScroll($event: Event) {
		if (this.paymentsCards.length == 0)
      		return;

		let limit = document.body.offsetHeight - window.innerHeight;
		if (window.scrollY >= limit) {
			this.addMultipleCards(10);
		}
  }
}
