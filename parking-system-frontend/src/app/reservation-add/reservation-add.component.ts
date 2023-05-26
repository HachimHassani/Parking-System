import { AfterViewInit, CUSTOM_ELEMENTS_SCHEMA, Component, NgModule, OnInit, ViewChild } from '@angular/core';
import { ApexAxisChartSeries, ApexChart, ApexPlotOptions, ApexXAxis, ChartComponent } from 'ng-apexcharts';
import { LoadingComponent } from '../loading/loading.component';
import { Subscription } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { NotificationComponent } from '../notification/notification.component';

export type ChartOptions = {
	series: ApexAxisChartSeries;
	chart: ApexChart;
	xaxis: ApexXAxis;
	plotOptions: ApexPlotOptions;
};


@Component({
	selector: 'app-reservation-add',
	templateUrl: './reservation-add.component.html',
	styleUrls: ['./reservation-add.component.css'],
	
})
export class ReservationAddComponent implements OnInit, AfterViewInit {
	@ViewChild(LoadingComponent) loadingCompoenent!: LoadingComponent;
	@ViewChild(NotificationComponent) notificationComponent!: NotificationComponent;
	/*availableTimelineOptions  :ChartOptions= {
		series: [
			{
				data: [
					{
						x: "Parking Available",
						y: [
							new Date("2019-03-02").getTime(),
							new Date("2019-03-04").getTime()
						],
						fillColor: '#f00'
					},
					{
						x: "Parking Available",
						y: [
							new Date("2019-03-09").getTime(),
							new Date("2019-03-30").getTime()
						],
						fillColor: '#f00'
					},
					{
						x: "My Reservation",
						y: [
							new Date("2019-02-04").getTime(),
							new Date("2019-03-08").getTime()
						]
					},
				]
			}
		],
		chart: {
			height: 350,
			type: "rangeBar",
			toolbar: {
				show: false
			}
		},
		plotOptions: {
			bar: {
				horizontal: true
			}
		},
		xaxis: {
			type: "datetime"
		}
	};*/

	constructor(private http: HttpClient, private cookieService: CookieService, private route: ActivatedRoute, private router: Router) {}

	//data
	reservationTime = {
		from: Date.now(),
		to: Date.now()
	};
	id = '';
	name = '';
	city = '';
	cost = 10;
	places = 100;

	parkingInfoSubscription: Subscription | undefined;

	ngOnInit(): void {

	}

	onFromInputChange(from: number| null) {
		if (from) {
			console.log((new Date(from)).toISOString());
			this.reservationTime.from = from;
			if (from > this.reservationTime.to) {
				this.reservationTime.to = from
			} 
		}
			
	}

	onToInputChange(to: number | null) {
		if (to != null)
			this.reservationTime.to = to;
	}

	getPrice() {
		const hoursToMilliSec = 60 * 60 * 1000;
		return ( this.reservationTime.to - this.reservationTime.from ) * this.cost / hoursToMilliSec;
	}

	//loading 
	isLoading = true;

	//on order click
	onOrderClick() {
		//start loading
		this.loadingCompoenent.show();
		//get user token
		const token = this.cookieService.get('token');
		//body
		const body = {
			parkinglotId: this.id,
			from: new Date(this.reservationTime.from).toISOString().split('.')[0],
			to: new Date(this.reservationTime.to).toISOString().split('.')[0]
		}
		console.log(body);
		//add reservations
		this.http.post('api/reservations', body, {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		},).subscribe((res: any) => {
			this.loadingCompoenent.hide();
			this.notificationComponent.show("correct", "reservation added", 3);
		});
	}

	//fetch data
	async fetchParkingData() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingCompoenent.show();
		//fetch data
		//get user token
		const token = this.cookieService.get('token');
		//fetch data from api
		this.parkingInfoSubscription = this.http.get(`api/parking-lots/${this.id}`, {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: any) => {
			console.log(res);
			this.name = res.name;
			this.city = res.city;
			this.cost = res.parkingFee;
			this.places = res.capacity;
			this.loadingCompoenent.hide();
			this.isLoading = false;
		});
		//end loading
		this.isLoading = false;
		this.loadingCompoenent.hide();
	}

	ngAfterViewInit(): void {
		//get id
		this.route.params.subscribe(params => {
			this.id = params['parkingId'];
			console.log(this.id);
			this.fetchParkingData();
		});
		
	}

}
