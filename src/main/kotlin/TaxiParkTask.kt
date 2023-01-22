package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver -> trips.none { it.driver == driver } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int) =
    allPassengers.filter { passenger -> trips.filter { it.passengers.contains(passenger) }.size >= minTrips }.toSet()


/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    trips.filter { it.driver == driver }.flatMap { it.passengers }.groupingBy { it }.eachCount()
        .filter { it.value > 1 }.keys

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { passenger ->
        trips.filter { it.passengers.contains(passenger) }.count { it.discount != null } >
                trips.filter { it.passengers.contains(passenger) }.count { it.discount == null }
    }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val map = trips.groupingBy { it.duration / 10 }.eachCount()
    val max = map.maxBy { it.value }?.value
    return map.filter { it.value == max }.keys.firstOrNull()?.let { it * 10..it * 10 + 9 }
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val income = trips.groupingBy { it.driver }.fold(0.0) { sum, trip -> sum + trip.cost }
    val drivers20 = allDrivers.size * 0.2
    val income80 = income.values.sum() * 0.8
    return trips.isNotEmpty() && income.values.sortedDescending().take(drivers20.toInt()).sum() >= income80
}
