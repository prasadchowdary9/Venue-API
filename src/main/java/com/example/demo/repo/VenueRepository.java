package com.example.demo.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {
//    List<Venue> findByUserId(Long userId);
}
