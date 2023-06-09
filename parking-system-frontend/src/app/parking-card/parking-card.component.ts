import { Component, OnDestroy } from '@angular/core';
import { CardComponent } from '../card/card.component';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';

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
export class ParkingCardComponent  extends CardComponent<ParkingCardData> implements OnDestroy{
	//collection
	isCollectionButtonAnimated = false;
	collectionSubscription : Subscription | undefined;

	constructor (private http: HttpClient, private cookieService: CookieService) {
		super();
	}

	//click animation
	async collectionClickAninmate() {
		this.isCollectionButtonAnimated = true;
		await new Promise(r => setTimeout(r, 200 ));
		this.isCollectionButtonAnimated = false;
	}

	//on collection clikc
	onCollectionClick() {
		if (!this.data)
			return;
		this.data.inCollection = !this.data.inCollection;
		this.collectionClickAninmate();
		//get user id
		const userId = this.cookieService.get('id');
		//get token
		const token = this.cookieService.get('token');
		
		if (this.data.inCollection) {
			//body
			const body = {};
			//add to favorite
			this.collectionSubscription = this.http.post(`/api/users/favourites/${this.data.id}`, body, {
				headers: {
					'Authorization': `Bearer ${token}`
				}
			}).subscribe();
		}
		else {
			//body
			const body = {};
			//add to favorite
			this.collectionSubscription = this.http.delete(`/api/users/favourites/${this.data.id}`, {
				headers: {
					'Authorization': `Bearer ${token}`
				}
			}).subscribe();
		}
		
	}

	ngOnDestroy(): void {
		this.collectionSubscription?.unsubscribe();
	}
}
