//package com.example.demo.entities;
//
//import jakarta.persistence.*;
//import org.locationtech.jts.geom.Point;
//
//@Entity
//public class PetSighting {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String message;
//
//    @Column(name = "latitude", nullable = false)
//    private double latitude;
//
//    @Column(name = "longitude", nullable = false)
//    private double longitude;
//
//    @ManyToOne
//    @JoinColumn(name = "reported_by", nullable = false)
//    private User reportedBy;
//
//    @ManyToOne
//    @JoinColumn(name = "pet_id", nullable = true)
//    private Pet pet;
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public double getLatitude() {
//		return latitude;
//	}
//
//	public void setLatitude(double latitude) {
//		this.latitude = latitude;
//	}
//
//	public double getLongitude() {
//		return longitude;
//	}
//
//	public void setLongitude(double longitude) {
//		this.longitude = longitude;
//	}
//
//	public User getReportedBy() {
//		return reportedBy;
//	}
//
//	public void setReportedBy(User reportedBy) {
//		this.reportedBy = reportedBy;
//	}
//
//	public Pet getPet() {
//		return pet;
//	}
//
//	public void setPet(Pet pet) {
//		this.pet = pet;
//	}
//
//}
package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pet_sightings")
public class PetSighting {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(nullable = false)
	private String message;

	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	private double longitude;

	// Auditoría
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	// Relaciones
	@ManyToOne
	@JoinColumn(name = "reported_by", nullable = false)
	private User reportedBy;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	// Getters y Setters
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public User getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(User reportedBy) {
		this.reportedBy = reportedBy;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
