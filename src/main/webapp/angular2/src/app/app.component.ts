import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    roomsearch: FormGroup;
    submtted: boolean;
    rooms: Room[];

    ngOnInit(): void {
        this.roomsearch = new FormGroup({
            checkin: new FormControl(''),
            checkout: new FormControl('')
        });
        this.rooms = this.ROOMS;
        console.log(this.rooms);
    }

    onSubmit({value, valid} : {value: Roomsearch, valid: boolean}) {
        console.log(value);
    }

    reserveRoom(value : string) {
        console.log(value);
    }

    ROOMS : Room[] = [
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
    ]




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
