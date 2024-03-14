package com.amenity_reservation_system;

import com.amenity_reservation_system.model.AmenityType;
import com.amenity_reservation_system.model.Capacity;
import com.amenity_reservation_system.model.Reservation;
import com.amenity_reservation_system.model.User;
import com.amenity_reservation_system.repos.CapacityRepository;
import com.amenity_reservation_system.repos.ReservationRepository;
import com.amenity_reservation_system.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@SpringBootApplication
public class AmenityReservationSystemApplication {

  private Map<AmenityType, Integer> initialCapacities =
      new HashMap<>() {
        {
          put(AmenityType.GYM, 20);
          put(AmenityType.POOL, 4);
          put(AmenityType.SAUNA, 1);
        }
      };

  public static void main(String[] args) {
    SpringApplication.run(AmenityReservationSystemApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(
      UserRepository userRepository,
      CapacityRepository capacityRepository) {
    return (args) -> {

      List<String> usernamesToCheck = Arrays.asList("dasanik", "jaiswaltushar", "prashant", "ansarishoib", "singhaparna");
      List<User> existingUsers = userRepository.findUserByUsernameIn(usernamesToCheck);

      if (existingUsers.isEmpty()) {
        userRepository.save(
                new User("Anik Das", "dasanik", bCryptPasswordEncoder().encode("12345")));

        userRepository.save(
                new User("Tushar Jaiswal", "jaiswaltushar", bCryptPasswordEncoder().encode("12345")));

        userRepository.save(
                new User("Prashant Prashant", "prashant", bCryptPasswordEncoder().encode("12345")));

        userRepository.save(
                new User("Shoib Ansari", "ansarishoib", bCryptPasswordEncoder().encode("12345")));

        userRepository.save(
                new User("Aparna Singh", "singhaparna", bCryptPasswordEncoder().encode("12345")));
      }

      List<AmenityType> capacityTypesToCheck = Arrays.asList(AmenityType.POOL, AmenityType.GYM, AmenityType.SAUNA);
      List<Capacity> existingCapacities = capacityRepository.findByAmenityTypeIn(capacityTypesToCheck);

      if(existingCapacities.isEmpty()) {
        for (AmenityType amenityType : initialCapacities.keySet()) {
          capacityRepository.save(new Capacity(amenityType, initialCapacities.get(amenityType)));
        }
      }

    };
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
