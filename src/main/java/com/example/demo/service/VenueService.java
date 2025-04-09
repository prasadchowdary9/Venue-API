package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repo.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    @Autowired
    private VenueRepository venueRepository;

    public List<VenueDTO> getAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        List<VenueDTO> venueDTOs = new ArrayList<>();
        
        for (Venue venue : venues) {
            venueDTOs.add(convertToDTO(venue));
        }
        
        return venueDTOs;
    }
    public VenueDTO getVenueById(Long id) {
    	Optional<Venue> venue = venueRepository.findById(id);
    	if(venue.isPresent()) {
    		return convertToDTO(venue.get());
    	}
    	return new VenueDTO();
    	
    }

    public VenueDTO createVenue(VenueDTO venueDTO) {
        Venue venue = convertToEntity(venueDTO);
        Venue savedVenue = venueRepository.save(venue);
        return convertToDTO(savedVenue);
    }

    private VenueDTO convertToDTO(Venue venue) {
        List<AvailabilityDTO> availabilityDTOs = new ArrayList<>();
        
        for (Availability availability : venue.getAvailability()) {
            List<SlotDTO> slotDTOs = new ArrayList<>();
            
            for (Slot slot : availability.getSlots()) {
                slotDTOs.add(new SlotDTO(slot.getTime(), slot.isAvailable()));
            }
            
            availabilityDTOs.add(new AvailabilityDTO(availability.getDate(), slotDTOs));
        }
        
        return new VenueDTO(
                String.valueOf(venue.getId()), venue.getName(), venue.getLocation(), venue.getDescription(),
                venue.getImage(), venue.getImages(), venue.getPrice(), venue.getCapacity(),
                venue.getAmenities(), venue.getRating(), venue.getReviewCount(), availabilityDTOs,
                venue.isFeatured(), venue.getCity()
        );
    }

    private Venue convertToEntity(VenueDTO dto) {
        Venue venue = new Venue();
        venue.setName(dto.getName());
        venue.setLocation(dto.getLocation());
        venue.setDescription(dto.getDescription());
        venue.setImage(dto.getImage());
        venue.setImages(dto.getImages());
        venue.setPrice(dto.getPrice());
        venue.setCapacity(dto.getCapacity());
        venue.setAmenities(dto.getAmenities());
        venue.setRating(dto.getRating());
        venue.setReviewCount(dto.getReviewCount());
        venue.setFeatured(dto.isFeatured());
        venue.setCity(dto.getCity());
        
        List<Availability> availabilityList = new ArrayList<>();
        
        for (AvailabilityDTO availabilityDTO : dto.getAvailability()) {
            Availability availability = new Availability();
            availability.setDate(availabilityDTO.getDate());
            availability.setVenue(venue);
            
            List<Slot> slotList = new ArrayList<>();
            for (SlotDTO slotDTO : availabilityDTO.getSlots()) {
                Slot slot = new Slot();
                slot.setTime(slotDTO.getTime());
                slot.setAvailable(slotDTO.isAvailable());
                slot.setAvailability(availability);
                slotList.add(slot);
            }
            
            availability.setSlots(slotList);
            availabilityList.add(availability);
        }
        
        venue.setAvailability(availabilityList);
        return venue;
    }
}