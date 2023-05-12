import { AfterViewInit, Component, ComponentRef, HostListener, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { PaymentCardComponent } from '../payment-card/payment-card.component';
import { ProgressiveCardsLoaderComponent } from '../progressive-cards-loader/progressive-cards-loader.component';
import { take } from 'rxjs';
import { LoadingComponent } from '../loading/loading.component';

@Component({
	selector: 'app-payments-page',
	templateUrl: './payments-page.component.html',
	styleUrls: ['./payments-page.component.css']
})
export class PaymentsPageComponent extends ProgressiveCardsLoaderComponent<PaymentCardComponent> implements OnInit, AfterViewInit{
	@ViewChild(LoadingComponent) loadingComponent!: LoadingComponent;
	//constuctor
	constructor() {
		super([], PaymentCardComponent);
	}

	//create payments card
	override addCard() {
		let payment = super.addCard();
		payment.instance.deleteCard
		.pipe(take(1))
		.subscribe(event  => {
			this.onDeleteCard(event, payment);
		})
		return payment;

	}

	//on delete cards
	onDeleteCard(event: Event, _payment: ComponentRef<PaymentCardComponent>) {
		_payment.destroy();
		const index = this.cards.indexOf(_payment);
		if (index >= 0) {
			this.cards.splice(index, 1);
			//check length
			if (this.cards.length < 5) {
				this.addMultipleCards(5);
			};
		}
	};

	//on init
	ngOnInit() {
	}

	async fetchPayments() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponent.show();
		await new Promise(r => setTimeout(r, 1000));
		this.loadingComponent.hide();
		this.addMultipleCards(10);
	}

	override ngAfterViewInit(): void {
		this.fetchPayments();
	}

}
