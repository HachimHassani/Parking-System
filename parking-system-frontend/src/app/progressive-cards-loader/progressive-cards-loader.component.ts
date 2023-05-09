import { Component, ComponentRef, HostListener, Type, ViewChild, ViewContainerRef } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { take } from 'rxjs';
import { PaymentCardComponent } from '../payment-card/payment-card.component';

export type CardType = PaymentCardComponent;

@Component({
  	template: ''
})
export class ProgressiveCardsLoaderComponent <CardType> {
	@ViewChild("cardsContainer", { read: ViewContainerRef }) container!: ViewContainerRef;
	//constuctor
	constructor(private data: Array<any>, private componentType: Type<CardType>) {}

	//data
	//number of payments created 
	cardCreateNumber = 7
	//payments cards
	cards: ComponentRef<CardType>[] = [];
	//create payments card
	addCard() {
		let card = this.container.createComponent<CardType>(this.componentType);
		this.cards.push(card);
		return card;
	}

	//create multiple cards
	addMultipleCards(count: number) {
		for (let i = 0; i < count; i++) {
			let card = this.addCard();
			//show
			setTimeout(() => {
				(card.instance as CardComponent).show();
			}, (i + 1) * 100);
		}
	}


	//on after view init
	ngAfterViewInit(): void {
		this.addMultipleCards(this.cardCreateNumber);
		
	}

	//on scroll create other cards
	@HostListener('window:scroll', ['$event'])
	onWindowScroll($event: Event) {
		if (this.cards.length == 0)
			return;

		let limit = document.body.offsetHeight - window.innerHeight;
		if (window.scrollY >= limit) {
			this.addMultipleCards(10);
		}
	}
}
