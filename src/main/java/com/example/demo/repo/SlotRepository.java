package com.example.demo.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Slot;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByAvailabilityId(Long availabilityId);
}
