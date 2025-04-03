package com.example.demo.dto;

public class SlotDTO {
    private String time;
    private boolean available;

    public SlotDTO() {}

	public SlotDTO(String time2, boolean available2) {
		// TODO Auto-generated constructor stub
		this.time=time2;
		this.available = available2;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

    // Getters and Setters
    
}
