import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { EChartsOption, SeriesOption, color } from 'echarts';
import { LoadingComponent } from '../loading/loading.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
	selector: 'app-parking-info',
	templateUrl: './parking-info.component.html',
	styleUrls: ['./parking-info.component.css']
})
export class ParkingInfoComponent implements OnInit, AfterViewInit {
	@ViewChild(LoadingComponent) loadingComponent!: LoadingComponent;

	constructor(private route: ActivatedRoute ){}

	//data
	id = "";
	name = "Parking's name";
	city = "Marrakech";
	cost = 32;
	reserved = 0;
	places = 0;
	reservationPerDay = Array<number>(8).fill(0);

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

	//fetch data
	async fetchParkingData() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingComponent.show();
		//artifical wait for testing
		await new Promise(r => setTimeout(r, 2000));
		//set data
		this.setParkingPlaces(79, 100);
		this.setReservationPerDay([24, 32, 80, 60, 13, 42, 8]);
		//end loading
		this.isLoading = false;
		this.loadingComponent.hide();
	}

	//on init
	ngOnInit(): void {
		//get id
		this.route.params.subscribe(params => {
			this.id = params['parkingId'];
			console.log(this.id);
		});
	}
	
	//
	ngAfterViewInit(): void {
		this.fetchParkingData();
	}

}
