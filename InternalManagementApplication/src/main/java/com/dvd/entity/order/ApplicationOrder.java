package com.dvd.entity.order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dvd.entity.ApplicationCustomer;
import com.dvd.entity.ApplicationUser;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
* Defines the Order resource.
*
* @author David Gheorghe
*/
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "Orders")
public class ApplicationOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ApplicationOrderStatus status;
	
	private LocalDateTime entryDate = LocalDateTime.now();
	private LocalDateTime completionDate;
	private LocalDate dueDate;
	private String details;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ApplicationUser> pinnedTo;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private ApplicationUser user;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private ApplicationCustomer customer;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ApplicationOrderContent> content;
	
	public void setCustomer(ApplicationCustomer customer) {
		this.customer = customer;
		customer.addOrder(this);
	}
	
	public void setContent(Set<ApplicationOrderContent> content) {
		this.content = content;
		content.forEach(obj -> obj.setOrder(this));
	}
	
	public void setStatus(ApplicationOrderStatus status) {
		if (status == ApplicationOrderStatus.COMPLETE) {
			completionDate = LocalDateTime.now();
		}
		this.status = status;
	}
	
	public void pinToUser(ApplicationUser user) {
		if (pinnedTo.contains(user) == false) {
			pinnedTo.add(user);
			user.addPinnedOrder(this);
		}
	}
	
	public void unpinFromUser(ApplicationUser user) {
		if (pinnedTo.contains(user)) {
			pinnedTo.remove(user);
			user.removePinnedOrder(this);
		}
	}
	
	public void assignToUser(ApplicationUser user) {
		if (user.equals(this.user) == false) {
			this.user = user;
			
		}
	}
	
	public void removeAssignedFromUser(ApplicationUser user) {
		if (user.equals(this.user)) {
			this.user = null;
			
		}
	}
	
	public String toString() {
		return createToString();
	}
	
	private String createToString() {
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.append("Order[ id = ").append(getId()).append(", status = ").append(getStatus()).append(", due date = ")
			.append(getDueDate()).append(", customerId").append(getCustomer().getId()).toString();
	}
}