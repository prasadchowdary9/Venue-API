package com.example.demo.dto;

import java.util.List;

public class AvailabilityDTO {
    private String date;
    private List<SlotDTO> slots;

    public AvailabilityDTO() {}


	public AvailabilityDTO(String date, List<SlotDTO> slots) {
		super();
		this.date = date;
		this.slots = slots;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<SlotDTO> getSlots() {
		return slots;
	}

	public void setSlots(List<SlotDTO> slots) {
		this.slots = slots;
	}

    // Getters and Setters
    
}
