package com.dvd.DTO.reports;

import lombok.Data;

/**
* * Defines the DTO to store the number of new orders per week for the last eight weeks.

*
* @author David Gheorghe
*/
@Data
public class NewOrdersReportDTO {
	int numberOfNewOrdersEightWeeksAgo;
	int numberOfNewOrdersSevenWeeksAgo;
	int numberOfNewOrdersSixWeeksAgo;
	int numberOfNewOrdersFiveWeeksAgo;
	int numberOfNewOrdersFourWeeksAgo;
	int numberOfNewOrdersThreeWeeksAgo;
	int numberOfNewOrdersTwoWeeksAgo;
	int numberOfNewOrdersOneWeekAgo;
}
