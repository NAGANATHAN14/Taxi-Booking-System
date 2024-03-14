/*Design a Call taxi booking application -There are n number of taxi’s. For simplicity, assume 4.
 But it should work for any number of taxi’s. 
 -The are 6 points(A,B,C,D,E,F) -All the points are in a straight line, and each point is 15kms away from the adjacent points. 
 -It takes 60 mins to travel from one point to another
 -Each taxi charges Rs.100 minimum for the first 5 kilometers and Rs.10 for the subsequent kilometers. 
 -For simplicity, time can be entered as absolute time. Eg: 9hrs, 15hrs etc. -All taxi’s are initially stationed at A. 
 -When a customer books a Taxi, a free taxi at that point is allocated 
 -If no free taxi is available at that point, a free taxi at the nearest point is allocated. 
 -If two taxi’s are free at the same point, one with lower earning is allocated 
 -Note that the taxi only charges the customer from the pickup point to the drop point. Not the distance it travels from an adjacent point to pickup the customer. 
 -If no taxi is free at that time, booking is rejected Design modules for
1) Call taxi booking
Input 1:
Customer ID: 1
Pickup Point: A
Drop Point: B
Pickup Time: 9

Output 1:
Taxi can be allotted.
Taxi-1 is allotted

Input 2:
Customer ID: 2
Pickup Point: B
Drop Point: D
Pickup Time: 9

Output 1:
Taxi can be allotted.
Taxi-2 is allotted
(Note: Since Taxi-1 would have completed its journey when second booking is done, so Taxi-2 from nearest point A which is free is allocated)

Input 3:
Customer ID: 3
Pickup Point: B
Drop Point: C
Pickup Time: 12

Output 1:
Taxi can be allotted.
Taxi-1 is allotted
2) Display the Taxi details

Taxi No:    Total Earnings:
BookingID    CustomerID    From    To    PickupTime    DropTime    Amount

Output:
Taxi-1    Total Earnings: Rs. 400

1     1     A    B    9    10    200
3    3    B    C    12    13    200

Taxi-2 Total Earnings: Rs. 350
2    2    B    D    9    11    350 */

import java.util.*;

class Taxi {
    int Taxi_id;
    char currentPoint;
    int totalEarnings;
    int endTime;

    public Taxi(int id){

        this.Taxi_id = id;
        this.currentPoint = 'A';
        this.totalEarnings = 0;
        this.endTime = 0;

    }

}

class Booking{
    int booking_id;
    char pickupPoint;
    char dropPoint;
    int pickupTime;
    Taxi taxi;

    public Booking(int id, char pickupPoint, char dropPoint, int pickupTime){
        this.booking_id = id;
        this.pickupPoint = pickupPoint;
        this.dropPoint = dropPoint;
        this.pickupTime = pickupTime;
    }
}

class TaxiBookingSystem{
    List<Taxi> taxis = new ArrayList<>();
    List<Booking> bookings = new ArrayList<>();

    public TaxiBookingSystem(int numTaxis)
    {
        this.taxis = new ArrayList<>();
        this.bookings = new ArrayList<>();
        for(int i = 0; i <= numTaxis; i++){
            this.taxis.add(new Taxi(i));
        }
    }

    public String Book_taxi(int id, char pickupPoint, char dropPoint, int pickupTime){
       
       List<Taxi> availaTaxis = new ArrayList<>();

       for( Taxi taxi : taxis)
       {
        if (taxi.endTime <= pickupTime) availaTaxis.add(taxi);
       }
       
       if( availaTaxis.isEmpty()) return "Taxis not available";

       availaTaxis.sort((a,b) ->{
        int diff = Math.abs(a.currentPoint - pickupPoint) - Math.abs(b.currentPoint - pickupPoint);
        if( diff == 0) return a.totalEarnings - b.totalEarnings;
        return diff;
       });

       Taxi ChosenTaxi = availaTaxis.get(0);
       int travelTime = Math.abs(pickupPoint - dropPoint);
       int travelCost = 100 + Math.max(0, (travelTime * 15 - 5)*10);

       ChosenTaxi.totalEarnings += travelCost;
       ChosenTaxi.currentPoint = dropPoint;
       ChosenTaxi.endTime = pickupTime + travelTime;

       Booking booking = new Booking(id, pickupPoint, dropPoint, pickupTime);
       booking.taxi = ChosenTaxi;
       bookings.add(booking);

        return "Taxi can be allotted.\nTaxi-" + (ChosenTaxi.Taxi_id + 1) + " is allotted";
    }
    public void displayDetails() {
        for (Taxi taxi : taxis) {
            System.out.println("Taxi-" + (taxi.Taxi_id + 1) + " Total Earnings: Rs. " + taxi.totalEarnings);
            for (Booking booking : bookings) {
                if (booking.taxi == taxi) {
                    System.out.println(booking.booking_id + " " + booking.pickupPoint + " " + booking.dropPoint + " " + booking.pickupTime + " " + (booking.pickupTime + Math.abs(booking.pickupPoint - booking.dropPoint)) + " " + (100 + Math.max(0, (Math.abs(booking.pickupPoint - booking.dropPoint) * 15 - 5) * 10)));
                }
            }
        }
    }
    public static void main (String[] args){
        TaxiBookingSystem taxiService = new TaxiBookingSystem(4);
    
        System.out.println(taxiService.Book_taxi(1, 'A', 'B', 9));
        System.out.println(taxiService.Book_taxi(2, 'B', 'D', 9));
        System.out.println(taxiService.Book_taxi(3, 'B', 'C', 12));
    
        taxiService.displayDetails();
    }
}

    