package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AvailabilityDTO;
import com.example.demo.dto.SlotDTO;
import com.example.demo.dto.VenueDTO;
import com.example.demo.entity.Availability;
import com.example.demo.entity.Slot;
import com.example.demo.entity.Venue;
import com.example.demo.repo.AvailabilityRepository;
import com.example.demo.repo.SlotRepository;
import com.example.demo.repo.VenueRepository;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private SlotRepository slotRepository;

    // Create Venue with Availability and Slots
    public Venue createVenue(VenueDTO venueDTO) {
        Venue venue = new Venue();
        venue.setName(venueDTO.getName());
        venue.setLocation(venueDTO.getLocation());
        venue.setCity(venueDTO.getCity());
        venue.setDescription(venueDTO.getDescription());
        venue.setImage(venueDTO.getImage());
        venue.setImages(venueDTO.getImages());
        venue.setPrice(venueDTO.getPrice());
        venue.setCapacity(venueDTO.getCapacity());
        venue.setAmenities(venueDTO.getAmenities());
        venue.setRating(venueDTO.getRating());
        venue.setReviewCount(venueDTO.getReviewCount());
        venue.setFeatured(venueDTO.isFeatured());
        venue.setUserId(venueDTO.getUserId());

        Venue savedVenue = venueRepository.save(venue);

        // Save Availability and Slots
        if (venueDTO.getAvailabilityList() != null) {
            for (AvailabilityDTO availabilityDTO : venueDTO.getAvailabilityList()) {
                Availability availability = new Availability();
                availability.setDate(availabilityDTO.getDate());
                availability.setVenueId(savedVenue.getId());
                Availability savedAvailability = availabilityRepository.save(availability);

                if (availabilityDTO.getSlots() != null) {
                    for (SlotDTO slotDTO : availabilityDTO.getSlots()) {
                        Slot slot = new Slot();
                        slot.setTime(slotDTO.getTime());
                        slot.setAvailable(slotDTO.isAvailable());
                        slot.setAvailabilityId(savedAvailability.getId());
                        slotRepository.save(slot);
                    }
                }
            }
        }
        return savedVenue;
    }

    // Get All Venues with Related Availability and Slots
    public List<VenueDTO> getAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        List<VenueDTO> venueDTOList = new ArrayList<>();

        for (Venue venue : venues) {
            VenueDTO venueDTO = new VenueDTO();
            venueDTO.setId(venue.getId());
            venueDTO.setName(venue.getName());
            venueDTO.setLocation(venue.getLocation());
            venueDTO.setCity(venue.getCity());
            venueDTO.setDescription(venue.getDescription());
            venueDTO.setImage(venue.getImage());
            venueDTO.setImages(venue.getImages());
            venueDTO.setPrice(venue.getPrice());
            venueDTO.setCapacity(venue.getCapacity());
            venueDTO.setAmenities(venue.getAmenities());
            venueDTO.setRating(venue.getRating());
            venueDTO.setReviewCount(venue.getReviewCount());
            venueDTO.setFeatured(venue.isFeatured());

            // Get Availabilities
            List<Availability> availabilities = availabilityRepository.findByVenueId(venue.getId());
            List<AvailabilityDTO> availabilityDTOList = new ArrayList<>();

            for (Availability availability : availabilities) {
                AvailabilityDTO availabilityDTO = new AvailabilityDTO();
                availabilityDTO.setId(availability.getId());
                availabilityDTO.setDate(availability.getDate());

                // Get Slots for Availability
                List<Slot> slots = slotRepository.findByAvailabilityId(availability.getId());
                List<SlotDTO> slotDTOList = new ArrayList<>();
                for (Slot slot : slots) {
                    SlotDTO slotDTO = new SlotDTO();
                    slotDTO.setId(slot.getId());
                    slotDTO.setTime(slot.getTime());
                    slotDTO.setAvailable(slot.isAvailable());
                    slotDTO.setAvailabilityId(availability.getId());
                    slotDTOList.add(slotDTO);
                }

                availabilityDTO.setSlots(slotDTOList);
                availabilityDTOList.add(availabilityDTO);
            }

            venueDTO.setAvailabilityList(availabilityDTOList);
            venueDTOList.add(venueDTO);
        }
        return venueDTOList;
    }
}
