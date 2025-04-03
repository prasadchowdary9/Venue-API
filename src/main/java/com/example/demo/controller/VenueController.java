package com.example.demo.controller;



import com.example.demo.dto.VenueDTO;
import com.example.demo.entity.Venue;
import com.example.demo.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin(origins = "*")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // Create a new Venue along with Availability and Slots
    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody VenueDTO venueDTO) {
        Venue createdVenue = venueService.createVenue(venueDTO);
        return ResponseEntity.ok(createdVenue);
    }

    // Get all Venues with Availability and Slots
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        List<VenueDTO> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }
}
