import { Moment } from 'moment';

export interface IDateCalendar {
    id: number;
    title: string;
    start: Moment;
    end: string;
    url: string;
}

export class DateCalendar {
    constructor(public id: number, public title: string, public start: string, public end?: string, public url?: string) {}
}
