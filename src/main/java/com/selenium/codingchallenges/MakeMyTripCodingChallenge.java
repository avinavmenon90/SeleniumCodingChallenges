package com.selenium.codingchallenges;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

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


public class MakeMyTripCodingChallenge {
	
	static WebDriver driver;
	static WebDriverWait expWait;
	static int counter;
	static WebElement nextMonthBtn,roundtripBtn,totalFlightPriceDisplayed,
	selectNumOfTravelers,numOfTravelersApplyBtn, searchBtn,nonStopBtn,oneStopBtn,
	depFlightPriceDisplayed,returnFlightPriceDisplayed;
	static List<WebElement> adultTravelers,departureFlights,returnFlights,selectReturnFlightPricesFromList,
	selectDepartureFlightsFromList,selectReturnFlightsFromList,selectDepartureFlightPricesFromList;
	static JavascriptExecutor executor;
	static Actions action;

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.edge.driver","./src/main/java/com/selenium/utilities/Browser Drivers/msedgedriver.exe");
		driver = new EdgeDriver();
		driver.get("https://www.makemytrip.com/");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	//clicking on a random section (to dismiss Login banner)
		 action = new Actions(driver);
		 action.moveToElement(driver.findElement(By.xpath("//div[@id=\"root\"]//h1[text()='Search Flights']")))
		 .doubleClick().build().perform();
		
	//click on roundtrip button
		roundtripBtn = driver.findElement(By.xpath("//div[@class='makeFlex']//li[@data-cy='roundTrip']//span[starts-with(@class,'tabsCircle')]"));
		//System.out.println("is roundtrip button enabled? "+roundtripBtn.isEnabled());
		
	//using JavaScriptExecutor to click (since regular click didn't work)	
		clickButtonUsingJS(roundtripBtn, driver);
		//executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", roundtripBtn);		
		
	//from city
		driver.findElement(By.id("fromCity")).sendKeys("Delhi");
		Thread.sleep(500);
		List<WebElement> cities = driver.findElements(By.xpath("//div[@role='listbox']//li//div//p"));
		//System.out.println("cities list size: "+cities.size());
		for(WebElement fromCity: cities) {
			if(fromCity.getText().contains("Delhi")) {
				System.out.println("Found From City: "+fromCity.getText());
				
			//using JavaScriptExecutor to click (since regular click didn't work)	
				clickButtonUsingJS(fromCity,driver);
				//JavascriptExecutor executor = (JavascriptExecutor)driver;
				//executor.executeScript("arguments[0].click();", fromCity);
				break;
			}
		}
		//Thread.sleep(5000);
		
	//to city
		driver.findElement(By.id("toCity")).sendKeys("Bengaluru");
		Thread.sleep(5000);
		cities = driver.findElements(By.xpath("//div[@role='listbox']//li//div//p"));
		//System.out.println("cities list size: "+cities.size());
		for(WebElement toCity: cities) {
			//System.out.println(toCity.getText());
			if(toCity.getText().contains("Bengaluru")) {
				System.out.println("Found To City: "+toCity.getText());
				//using JavaScriptExecutor to click (since regular click didn't work);
				clickButtonUsingJS(toCity, driver);
				//JavascriptExecutor executor = (JavascriptExecutor)driver;
				//executor.executeScript("arguments[0].click();", toCity);
				break;
			}
		}
		
//click on departure date dropdown
		 clickButtonUsingJS(driver.findElement(By.xpath("//span[text()='DEPARTURE']")), driver);
		
		//executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='DEPARTURE']")));
	
//choose departure date
		selectDate("Oct 15 2021");

// move mouse pointer outside dropdown
	 action = new Actions(driver);
	 action.moveToElement(driver.findElement(By.xpath("//div[@id=\"root\"]//h1[text()='Search Flights']")))
	 .doubleClick().build().perform();
	
	 //Thread.sleep(3000);
	 
//click on Return date dropdown
	 clickButtonUsingJS(driver.findElement(By.xpath("//span[text()='RETURN']")), driver);	
	 	//executor = (JavascriptExecutor)driver;
	 	//executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='RETURN']")));

//choose return date
	 	selectDate("Oct 20 2021");	
	 			
		Thread.sleep(1000);
		
	//select number of travellers	
		selectNumOfTravelers = driver.findElement(By.xpath("//div[@data-cy='flightTraveller']//span[text()='Travellers & CLASS']"));
		
		selectNumOfTravelers.click();
		adultTravelers = driver.findElements(By.xpath("//div[@data-cy='flightTraveller']//p[@data-cy='adultRange']/following::ul/li[starts-with(@data-cy,'adults')]"));
		numOfTravelersApplyBtn = driver.findElement(By.xpath("//button[@data-cy='travellerApplyBtn']"));
		
		//System.out.println("confirm adultTravelers list size: "+adultTravelers.size());	
		for(WebElement e: adultTravelers) {
			//System.out.println(e.getText());
			if(e.getText().contains(Integer.toString(2))) {
				 e.click();
				 numOfTravelersApplyBtn.click();
				 break;
				 }
			}
		
	//click on Search button
		searchBtn = driver.findElement(By.xpath("//a[contains(@class,'primaryBtn') or contains(@class,'widgetSearchBtn')]"));
		searchBtn.click();
		
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		
	//go to search results page
		searchResultsPage();
		
		//driver.close();
		//driver.quit();
	}
	
	
	
	
	public static void selectDate(String dateToSelect) throws InterruptedException {	
		//loop through dates
			List<WebElement> dates = driver.findElements(By.xpath("//div[starts-with(@class,'DayPicker-Day')][@aria-label]"));
			//System.out.println("number of dates on page: "+dates.size());
			counter = 1; //set counter to 1
			
			for(int i=0;i<dates.size();i++) {
				//System.out.println(dates.get(i).getAttribute("aria-label"));
				counter++;
				//System.out.println("empty? "+dt.getAttribute("aria-label").isEmpty())
					
				if ((dates.get(i).getAttribute("aria-label").contains(dateToSelect))){
					System.out.println("Found date "+dates.get(i).getAttribute("aria-label")+" in the dropdown!");
					
					clickButtonUsingJS(dates.get(i), driver);
					//executor = (JavascriptExecutor)driver;
					//executor.executeScript("arguments[0].click();", dates.get(i));
					
					break; //exit loop since date was found
				}
				
				else if(counter!=dates.size())
					continue;
				
				else {
					System.out.println("Date note found! Going to next month..");
					Thread.sleep(1000);
					nextMonthBtn = driver.findElement(By.xpath("//span[@aria-label='Next Month']"));
					//System.out.println("next btn enabled?"+nextMonthBtn.isEnabled());
					
					clickButtonUsingJS(nextMonthBtn, driver);
					
					Thread.sleep(500);
					dates = driver.findElements(By.xpath("//div[starts-with(@class,'DayPicker-Day')][@aria-label]"));
					counter = 1; //reset counter to 1 before next loop
					i=0; //reset loop variable
				}
			}
		}
	
	
	
	
	
	public static void searchResultsPage() throws InterruptedException {
		
	//scroll to end of page
		scrollToPageEnd();
		
	//count of # of departure & return flights
		System.out.println("\n"+"<--------------Total Flights-------------->");
		countOfFlights(driver);
		
	//filter Non-stop flights only
		nonStopBtn = driver.findElement(By.xpath("//div[@class='fli-filter-items']//span[contains(@class,'wdh_half')]//span[@class='check'][@data-filtertext='collapsed_stop_nonStop']"));
		//System.out.println("nonStopBtn enabled? "+nonStopBtn.isEnabled());
		
	//click on non-stop filter	
		clickButtonUsingJS(nonStopBtn, driver);
		Thread.sleep(3000);
	
	//scroll to end of page
		scrollToPageEnd();
		
    //count  # of Non-stop departure & return flights
		System.out.println("\n"+"<--------------Non-Stop Flights-------------->");
		countOfFlights(driver);
		
	//deselect non-stop filter
		clickButtonUsingJS(nonStopBtn, driver);
		Thread.sleep(1000);
	
	//filter 1-stop flights only
		oneStopBtn = driver.findElement(By.xpath("//div[@class='fli-filter-items']//span[contains(@class,'wdh_half')]//span[@class='check'][@data-filtertext='collapsed_stop_oneStop']"));
		clickButtonUsingJS(oneStopBtn, driver);
		Thread.sleep(3000);
		
	//scroll to end of page
		scrollToPageEnd();
		
	//count  # of 1-stop departure & return flights
		System.out.println("\n"+"<--------------1-Stop Flights-------------->");
		countOfFlights(driver);
	
	//deselect 1-stop filter
		clickButtonUsingJS(oneStopBtn, driver);
		Thread.sleep(3000);	
	
	//select departure & return flight by passing random integers
		selectRandomFlights(driver, 16, 10);
	}
	
	
	public static void scrollToPageEnd() {
		
		//scroll down to end of page before collecting results
				executor = (JavascriptExecutor) driver;
				
				try {
				    long lastHeight=((Number)executor.executeScript("return document.body.scrollHeight")).longValue();
				    while (true) {
				        executor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				        Thread.sleep(2000);

				        long newHeight = ((Number)executor.executeScript("return document.body.scrollHeight")).longValue();
				        if (newHeight == lastHeight)
				            break;
				        lastHeight = newHeight;
				    }
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
	}
	
	
	public static void clickButtonUsingJS(WebElement element, WebDriver driver) {
		
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
	}
	
	public static void countOfFlights(WebDriver driver) {
	
		departureFlights = driver.findElements(By.xpath("//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]"));
		returnFlights = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]"));
				
		System.out.println("Total # of departure flights: "+departureFlights.size());
		System.out.println("Total # of return flights: "+returnFlights.size());
		System.out.println();
	}
	
	public static void selectRandomFlights(WebDriver driver, int i, int j) throws InterruptedException {
		
		selectDepartureFlightsFromList = driver.findElements(By.xpath("//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='splitVw-inner']"));
		selectReturnFlightsFromList = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='splitVw-inner']"));
		selectDepartureFlightPricesFromList = driver.findElements(By.xpath("//*[@id='ow-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']"));
		selectReturnFlightPricesFromList = driver.findElements(By.xpath("//*[@id='rt-domrt-jrny']//div[starts-with(@class,'fli-list splitVw-listing')]//span[@class='actual-price']"));
		
	//select departure flight	
		clickButtonUsingJS(selectDepartureFlightsFromList.get(i), driver);
	//price of 3rd departure flight
		System.out.println("Departure Flight price for Flight #"+(i+1)+": "+selectDepartureFlightPricesFromList.get(i).getText().trim());
		
		Thread.sleep(3000);
		
	//select return flight	
		clickButtonUsingJS(selectReturnFlightsFromList.get(j), driver);
	//price of 7th departure flight
		System.out.println("Return Flight pricefor Flight #"+(j+1)+": "+selectReturnFlightPricesFromList.get(j).getText().trim());
		
		Thread.sleep(1000);
		
		depFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[@class='splitVw-footer-left']//p[@class='actual-price']"));
		//System.out.println("Checking dep flight displayed: "+depFlightPriceDisplayed.getText().trim());
		
		returnFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[@class='splitVw-footer-right']//p[@class='actual-price']"));
		//System.out.println("Checking return flight displayed: "+returnFlightPriceDisplayed.getText().trim());
		
		if((selectDepartureFlightPricesFromList.get(i).getText().trim().equalsIgnoreCase(depFlightPriceDisplayed.getText().trim())))
				System.out.println("Departure Price display match!");
		
		if((selectReturnFlightPricesFromList.get(j).getText().trim().equalsIgnoreCase(returnFlightPriceDisplayed.getText().trim())))
				System.out.println("Return Price display match!");
		
	//calculating total price & comparing to price displayed
		totalFlightPriceDisplayed = driver.findElement(By.xpath("//*[@id='listing-id']//div[starts-with(@class,'splitVw-footer-total')]//span[@class='splitVw-total-fare']"));
		Integer IntValueTotalFlightPrice = Integer.parseInt(totalFlightPriceDisplayed.getText().trim().replaceAll("[$| ]",""));
		//System.out.println("Checking Total flight displayed: "+totalFlightPriceDisplayed.getText().trim());
		
	//calculating total price 
		Integer IntValueDepartureFlightPrice = Integer.parseInt(selectDepartureFlightPricesFromList.get(i).getText().trim().replaceAll("[$| ]",""));
		Integer IntValueReturnFlightPrice = Integer.parseInt(selectReturnFlightPricesFromList.get(j).getText().trim().replaceAll("[$| ]",""));
		//System.out.println("Intvalue of Dep flight "+IntValueDepartureFlightPrice);
		//System.out.println("Intvalue of Return flight "+IntValueReturnFlightPrice);
		
		//System.out.println("Total price added up "+(IntValueDepartureFlightPrice+IntValueReturnFlightPrice));
		
		if(IntValueTotalFlightPrice == (IntValueDepartureFlightPrice+IntValueReturnFlightPrice))
				System.out.println("\n"+"Calculated Total Price matches displayed Total Price!");
	}
	
}
