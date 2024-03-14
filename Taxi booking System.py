class Taxi :
    def __init__(self, Taxi_id):
        self.Taxi_id = Taxi_id
        self.currentPoint = 'A'
        self.totalEarnings = 0
        self.endtime = 0

class Booking :
    def __init__(self, booking_id, pickupPoint, dropPoint, pickupTime):
        self.booking_id = booking_id
        self.pickupPoint = pickupPoint
        self.dropPoint = dropPoint
        self.pickupTime = pickupTime
        self.Taxi = None

class Taxi_Booking_System :
    taxis = []
    bookings = []
    
    def __init__(self, numTaxis) -> None:
        self.taxis = []
        self.bookings = []
        for i in range(numTaxis):
            self.taxis.append(Taxi(i))
   
    def book_taxi(self,id, pickupPoint, dropPoint, pickupTime):
        availableTaxi = [taxi for taxi in self.taxis if taxi.endtime <= pickupTime]
        
        if not availableTaxi:
            return "Taxis not available"
        
        availableTaxi.sort(key=lambda x: (abs(ord(x.currentPoint) - ord(pickupPoint)), x.totalEarnings))
        
        chosenTaxi = availableTaxi[0]
        traveltime = abs(ord(pickupPoint) - ord(dropPoint))
        travelcost = 100 + max(0,(traveltime * 15 - 5)* 10)
        
        chosenTaxi.totalEarnings += travelcost
        chosenTaxi.currentPoint = dropPoint
        chosenTaxi.endtime = pickupTime + traveltime
        
        booking = Booking(id,pickupPoint,dropPoint,pickupTime)
        booking.taxi = chosenTaxi
        self.bookings.append(booking)
        
        return f"Taxi can be allotted.\nTaxi-{chosenTaxi.Taxi_id + 1} is allotted"
    
    
    def display_details(self):
        for taxi in self.taxis:
            print(f"Taxi-{taxi.Taxi_id + 1} Total Earnings: Rs. {taxi.totalEarnings}")
            for booking in self.bookings:
                if booking.taxi == taxi:
                    drop_time = booking.pickupTime + abs(ord(booking.pickupPoint) - ord(booking.dropPoint))
                    amount = 100 + max(0, (abs(ord(booking.pickupPoint) - ord(booking.dropPoint)) * 15 - 5) * 10)
                    print(f"{booking.booking_id} {booking.pickupPoint} {booking.dropPoint} {booking.pickupTime} {drop_time} {amount}")

if __name__ == "__main__":
    taxi_service = Taxi_Booking_System(4)

    print(taxi_service.book_taxi(1, 'A', 'B', 9))
    print(taxi_service.book_taxi(2, 'B', 'D', 9))
    print(taxi_service.book_taxi(3, 'B', 'C', 12))

    taxi_service.display_details()
        