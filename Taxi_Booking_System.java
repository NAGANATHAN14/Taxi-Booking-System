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

    
