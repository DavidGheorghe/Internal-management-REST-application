package com.dvd.service.impl;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dvd.DTO.ApplicationCustomerDTO;
import com.dvd.DTO.ApplicationUserDTO;
import com.dvd.DTO.GetResourcesResponse;
import com.dvd.DTO.order.ApplicationOrderContentDTO;
import com.dvd.DTO.order.ApplicationOrderDTO;
import com.dvd.DTO.order.RetrievedOrderContentDTO;
import com.dvd.DTO.order.RetrievedOrderDTO;
import com.dvd.DTO.reports.ActiveAndDueOrdersReportsDTO;
import com.dvd.DTO.reports.CompletedOrdersReportDTO;
import com.dvd.DTO.reports.NewOrdersReportDTO;
import com.dvd.entity.ApplicationColor;
import com.dvd.entity.ApplicationCustomer;
import com.dvd.entity.ApplicationRole;
import com.dvd.entity.ApplicationUser;
import com.dvd.entity.order.ApplicationOrder;
import com.dvd.entity.order.ApplicationOrderContent;
import com.dvd.entity.order.ApplicationOrderStatus;
import com.dvd.entity.product.ApplicationProduct;
import com.dvd.repository.ApplicationColorRepository;
import com.dvd.repository.ApplicationCustomerRepository;
import com.dvd.repository.ApplicationProductRepository;
import com.dvd.repository.ApplicationUserRepository;
import com.dvd.repository.order.ApplicationOrderRepository;
import com.dvd.service.ApplicationOrderService;
import com.dvd.utils.UtilsMethods;

import lombok.RequiredArgsConstructor;

/**
* Defines the service implementation for the Order resource.
*
* @author David Gheorghe
*/
@RequiredArgsConstructor
@Service
public class ApplicationOrderServiceImpl implements ApplicationOrderService {
	private final ApplicationOrderRepository orderRepository;
	private final ApplicationUserRepository userRepository;
	private final ApplicationCustomerRepository customerRepository;
	private final ApplicationColorRepository colorRepository;
	private final ApplicationProductRepository productRepository;
	private final ModelMapper mapper;
	
	@Override
	public RetrievedOrderDTO createOrder(ApplicationOrderDTO orderDTO) {
		/** Set status, due date, the customer, details and the content, then return a DTO. */
		ApplicationOrder newOrder = new ApplicationOrder();
		
		newOrder.setStatus(ApplicationOrderStatus.NEW);
		
		LocalDate dueDate = LocalDate.parse(orderDTO.getDueDate());
		newOrder.setDueDate(dueDate);
		
		ApplicationCustomer customer = UtilsMethods.getResourceByIdOrElseThrow(customerRepository, orderDTO.getCustomerId(), "Customer");
		newOrder.setCustomer(customer);
		
		newOrder.setDetails(orderDTO.getDetails());
		addContent(newOrder, orderDTO.getContent());
		
		orderRepository.save(newOrder);
		RetrievedOrderDTO newOrderDTO = mapOrderToDTOWithoutPinned(newOrder);
		return newOrderDTO;
	}

	@Override
	public RetrievedOrderDTO deleteById(Long id) {
		ApplicationOrder deletedOrder = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, id, "Order");
		RetrievedOrderDTO deletedOrderDTO = mapOrderToDTOWithoutPinned(deletedOrder);
		orderRepository.deleteById(id);
		return deletedOrderDTO;
	}

	@Override
	public GetResourcesResponse<RetrievedOrderDTO> getAllOrders(int pageNo, int pageSize, String sortBy,
			String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationOrder> orders = orderRepository.findAll(pageable);
		List<ApplicationOrder> listOfOrders = orders.getContent();
		List<RetrievedOrderDTO> content = listOfOrders
													.stream()
													.map(order -> mapOrderToDTO(order, UtilsMethods.getLoggedInUsername()))
													.collect(Collectors.toList());
		GetResourcesResponse<RetrievedOrderDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, orders);
		return response;
	}

	@Override
	public GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int pageNo, int pageSize,
			String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationOrder> orders = orderRepository.findByIdOrCustomerNameAndCompany(keyword, pageable);
		List<ApplicationOrder> listOfOrders = orders.getContent();
		List<RetrievedOrderDTO> content = listOfOrders
													.stream()
													.map(order -> mapOrderToDTO(order, UtilsMethods.getLoggedInUsername()))
													.collect(Collectors.toList());
		GetResourcesResponse<RetrievedOrderDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, orders);
		return response;
	}
	
	@Override
	public GetResourcesResponse<RetrievedOrderDTO> getAllOrdersFilteredBy(String keyword, int statusId, int pageNo,
			int pageSize, String sortBy, String sortDir) {
		ApplicationOrderStatus statusFilter = ApplicationOrderStatus.getStatusById(statusId);
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<ApplicationOrder> orders = keyword.isEmpty() ? 
				orderRepository.findByStatus(statusFilter, pageable) : 
				orderRepository.findByIdOrCustomerNameAndCompanyAndStatus(keyword, statusFilter, pageable);
		List<ApplicationOrder> listOfOrders = orders.getContent();
		List<RetrievedOrderDTO> content = listOfOrders
											.stream()
											.map(order -> mapOrderToDTO(order, UtilsMethods.getLoggedInUsername()))
											.collect(Collectors.toList());
		GetResourcesResponse<RetrievedOrderDTO> response = new GetResourcesResponse<>();
		response.setGetResourcesResponseFields(content, orders);
		return response;
	}
	
	@Override
	public List<RetrievedOrderDTO> getDashboardOrders() {
		String username = UtilsMethods.getLoggedInUsername();
		ApplicationUser user =  userRepository.findByUsername(username);
		List<RetrievedOrderDTO> ordersDTO = new ArrayList<>();
		List<ApplicationOrder> assignedOrders = user.getAssignedOrders();
		assignedOrders.stream().forEach(order -> ordersDTO.add(mapOrderToDTO(order, username)));
		if (user.getRoles().contains(ApplicationRole.SUPERVISOR)) {
			List<ApplicationOrder> pinnedOrders = user.getPinnedOrders();
			pinnedOrders.stream().forEach(order -> ordersDTO.add(mapOrderToDTO(order, username)));
		}
		return ordersDTO;
	}

	@Override
	public RetrievedOrderDTO getOrderById(Long id) {
		ApplicationOrder fetchedOrder = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, id, "Order");
		return mapOrderToDTOWithoutPinned(fetchedOrder);
	}

	@Override
	public RetrievedOrderDTO updateOrderStatus(Long orderId, int statusId) {
		ApplicationOrder updatedOrder = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		ApplicationOrderStatus newStatus = ApplicationOrderStatus.getStatusById(statusId);
		updatedOrder.setStatus(newStatus);
		orderRepository.save(updatedOrder);
		return mapOrderToDTOWithoutPinned(updatedOrder);
	}

	@Override
	public RetrievedOrderDTO updateOrder(Long id, ApplicationOrderDTO orderDTO) {
		ApplicationOrder updatedOrder = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, id, "Order");
		if (orderDTO.getCustomerId() != null) {
			updateOrderCustomer(updatedOrder, orderDTO.getCustomerId());
		}
		if (orderDTO.getStatusId() != null) {
			updateOrderStatus(updatedOrder, orderDTO.getStatusId());
		}
		
		String currentDetails = orderDTO.getDetails();
		String newDetails = updatedOrder.getDetails();
		if (UtilsMethods.isStringFieldValidForUpdate(currentDetails, newDetails)) {
			updatedOrder.setDetails(currentDetails);
		}
		
		String newDueDate = orderDTO.getDueDate();
		if (newDueDate != null) {
			updateDueDate(updatedOrder, newDueDate);
		}
		orderRepository.save(updatedOrder);
		return mapOrderToDTOWithoutPinned(updatedOrder);
	}

	@Override
	public RetrievedOrderDTO pinOrder(Long orderId, String username) {
		ApplicationUser user =  userRepository.findByUsername(username);
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		order.pinToUser(user);
		orderRepository.save(order);
		return mapOrderToDTO(order, username);
	}

	@Override
	public RetrievedOrderDTO unpinOrder(Long orderId, String username) {
		ApplicationUser user =  userRepository.findByUsername(username);
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		order.unpinFromUser(user);
		orderRepository.save(order);
		return mapOrderToDTO(order, username);
	}

	public boolean isOrderPinned(Long orderId, String username) {
		ApplicationUser user =  userRepository.findByUsername(username);
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		boolean isPinned = order.getPinnedTo().contains(user);
		return isPinned;
	}
	
	@Override
	public Double computeContentItemPrice(Long productId, Integer amount) {
		ApplicationProduct product = UtilsMethods.getResourceByIdOrElseThrow(productRepository, productId, "Product");
		Double productPrice = product.getProductPrices().getFinalPrice();
		Double contentPrice = productPrice * (double) amount;
		DecimalFormat df = new DecimalFormat("0.00");
		String formattedPriceLocale = df.format(contentPrice);
		String formattedPriceFinal = formattedPriceLocale.replace(",", ".");
		return Double.valueOf(formattedPriceFinal);
	}
	
	private void addContent(ApplicationOrder order, Set<ApplicationOrderContentDTO> contentSet) {
		Set<ApplicationOrderContent> finalContent = new HashSet<ApplicationOrderContent>();
		for (ApplicationOrderContentDTO contentDTO : contentSet) {
			ApplicationOrderContent content = createOrderContentFromDTO(contentDTO);
			finalContent.add(content);
		}
		order.setContent(finalContent);
	}
	
	@Override
	public List<RetrievedOrderContentDTO> getOrderContent(Long orderId) {
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		Set<ApplicationOrderContent> content = order.getContent();
		List<RetrievedOrderContentDTO> contentDTOs = new ArrayList<>();
		content.forEach(c -> {
			RetrievedOrderContentDTO contentDTO = mapper.map(c, RetrievedOrderContentDTO.class);
			Double contentPrice = computeContentItemPrice(c.getProduct().getId(), c.getQuantity());
			contentDTO.setContentPrice(contentPrice);
			contentDTOs.add(contentDTO);
		});
		return contentDTOs;
	}
	
	private ApplicationOrderContent createOrderContentFromDTO(ApplicationOrderContentDTO contentDTO) {
		ApplicationOrderContent content = new ApplicationOrderContent();
		Long colorId = contentDTO.getColorId();
		ApplicationColor color = UtilsMethods.getResourceByIdOrElseThrow(colorRepository, colorId, "Color");
		Long productId = contentDTO.getProductId();
		ApplicationProduct product = UtilsMethods.getResourceByIdOrElseThrow(productRepository, productId, "Product");
		content.setColor(color);
		content.setProduct(product);
		content.setQuantity(contentDTO.getQuantity());
		return content;
	}
	
	private void updateOrderStatus(ApplicationOrder order, int statusId) {
		ApplicationOrderStatus newStatus = ApplicationOrderStatus.getStatusById(statusId);
		if (newStatus != order.getStatus()) {
			order.setStatus(newStatus);
			orderRepository.save(order);
		}
	}

	private void updateOrderCustomer(ApplicationOrder order, Long customerId) {
		ApplicationCustomer newCustomer = UtilsMethods.getResourceByIdOrElseThrow(customerRepository, customerId, "Customer");
		if (UtilsMethods.isFieldValidForUpdate(newCustomer, order.getCustomer())) {
			order.getCustomer().getOrders().remove(order);
			order.setCustomer(newCustomer);
		}
	}
	
	private void updateOrderContents(ApplicationOrder order, Set<ApplicationOrderContentDTO> contentsDTOs) {
		for (ApplicationOrderContent content : order.getContent()) {
			
		}
	}
	
	private void updateContent(ApplicationOrderContent updatedOrderContent, Long newProductId, Long newColorId, Integer newQuantity) {
		if (newProductId != null) {
			updateProduct(updatedOrderContent, newProductId);
		}
		if (newColorId != null) {
			updateColor(updatedOrderContent, newColorId);
		}
		if (newQuantity != null) {
			updateQuantity(updatedOrderContent, newQuantity);
		}
	}
	
	private void updateProduct(ApplicationOrderContent updatedOrderContent, Long newProductId) {
		ApplicationProduct newProduct = UtilsMethods.getResourceByIdOrElseThrow(productRepository, newProductId, "Product");
		if (UtilsMethods.isFieldValidForUpdate(newProduct, updatedOrderContent.getProduct())) {
			updatedOrderContent.setProduct(newProduct);
		}
	}
	
	private void updateColor(ApplicationOrderContent updatedOrderContent, Long newColorId) {
		ApplicationColor newColor = UtilsMethods.getResourceByIdOrElseThrow(colorRepository, newColorId, "Color");
		if (UtilsMethods.isFieldValidForUpdate(newColor, updatedOrderContent.getColor())) {
			updatedOrderContent.setColor(newColor);
		}
	}
	
	private void updateQuantity(ApplicationOrderContent updatedOrderContent, Integer newQuantity) {
		if (updatedOrderContent.getQuantity() != newQuantity) {
			updatedOrderContent.setQuantity(newQuantity);
		}
	}
	
	private void updateDueDate(ApplicationOrder order, String dueDateStr) {
		LocalDate newDueDate = LocalDate.parse(dueDateStr);
		LocalDate oldDueDate = order.getDueDate();
		if (UtilsMethods.isFieldValidForUpdate(newDueDate, oldDueDate)) {
			order.setDueDate(newDueDate);
		}
	}
	
	@Override
	public RetrievedOrderDTO updateDueDate(Long id, String dueDateStr) {
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, id, "Order");
		LocalDate newDueDate = LocalDate.parse(dueDateStr);
		LocalDate oldDueDate = order.getDueDate();
		if (UtilsMethods.isFieldValidForUpdate(newDueDate, oldDueDate)) {
			order.setDueDate(newDueDate);
		}
		orderRepository.save(order);
		return mapOrderToDTOWithoutPinned(order);
	}
	
	@Override
	public ActiveAndDueOrdersReportsDTO getActiveAndDueReports() {
		ActiveAndDueOrdersReportsDTO orderReportsDTO = new ActiveAndDueOrdersReportsDTO();
		Integer numberOfDueOrders = orderRepository.getNumberOfDueOrdersInOneWeek();
		Integer numberOfActiveOrders = orderRepository.getNumberOfActiveOrders();
		
		orderReportsDTO.setNumberOfActiveOrders(numberOfActiveOrders);
		orderReportsDTO.setNumberOfDueOrders(numberOfDueOrders);
		
		return orderReportsDTO;
	}
	
	@Override
	public CompletedOrdersReportDTO getCompletedOrdersReport() {
		Integer eightWeeksAgo = orderRepository.getNumberOfCompletedOrdersEightWeeks();
		Integer sevenWeeksAgo = orderRepository.getNumberOfCompletedOrdersSevenWeeks();
		Integer sixWeeksAgo = orderRepository.getNumberOfCompletedOrdersSixWeeks();
		Integer fiveWeeksAgo = orderRepository.getNumberOfCompletedOrdersFiveWeeks();
		Integer fourWeeksAgo = orderRepository.getNumberOfCompletedOrdersFourWeeks();
		Integer threeWeeksAgo = orderRepository.getNumberOfCompletedOrdersThreeWeeks();
		Integer twoWeeksAgo = orderRepository.getNumberOfCompletedOrdersTwoWeeks();
		Integer oneWeekAgo = orderRepository.getNumberOfCompletedOrdersOneWeek();
		
		CompletedOrdersReportDTO report = new CompletedOrdersReportDTO();
		
		report.setNumberOfOrderCompletedEightWeeksAgo(eightWeeksAgo);
		report.setNumberOfOrderCompletedSevenWeeksAgo(sevenWeeksAgo);
		report.setNumberOfOrderCompletedSixWeeksAgo(sixWeeksAgo);
		report.setNumberOfOrderCompletedFiveWeeksAgo(fiveWeeksAgo);
		report.setNumberOfOrderCompletedFourWeeksAgo(fourWeeksAgo);
		report.setNumberOfOrderCompletedThreeWeeksAgo(threeWeeksAgo);
		report.setNumberOfOrderCompletedTwoWeeksAgo(twoWeeksAgo);
		report.setNumberOfOrderCompletedOneWeekAgo(oneWeekAgo);
		
		return report;
	}
	
	@Override
	public NewOrdersReportDTO getNewOrdersReport() {
		Integer eightWeeksAgo = orderRepository.getNumberOfNewOrdersEightWeeks();
		Integer sevenWeeksAgo = orderRepository.getNumberOfNewOrdersSevenWeeks();
		Integer sixWeeksAgo = orderRepository.getNumberOfNewOrdersSixWeeks();
		Integer fiveWeeksAgo = orderRepository.getNumberOfNewOrdersFiveWeeks();
		Integer fourWeeksAgo = orderRepository.getNumberOfNewOrdersFourWeeks();
		Integer threeWeeksAgo = orderRepository.getNumberOfNewOrdersThreeWeeks();
		Integer twoWeeksAgo = orderRepository.getNumberOfNewOrdersTwoWeeks();
		Integer oneWeekAgo = orderRepository.getNumberOfNewOrdersOneWeek();
		
		NewOrdersReportDTO report = new NewOrdersReportDTO();
		
		report.setNumberOfNewOrdersEightWeeksAgo(eightWeeksAgo);
		report.setNumberOfNewOrdersSevenWeeksAgo(sevenWeeksAgo);
		report.setNumberOfNewOrdersSixWeeksAgo(sixWeeksAgo);
		report.setNumberOfNewOrdersFiveWeeksAgo(fiveWeeksAgo);
		report.setNumberOfNewOrdersFourWeeksAgo(fourWeeksAgo);
		report.setNumberOfNewOrdersThreeWeeksAgo(threeWeeksAgo);
		report.setNumberOfNewOrdersTwoWeeksAgo(twoWeeksAgo);
		report.setNumberOfNewOrdersOneWeekAgo(oneWeekAgo);
		
		return report;
	}
	
	@Override
	public RetrievedOrderDTO assignOrderToUser(Long orderId, String username) {
		ApplicationUser user =  userRepository.findByUsername(username);
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		order.assignToUser(user);
		orderRepository.save(order);
		return mapOrderToDTOWithoutPinned(order);
	}
	
	@Override
	public RetrievedOrderDTO removeAssignedOrderFromUser(Long orderId, String username) {
		ApplicationUser user =  userRepository.findByUsername(username);
		ApplicationOrder order = UtilsMethods.getResourceByIdOrElseThrow(orderRepository, orderId, "Order");
		order.removeAssignedFromUser(user);
		orderRepository.save(order);
		return mapOrderToDTOWithoutPinned(order);
	}
	
	
	private RetrievedOrderDTO mapOrderToDTO(ApplicationOrder order, String username) {
		RetrievedOrderDTO orderDTO = new RetrievedOrderDTO();
		
		orderDTO.setCustomer(mapper.map(order.getCustomer(), ApplicationCustomerDTO.class));
		orderDTO.setDetails(order.getDetails());
		orderDTO.setDueDate(order.getDueDate());
		orderDTO.setEntryDate(order.getEntryDate());
		orderDTO.setId(order.getId());
		orderDTO.setStatus(order.getStatus());
		if (order.getUser() != null) {
			orderDTO.setAssignee(mapper.map(order.getUser(), ApplicationUserDTO.class));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserUsername = authentication.getName();
		boolean pinned = isOrderPinned(orderDTO.getId(), currentUserUsername);
		orderDTO.setPinned(pinned);
		
		return orderDTO;
	}
	
	private RetrievedOrderDTO mapOrderToDTOWithoutPinned(ApplicationOrder order) {
		RetrievedOrderDTO orderDTO = new RetrievedOrderDTO();
		
		orderDTO.setCustomer(mapper.map(order.getCustomer(), ApplicationCustomerDTO.class));
		orderDTO.setDetails(order.getDetails());
		orderDTO.setDueDate(order.getDueDate());
		orderDTO.setEntryDate(order.getEntryDate());
		orderDTO.setId(order.getId());
		orderDTO.setStatus(order.getStatus());
		if (order.getUser() != null) {
			orderDTO.setAssignee(mapper.map(order.getUser(), ApplicationUserDTO.class));
		}
		return orderDTO;
	}
}
