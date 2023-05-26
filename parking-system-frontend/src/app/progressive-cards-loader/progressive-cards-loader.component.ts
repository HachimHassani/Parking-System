import { AfterViewInit, Component, ComponentRef, HostListener, Type, ViewChild, ViewContainerRef } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { take } from 'rxjs';
import { PaymentCardComponent } from '../payment-card/payment-card.component';
import { number } from 'echarts';
import { Data } from '@angular/router';

export type CardType = CardComponent<DataType>;
export type DataType = any;

@Component({
  	template: ''
})
export abstract class ProgressiveCardsLoaderComponent <CardType, DataType> implements AfterViewInit{
	@ViewChild("cardsContainer", { read: ViewContainerRef }) container!: ViewContainerRef;
	//constuctor
	constructor(private data: Array<DataType>, private componentType: Type<CardType>) {}

	//data
	setData(_data: Array<any>) {
		this.data = _data;
	}
	//number of payments created 
	numberOfCards = 0
	//payments cards
	cards: ComponentRef<CardType>[] = [];
	//create payments card
	addCard() {
		let card = this.container.createComponent<CardType>(this.componentType);
		this.cards.push(card);
		this.numberOfCards++;
		return card;
	}


	//create multiple cards
	async addMultipleCards(count: number) {
		const realCount = Math.min(count, this.data.length - this.numberOfCards);
		//create the cards
		for (let i = 0; i < realCount; i++) {
			let card = this.addCard();
		}
		//animate the cards
		//wait for 100 ms
		for (let i = 0; i < realCount; i++) {
			let card = this.cards[i + this.cards.length - realCount];
			(card.instance as CardComponent<DataType>).data = this.data[i + this.cards.length - realCount];
			await new Promise(r => setTimeout(r, 100));
			(card.instance as CardComponent<DataType>).show();
		}
	}

	//delete cards
	deleteAllCards() {
		this.numberOfCards = 0;
		this.cards.forEach(card => {
			card.destroy();
		});
		this.cards = [];
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
