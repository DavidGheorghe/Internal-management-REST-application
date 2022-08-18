package com.dvd.DTO.reports;

import lombok.Data;

/**
* Defines the DTO to store the number of completed orders per week for the last eight weeks.
*
* @author David Gheorghe
*/
@Data
public class CompletedOrdersReportDTO {
	int numberOfOrderCompletedEightWeeksAgo;
	int numberOfOrderCompletedSevenWeeksAgo;
	int numberOfOrderCompletedSixWeeksAgo;
	int numberOfOrderCompletedFiveWeeksAgo;
	int numberOfOrderCompletedFourWeeksAgo;
	int numberOfOrderCompletedThreeWeeksAgo;
	int numberOfOrderCompletedTwoWeeksAgo;
	int numberOfOrderCompletedOneWeekAgo;
}
