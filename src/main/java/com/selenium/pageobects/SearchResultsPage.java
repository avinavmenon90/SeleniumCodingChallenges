package com.selenium.pageobects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.selenium.utilities.ElementUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]")
	private List<WebElement> departureFlights;
	
	@FindBy(xpath="//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]")
	private List<WebElement> returnFlights;
	
	@FindBy(xpath="//div[@class='fli-filter-items']//span[contains(@class,'wdh_half')]//span[@class='check'][@data-filtertext='collapsed_stop_nonStop']")
	private WebElement nonStopBtn;

	@FindBy(xpath="//div[@class='fli-filter-items']//span[contains(@class,'wdh_half')]//span[@class='check'][@data-filtertext='collapsed_stop_oneStop']")
	private WebElement oneStopBtn;
	
	@FindBy(xpath="//*[@id='listing-id']//div[@class='splitVw-footer-left']//p[@class='actual-price']")
	private WebElement depFlightPriceDisplayed;
	
	@FindBy(xpath="//*[@id='listing-id']//div[@class='splitVw-footer-right']//p[@class='actual-price']")
	private WebElement returnFlightPriceDisplayed;
	
	@FindBy(xpath="//*[@id='listing-id']//div[starts-with(@class,'splitVw-footer-total')]//span[@class='splitVw-total-fare']")
	private WebElement totalFlightPriceDisplayed;
	
	@FindBy(xpath="//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='splitVw-inner']")
	private List<WebElement> selectDepartureFlightsFromList;
	
	@FindBy(xpath="//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='splitVw-inner']")
	private List<WebElement> selectReturnFlightsFromList;
	
	@FindBy(xpath="//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']")
	private List<WebElement> selectDepartureFlightPricesFromList;
	
	@FindBy(xpath="//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']")
	private List<WebElement> selectReturnFlightPricesFromList;
	
	
	public SearchResultsPage(WebDriver driver) {
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void countOfTotalFlights() {
		
		System.out.println("\n"+"<--------------Total Flights-------------->");
		ElementUtils.scrollToPageEnd(driver);
		
		//departureFlights = driver.findElements(By.xpath("//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]"));
		//returnFlights = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]"));
				
		System.out.println("Total # of departure flights: "+departureFlights.size());
		System.out.println("Total # of return flights: "+returnFlights.size());
		System.out.println();
	}
	
	public void countOfNonStopFlights() {
		
		ElementUtils.clickButtonUsingJS(nonStopBtn, driver);
		System.out.println("\n"+"<--------------Non-Stop Flights-------------->");
		ElementUtils.scrollToPageEnd(driver);
		
		System.out.println("Total # of departure flights: "+departureFlights.size());
		System.out.println("Total # of return flights: "+returnFlights.size());
		System.out.println();
		ElementUtils.clickButtonUsingJS(nonStopBtn, driver); //deselect filter
	}
	
	public void countOfOneStopFlights() {
		
		ElementUtils.clickButtonUsingJS(oneStopBtn, driver);
		System.out.println("\n"+"<--------------One-Stop Flights-------------->");
		ElementUtils.scrollToPageEnd(driver);		
		
		System.out.println("Total # of departure flights: "+departureFlights.size());
		System.out.println("Total # of return flights: "+returnFlights.size());
		System.out.println();
		ElementUtils.clickButtonUsingJS(oneStopBtn, driver); //deselect filter
	}
	
	public void selectRandomFlights(WebDriver driver, int i, int j) throws InterruptedException {
		
		//selectDepartureFlightsFromList = driver.findElements(By.xpath());
		//selectReturnFlightsFromList = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='splitVw-inner']"));
		//selectDepartureFlightPricesFromList = driver.findElements(By.xpath("//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']"));
		//selectReturnFlightPricesFromList = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']"));
		
	//select departure flight	
		ElementUtils.clickButtonUsingJS(selectDepartureFlightsFromList.get(i), driver);
	//price of 3rd departure flight
		System.out.println("Departure Flight price for Flight #"+(i+1)+": "+selectDepartureFlightPricesFromList.get(i).getText().trim());
		
		Thread.sleep(3000);
		
	//select return flight	
		ElementUtils.clickButtonUsingJS(selectReturnFlightsFromList.get(j), driver);
	//price of 7th departure flight
		System.out.println("Return Flight pricefor Flight #"+(j+1)+": "+selectReturnFlightPricesFromList.get(j).getText().trim());
		
		Thread.sleep(1000);
		
		//depFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[@class='splitVw-footer-left']//p[@class='actual-price']"));
		//System.out.println("Checking dep flight displayed: "+depFlightPriceDisplayed.getText().trim());
		
		//returnFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[@class='splitVw-footer-right']//p[@class='actual-price']"));
		//System.out.println("Checking return flight displayed: "+returnFlightPriceDisplayed.getText().trim());
		
		if((selectDepartureFlightPricesFromList.get(i).getText().trim().equalsIgnoreCase(depFlightPriceDisplayed.getText().trim())))
				System.out.println("Departure Price display match!");
		
		if((selectReturnFlightPricesFromList.get(j).getText().trim().equalsIgnoreCase(returnFlightPriceDisplayed.getText().trim())))
				System.out.println("Return Price display match!");
		
	//calculating total price & comparing to price displayed
		//totalFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[starts-with(@class,'splitVw-footer-total')]//span[@class='splitVw-total-fare']"));
		Integer IntValueTotalFlightPrice = Integer.parseInt(totalFlightPriceDisplayed.getText().trim().replaceAll("[$| ]",""));
		System.out.println("Checking Total flight displayed: "+totalFlightPriceDisplayed.getText().trim());
		
	//calculating total price 
		Integer IntValueDepartureFlightPrice = Integer.parseInt(selectDepartureFlightPricesFromList.get(i).getText().trim().replaceAll("[$| ]",""));
		Integer IntValueReturnFlightPrice = Integer.parseInt(selectReturnFlightPricesFromList.get(j).getText().trim().replaceAll("[$| ]",""));
		//System.out.println("Intvalue of Dep flight "+IntValueDepartureFlightPrice);
		//System.out.println("Intvalue of Return flight "+IntValueReturnFlightPrice);
		
		System.out.println("Total price added up "+(IntValueDepartureFlightPrice+IntValueReturnFlightPrice));
		
		if(IntValueTotalFlightPrice == (IntValueDepartureFlightPrice+IntValueReturnFlightPrice))
				System.out.println("\n"+"Calculated Total Price matches displayed Total Price!");
	}
}
