import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { EChartsOption, SeriesOption, color } from 'echarts';
import { LoadingComponent } from '../loading/loading.component';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';

@Component({
	selector: 'app-parking-info',
	templateUrl: './parking-info.component.html',
	styleUrls: ['./parking-info.component.css']
})
export class ParkingInfoComponent implements OnInit, AfterViewInit, OnDestroy {
	@ViewChild(LoadingComponent) loadingComponent!: LoadingComponent;

	constructor(private route: ActivatedRoute, private http: HttpClient, private cookieService: CookieService ){}

	//data
	id = "";
	name = "Parking's name";
	city = "Marrakech";
	cost = 32;
	reserved = 0;
	places = 0;
	reservationPerDay = Array<number>(8).fill(0);

	//subscriptions
	parkingInfoSubscription: Subscription | undefined;
	reservationPerWeekSubscription: Subscription | undefined;

	//loading
	isLoading = true;
	parkingInfoVisibility() {
		return this.isLoading ? 'hidden' : 'visible'
	}

	//charts
	//parking place chart
	parkingPlaceChartOption: EChartsOption = {}
	//set parking place chart
	setParkingPlaceChart(_data: any) {
		this.parkingPlaceChartOption =  {
			tooltip: {
				trigger: 'item'
			},
			legend: {
				bottom: '5%',
				left: 'center'
			},
			series: {
				name: 'Parking places',
				type: 'pie',
				radius: ['40%', '70%'],
				avoidLabelOverlap: false,
				itemStyle: {
					borderRadius: 10,
					borderColor: '#000',
					borderWidth: 2
				},
				label: {
					show: false,
					position: 'center'
				},
				labelLine: {
					show: false
				},
				color: [
					'#000',
					'#fff'
				],
				data: _data
			}
		}
	}

	//reservation chart
	reservationChartOption: EChartsOption = {}
	//set reservation chart
	setReservationChart(_data: any) {
		this.reservationChartOption = {
			xAxis: {
				type: 'category',
				data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
			},
			yAxis: {
				type: 'value'
			},
			color: '#000',
			series: [
				{
					data: _data,
					type: 'bar'
				}
			]
		}
	}

	//set data
	setParkingPlaces(_reserved: number, _places: number) {
		//set data
		this.reserved = _reserved;
		this.places = _places;
		//set chart
		this.setParkingPlaceChart([
			{ value: this.reserved, name: 'Reserved' },
			{ value: this.places - this.reserved, name: 'Free' }
		]);
	}

	//set reservation per day
	setReservationPerDay(_reservations: Array<number>) {
		this.reservationPerDay = _reservations;
		this.setReservationChart(this.reservationPerDay);
	}

	//fetch reservation per week
	fetchReservationPerWeek() {
		//get user token
		const token = this.cookieService.get('token');
		//fetch data from api
		this.reservationPerWeekSubscription = this.http.get<Array<any>>(`api/parking-lots/${this.id}/weekly-reservations`, {
			headers: {
				'Authorization': `Bearer ${token}`
			}
		}).subscribe((res: Array<any>) => {
			let reservationPerDay: Array<number> = [];
			res.forEach(day => {
				console.log(day);
				reservationPerDay.push(
					day.parkingLotReservations.length
				);
			});
			this.setReservationPerDay(reservationPerDay);
		});
	}

	//fetch data
	async fetchParkingData() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponent.show();
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
			this.city = res.city;
			this.cost = res.parkingFee;
			this.places = res.capacity;
			this.setParkingPlaces(res.capacity, this.places - res.availableSpaces);
			this.loadingComponent.hide();
			this.isLoading = false;
		});
		this.setReservationPerDay([0, 0, 0, 0, 0, 0, 0]);
		//get weekly data
		this.fetchReservationPerWeek();	
		
	}

	//on init
	ngOnInit(): void {
		//get id
		this.route.params.subscribe(params => {
			this.id = params['parkingId'];
			console.log(this.id);
		});
	}
	
	//after view init
	ngAfterViewInit(): void {
		this.fetchParkingData();
	}

	//on destroy
	ngOnDestroy(): void {
		this.parkingInfoSubscription?.unsubscribe();
		this.reservationPerWeekSubscription?.unsubscribe();
	}
}
