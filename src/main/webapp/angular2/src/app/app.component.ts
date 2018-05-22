import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Http, RequestOptions, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {map} from 'rxjs/operator/map';
import "rxjs/add/operator/map";

// import 'rxjs/operator/map';
//import 'rxjs/operator/catch';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    baseUrl = 'http://localhost:8080';
    roomsearch: FormGroup;
    submtted: boolean;
    rooms: Room[];
    currentCheckInValue: string;
    currentCheckOutValue: string;
    // request: ReserveRoomRequest;

    constructor(private http: Http) {}

    ngOnInit(): void {
        this.roomsearch = new FormGroup({
            checkin: new FormControl(''),
            checkout: new FormControl('')
        });
        const roomsearchValueChanges$ = this.roomsearch.valueChanges;
        roomsearchValueChanges$.subscribe(valChange => {
           this.currentCheckInValue = valChange.checkin;
           this.currentCheckOutValue = valChange.checkout;

        });
        // this.rooms = this.ROOMS;
        // console.log(this.rooms);
    }

    onSubmit({value, valid} : {value: Roomsearch, valid: boolean}) {
        // console.log(value);
        this.getAll().subscribe(
            rooms => this.rooms = rooms,
            errors => console.log(errors)
        );
    }

    reserveRoom(value : string) {
        let request = new ReserveRoomRequest(value, this.currentCheckInValue, this.currentCheckOutValue);
        this.createReservation(request);
    }

    createReservation(body : ReserveRoomRequest) {
        let bodyString = JSON.stringify(body);
        let headers = new Headers({'Content-Type': 'application/json'});
        let option = new RequestOptions({headers: headers});
        this.http.post(this.baseUrl + "/room/reservation/v1", body, option)
            .subscribe(res => console.log(res));
    }

    getAll(): Observable<Room[]> {
        /*return this.http.get(this.baseUrl + "/room/reservation/v1?checkin=2017-03-18&checkout=2017-03-25")
            .map(this.mapRoom)*/
        return this.http.get(this.baseUrl + "/room/reservation/v1?checkin=" + this.currentCheckInValue
            + "&checkout=" + this.currentCheckOutValue)
            .map(this.mapRoom)

    }

    mapRoom(response: Response): Room[] {
        return response.json().content;
    }

    /*ROOMS : Room[] = [
        {
            id: "3123123",
            roomNumber: "409",
            price: "20",
            links: ""
        },
        {
            id: "874564654",
            roomNumber: "410",
            price: "25",
            links: ""
        },
        {
            id: "12313",
            roomNumber: "411",
            price: "28",
            links: ""
        }
    ]*/




}

export interface Room {
    id: string;
    roomNumber: string;
    price: string;
    links: string;
}

export interface Roomsearch {
    checkin: string;
    checkout: string;
}

export class ReserveRoomRequest {
    roomId: string;
    checkin: string;
    checkout: string;


    constructor(roomId: string, checkin: string, checkout: string) {
        this.roomId = roomId;
        this.checkin = checkin;
        this.checkout = checkout;
    }

}
