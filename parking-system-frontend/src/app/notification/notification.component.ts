import { Component, OnInit } from '@angular/core';

export type NotificationType = "error" | "warning" | "correct";

@Component({
	selector: 'app-notification',
	templateUrl: './notification.component.html',
	styleUrls: ['./notification.component.css']
})
export class NotificationComponent {
	//data
	message = "";
	type: NotificationType = "warning";

	//visibility
	visible = false;

	//show
	async show(_type: NotificationType, _message: string, _duration: number) {
		//set data
		this.type = _type;
		this.message = _message;
		//wait a split second for the hide effect
		await new Promise(r => setTimeout(r, 10));
		//show
		this.visible = true;
		//wait time
		await new Promise(r => setTimeout(r, _duration * 1000));
		//hide
		this.visible = false;
		//wait for hide animation to finish about 400 ms
		await new Promise(r => setTimeout(r, 1100));

	}
}
