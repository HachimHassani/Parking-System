import { AfterViewInit, CUSTOM_ELEMENTS_SCHEMA, Component, NgModule, ViewChild } from '@angular/core';
import { ApexAxisChartSeries, ApexChart, ApexPlotOptions, ApexXAxis, ChartComponent } from 'ng-apexcharts';
import { LoadingComponent } from '../loading/loading.component';

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
export class ReservationAddComponent implements AfterViewInit {
	@ViewChild(LoadingComponent) loadingCompoenent!: LoadingComponent;
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

	//data
	reservationTime = {
		from: Date.now(),
		to: Date.now()
	};
	name = '';
	city = '';
	cost = 10;
	places = 100;


	onFromInputChange(from: number| null) {
		console.log(from);
		if (from) {
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

	//fetch data
	async fetchParkingData() {
		await new Promise(r => setTimeout(r, 0));
		this.loadingCompoenent.show();
		//fetch data
		await new Promise(r => setTimeout(r, 1000));
		this.name = "Parking's name";
		this.city = "Marrakech";
		//end loading
		this.isLoading = false;
		this.loadingCompoenent.hide();
	}

	ngAfterViewInit(): void {
		this.fetchParkingData();
	}

}
