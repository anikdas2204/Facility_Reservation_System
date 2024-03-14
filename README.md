# facility-reservation-system

A facility reservation system where users/residents can log in and reserve a time to use a service such as fitness center, pool, or sauna in a hypothetical apartment complex.
Each amenity will have a certain capacity (number of people that can use the service at the same time) so that people can make use of the amenities safely
and remotely.

## Technologies
* Spring Boot
* Thymeleaf
* Hibernate
* Swagger
* Spring Security
* Bootify
* Maven
* JPA
* H2 In-Memory Database
* Bootstrap

## Use Cases / User Stories

### 1. Facility Overview:

- Users should have access to an overview of all available facilities within the apartment complex, including details about each facility such as its type (e.g., POOL, GYM, SAUNA) and any specific rules or guidelines associated with usage.

### 2. Facility Booking:

- Users should be able to check the availability of facilities for booking.
- The system should allow users to make reservations for available time slots for a selected facility.
- Booking functionality should ensure that a facility can be reserved only if it is available during the specified time slot.

### 3. Active Reservations:

- Users should have a view of their active reservations.
- Reservations should be automatically removed from the user's view after the reservation time period has ended.

### 4. Reservation Modification:

- Users should be able to modify their reservation details, including changing the reservation date and time slots.
- Modification should be allowed only if the desired date and time slots are available for booking.

### 5. Reservation Cancellation:

- Users should have the ability to manually cancel their reservations.
- Cancelled reservations should be promptly removed from the user's active reservations list.

### 6. Feedback Submission:

- Users should be able to provide feedback after using any facility.
- Feedback submissions should be visible to the apartment manager/owner for review and improvement.


# This is a test line
