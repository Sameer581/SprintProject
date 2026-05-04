export interface ErrorResponse {
  errormsg: string;
  timeStamp: string;
  status: string;
  errMap?: { [field: string]: string[] };
}

