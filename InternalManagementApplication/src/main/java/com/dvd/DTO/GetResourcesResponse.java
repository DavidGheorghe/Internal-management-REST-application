package com.dvd.DTO;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* Defines a custom response for retrieving resources.
*
* @author David Gheorghe
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetResourcesResponse<T> {
	private List<T> content;
	private int pageNo;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private boolean last;
	
	public <M> void setGetResourcesResponseFields(List<T> content, Page<M> roles) {
		this.setContent(content);
		this.setPageNo(roles.getNumber());
		this.setPageSize(roles.getSize());
		this.setTotalPages(roles.getTotalPages());
		this.setTotalElements(roles.getTotalElements());
		this.setLast(roles.isLast());
	}
}
