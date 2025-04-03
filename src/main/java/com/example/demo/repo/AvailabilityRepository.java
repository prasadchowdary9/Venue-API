package com.example.demo.repo;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Availability;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByVenueId(Long venueId);
}
