import { Component } from '@angular/core';

@Component({
	selector: 'app-loading',
	templateUrl: './loading.component.html',
	styleUrls: ['./loading.component.css']
})
export class LoadingComponent {
	isLoading = false;

	//show /hide
	show() {
		this.isLoading = true;
	}
	hide() {
		this.isLoading = false;
	}
}
