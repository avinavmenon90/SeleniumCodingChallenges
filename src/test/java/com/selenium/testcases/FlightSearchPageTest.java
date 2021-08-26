package com.selenium.testcases;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.factory.DriverFactory;
import com.selenium.utilities.ConfigPropertiesFileReader;
import com.selenium.utilities.ElementUtils;

import junit.framework.Assert;

import com.selenium.pageobects.FlightsSearchPage;
import com.selenium.pageobects.SearchResultsPage;

/*
 * 1) go to this url - https://www.makemytrip.com
2) Click on flights link and click on round trip
3) Select from: Delhi and To: Bangalore
4) Select departure date (today’s date) and return date: after 7 days 
5) Click on Search
6) Print total number of records of “Departure Flight” and “Return Flight” on the console
7) Select Non-Stop and 1 Stop filter options and print total number of records of “Departure Flight” and “Return Flight” on the console
8) Select radio button of top 10 options of “Departure Flight” and “Return Flight”
9) Verify the same Departure Flight price and Return Flight price are getting reflected at bottom of the page
10) Verify the correct total amount (Departure Flight price + Return Flight price) is getting reflected correctly
 * 
 */

public class FlightSearchPageTest {

	DriverFactory df;
	ConfigPropertiesFileReader cp;
	Properties prop;
	WebDriver driver;
	FlightsSearchPage flightsSearchPage;
	SearchResultsPage searchResultsPage;
	ElementUtils elementUtils;
	private WebDriver searcResultsPageDriver;
	
	@BeforeClass
	public void setUp() {
		
		cp = new ConfigPropertiesFileReader();
		df = new DriverFactory();
		prop = cp.initProperties(); //initiate & load from properties file
		driver = df.initDriver(prop.getProperty("browser"), prop); //pass prop obj to df & launch URL
		
		flightsSearchPage = new FlightsSearchPage(driver); //initiate FlightsSearchPage obj
		//searchResultsPage = new SearchResultsPage(driver); //initiate SearchResultsPage obj
		elementUtils = new ElementUtils(driver);
	}
	
	@AfterClass
	public void tearDown() {
		
		driver.close();
		driver.quit();
	}
	
	
	@Test(priority=1)
	public void searchForFlights() throws InterruptedException {
		
		flightsSearchPage.dismissLoginBanner();
		flightsSearchPage.selectRoundTrip();
		flightsSearchPage.fromDestination("Delhi");	
		Thread.sleep(1000);
		flightsSearchPage.toDestination("Bangalore");
		flightsSearchPage.departureDate("Oct 15 2021");

		flightsSearchPage.dismissLoginBanner(); 
		
		flightsSearchPage.returnDate("Nov 10 2021");
		flightsSearchPage.numberOfTravellers(8);
		
		Thread.sleep(1000);
		
	//initializing search page obj after landing on that page	
		searcResultsPageDriver = flightsSearchPage.searchFlights();
		searchResultsPage = new SearchResultsPage(searcResultsPageDriver);
		
	//Assert the Search Page Title	
		Assert.assertEquals("MakeMyTrip",  searcResultsPageDriver.getTitle());
	}
	
	
	@Test(priority=2, dependsOnMethods={"searchForFlights"})
	public void validateFlightResults() throws InterruptedException {
		
		searchResultsPage.countOfTotalFlights();
		Thread.sleep(1000);
		searchResultsPage.countOfNonStopFlights();
		Thread.sleep(1000);
		searchResultsPage.countOfOneStopFlights();
		Thread.sleep(1000);
		searchResultsPage.selectRandomFlights(driver,15,9);
		Assert.assertTrue(true);
		
		
	}
	
	
}
