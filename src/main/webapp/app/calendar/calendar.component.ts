import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: [
    'calendar.css'
  ]
})
export class CalendarComponent implements OnInit {

  message: string;

  constructor() {
    this.message = 'CalendarComponent message';
  }

  ngOnInit() {
  }

}
