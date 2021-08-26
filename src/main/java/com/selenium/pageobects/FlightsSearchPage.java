package com.selenium.pageobects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.selenium.utilities.ElementUtils;

public class FlightsSearchPage {
	
	private WebDriver driver;
	private Actions action;
	private WebElement numOfTravelersApplyBtn;
	private List<WebElement> adultTravelers, cities;
	
	@FindBy(xpath="//div[@class='makeFlex']//li[@data-cy='roundTrip']//span[starts-with(@class,'tabsCircle')]")
	private WebElement roundtripBtn;
	
	@FindBy(id="fromCity")
	private WebElement fromCity;
	
	@FindBy(id="toCity")
	private WebElement toCity;

	@FindBy(id="")
	private WebElement departureDate;
	
	@FindBy(xpath="//span[text()='DEPARTURE']")
	private WebElement departureDateDropdown;
	
	@FindBy(xpath="//span[text()='RETURN']")
	private WebElement returnDateDropdown;
	
	@FindBy(xpath="//div[@data-cy='flightTraveller']")
	private WebElement numOfTravellersDropdown;
	
	@FindBy(xpath="//a[contains(@class,'primaryBtn') or contains(@class,'widgetSearchBtn')]")
	WebElement searchBtn;

	public FlightsSearchPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void dismissLoginBanner() {
	//clicking on a random section (to dismiss Login banner)
		 action = new Actions(driver);
		 action.moveToElement(driver.findElement(By.xpath("//div[@id=\"root\"]//h1[text()='Search Flights']")))
		 .doubleClick().build().perform();	
		 System.out.println("successful double-click!");
	}
	
	public void selectRoundTrip() {
		
		System.out.println("selecting Round Trip");
		//roundtripBtn = driver.findElement(By.xpath("//div[@class='makeFlex']//li[@data-cy='roundTrip']//span[starts-with(@class,'tabsCircle')]"));
		ElementUtils.clickButtonUsingJS(roundtripBtn, driver);
	}
	
	public void fromDestination(String fromDest) throws InterruptedException {

		//from city
			//fromCity = driver.findElement(By.xpath("//*[@id='fromCity']"));
			fromCity.sendKeys(fromDest);
			Thread.sleep(500);
			cities = driver.findElements(By.xpath("//div[@role='listbox']//li//div//p"));
			//System.out.println("cities list size: "+cities.size());
			for(WebElement fromCity: cities) {
				if(fromCity.getText().contains("Delhi")) {
					System.out.println("Found From City: "+fromCity.getText());
					
				//using JavaScriptExecutor to click (since regular click didn't work)	
				  ElementUtils.clickButtonUsingJS(fromCity,driver);
				//JavascriptExecutor executor = (JavascriptExecutor)driver;
				//executor.executeScript("arguments[0].click();", fromCity);
				  break;
				}
			}
	}
	
	public void toDestination(String toDest) throws InterruptedException {
	
		//to city
		    //toCity = driver.findElement(By.xpath("//*[@id='toCity']"));
			toCity.sendKeys(toDest);
			Thread.sleep(500);
			cities = driver.findElements(By.xpath("//div[@role='listbox']//li//div//p"));
	//System.out.println("cities list size: "+cities.size());
			for(WebElement toCity: cities) {
				//System.out.println(toCity.getText());
				if(toCity.getText().contains("Bengaluru")) {
					System.out.println("Found To City: "+toCity.getText());
					//using JavaScriptExecutor to click (since regular click didn't work);
					ElementUtils.clickButtonUsingJS(toCity, driver);
					//JavascriptExecutor executor = (JavascriptExecutor)driver;
					//executor.executeScript("arguments[0].click();", toCity);
					break;
				}
			}
	}

	public void departureDate(String depDate) throws InterruptedException {
		
		/*
		 * String[] depDate = date.split("-"); String depDay = depDate[0]; String
		 * depMonth = depDate[1]; String depYear = depDate[2];
		 */
		//click on departure date dropdown
		ElementUtils.clickButtonUsingJS(departureDateDropdown, driver);
		
		//choose departure date
		ElementUtils.selectDate(depDate,driver);
	}
	
	public void returnDate(String retnDate) throws InterruptedException {
		
		//click on return date dropdown
		ElementUtils.clickButtonUsingJS(returnDateDropdown, driver);
				
		//choose return date
		ElementUtils.selectDate(retnDate,driver);
	}
	
	public void numberOfTravellers(int travelersNum) {
		
	//click on travellers dropdown	
		numOfTravellersDropdown.click();
	
	//select travellers and apply	
		adultTravelers = driver.findElements(By.xpath("//div[@data-cy='flightTraveller']//p[@data-cy='adultRange']/following::ul/li[starts-with(@data-cy,'adults')]"));
		numOfTravelersApplyBtn = driver.findElement(By.xpath("//button[@data-cy='travellerApplyBtn']"));
		//System.out.println("confirm adultTravelers list size: "+adultTravelers.size());	
		for(WebElement e: adultTravelers) {
			//System.out.println(e.getText());
			if(e.getText().contains(Integer.toString(travelersNum))) {
				 e.click();
				 numOfTravelersApplyBtn.click();
				 break;
				 }
			}
	}
	
	public WebDriver searchFlights() {
		searchBtn.click();
		
	//wait for Search Results page to finish loading	
		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
				
		return driver;
	}
	
	
	
	
	
}
