package model;

import java.time.LocalDate;

public class Asset {
	private String id;
	private String name;
	private String category; // propertyType
	private int quantity;
	private String unit;
	private double value;
	private LocalDate acquiredDate;
	private String notes; // description

	public Asset() {
	}

	public Asset(String id, String name, String category, int quantity, String unit, double value, LocalDate acquiredDate,
			String notes) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.quantity = quantity;
		this.unit = unit;
		this.value = value;
		this.acquiredDate = acquiredDate;
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getAcquiredDate() {
		return acquiredDate;
	}

	public void setAcquiredDate(LocalDate acquiredDate) {
		this.acquiredDate = acquiredDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	// Alias methods for compatibility with Property naming
	public String getPropertyId() {
		return id;
	}

	public void setPropertyId(String propertyId) {
		this.id = propertyId;
	}

	public String getPropertyName() {
		return name;
	}

	public void setPropertyName(String propertyName) {
		this.name = propertyName;
	}

	public String getPropertyType() {
		return category;
	}

	public void setPropertyType(String propertyType) {
		this.category = propertyType;
	}

	public String getDescription() {
		return notes;
	}

	public void setDescription(String description) {
		this.notes = description;
	}

	public long getValueAsLong() {
		return (long) value;
	}
}
