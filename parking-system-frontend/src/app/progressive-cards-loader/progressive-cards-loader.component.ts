import { AfterViewInit, Component, ComponentRef, HostListener, Type, ViewChild, ViewContainerRef } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { take } from 'rxjs';
import { PaymentCardComponent } from '../payment-card/payment-card.component';

export type CardType = CardComponent;

@Component({
  	template: ''
})
export abstract class ProgressiveCardsLoaderComponent <CardType> implements AfterViewInit{
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
	async addMultipleCards(count: number) {
		//create the cards
		for (let i = 0; i < count; i++) {
			let card = this.addCard();
		}
		//animate the cards
		//wait for 100 ms
		for (let i = 0; i < count; i++) {
			let card = this.cards[i + this.cards.length - count];
			await new Promise(r => setTimeout(r, 100));
			(card.instance as CardComponent).show();
		}
	}

	//delete cards
	deleteAllCards() {
		this.cards.forEach(card => {
			card.destroy();
		});
	}

	ngAfterViewInit(): void {
		 
	}


	//on scroll create other cards
	@HostListener('window:scroll', ['$event'])
	onWindowScroll($event: Event) {
		if (this.cards.length == 0)
			return;

		let limit = document.body.offsetHeight - window.innerHeight;
		if (window.scrollY >= limit) {
			this.addMultipleCards(1);
		}
	}
}
