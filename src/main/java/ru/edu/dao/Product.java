package ru.edu.dao;

import java.time.LocalDate;

public class Product {

	private String id;
	private String name;
	private LocalDate manufactureDate;

	public Product(String id, String name, LocalDate manufactureDate) {
		this.id = id;
		this.name = name;
		this.manufactureDate = manufactureDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getManufactureDate() {
		return manufactureDate;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", manufactureDate=" + manufactureDate +
				'}';
	}

}
