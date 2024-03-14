package com.amenity_reservation_system;

import com.amenity_reservation_system.model.Reservation;
import com.amenity_reservation_system.model.User;
import com.amenity_reservation_system.service.ReservationService;
import com.amenity_reservation_system.service.UserDetailsServiceImpl;
import com.amenity_reservation_system.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Set;

@Controller
public class PagesController {

    final UserService userService;
    final ReservationService reservationService;

    public PagesController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/reservations")
    public String reservations(Model model, HttpSession session) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        User user = userService.getUserByUsername(name);

        // This should always be the case
        if (user != null) {
            session.setAttribute("user", user);

            // Empty reservation object in case the user creates a new reservation
            Reservation reservation = new Reservation();
            model.addAttribute("reservation", reservation);

            return "reservations";
        }

        return "index";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    // Add a mapping for the Sign Up button
    @GetMapping("/signup-page")
    public String goToSignupPage() {
        return "redirect:/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user) {
        userService.createUser(user);
        return "redirect:/login"; // Redirect to the login page after successful signup
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/reservations-submit")
    public String reservationsSubmit(@ModelAttribute Reservation reservation, BindingResult result, Model model, @SessionAttribute("user") User user) {
        // Save to DB after updating
        assert user != null;

        if (reservation.getReservationDate() != null && reservation.getReservationDate().isBefore(LocalDate.now())) {
            result.rejectValue("reservationDate", "error.reservation", "Reservation date must be today or later");
        }

        if (result.hasErrors()) {
            model.addAttribute("bindingResult", result);
            model.addAttribute("user", user);
            model.addAttribute("reservation", reservation);
            return "reservations";
        }

        reservation.setUser(user);
        reservationService.create(reservation);
        Set<Reservation> userReservations = user.getReservations();
        userReservations.add(reservation);
        user.setReservations(userReservations);
        userService.update(user.getId(), user);
        return "redirect:/reservations";
    }
}
