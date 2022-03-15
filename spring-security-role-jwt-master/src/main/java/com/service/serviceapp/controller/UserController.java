package com.service.serviceapp.controller;

import com.service.serviceapp.config.Exception.BusinessException;
import com.service.serviceapp.config.TokenProvider;
import com.service.serviceapp.model.*;
import com.service.serviceapp.service.ReservationService;
import com.service.serviceapp.service.UserService;
import com.service.serviceapp.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user) throws BusinessException {
        return userService.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }

    //Reservation Controller

    @PreAuthorize("hasRole('USER' || 'ADMIN')")
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public Reservation saveReservation(@RequestBody ReservationDto reservationDto) throws BusinessException {
        return reservationService.addReservation(reservationDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/getallreservations",method = RequestMethod.GET)
    public List<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public Reservation updateReservation(@RequestBody Reservation reservation,@PathVariable long id){
        return reservationService.update(reservation,id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/delete/{reservationid}",method = RequestMethod.DELETE)
    public void deleteReservation(@PathVariable("reservationid") Long reservationid){
        reservationService.deleteById(reservationid);
    }
}
