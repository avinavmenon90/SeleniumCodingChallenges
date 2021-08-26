package com.selenium.codingchallenges;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

/*
 * Open Chrome/Firefox
Launch app : https://www.noon.com/uae-en/

Get the carousel items from:
 Recommended For You
 Sports shoes under 199 AED
 Top picks in laptops
 Limited time offers
 Bestselling fragrances

You need to create a generic function, this will take the sectionName(String) as an input parameter and will return the collection of all the carousel items text in sorted order only.

You need to keep clicking on the next arrow icon until its disappeared and keep fetching the text of each item in carousel.
 */

public class HandlingCarouselChallenge {
	
	static WebDriver driver;
	static String url;
	static String Recommended_for_you_Carousel;
	static String Trending_Deals_Carousel;
	static String Top_Laptop_Picks;

	public static void main(String[] args) throws InterruptedException {

		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinav\\Downloads\\chromedriver.exe");
		//driver = new ChromeDriver();
		System.setProperty("webdriver.edge.driver", "C:\\Users\\Avinav\\Downloads\\edgedriver_win64\\msedgedriver.exe");
		driver = new EdgeDriver();
		
		url = "https://www.noon.com/uae-en/";
		
		driver.get(url);
		System.out.println("URL is: "+driver.getCurrentUrl());
		driver.manage().deleteAllCookies();
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//String recommended_for_you = "//h3[text()='Recommended for you']//following::div";
		Recommended_for_you_Carousel="//h3[text()='Recommended for you']/following::div[1][@class='sc-eGCarw jqTxQZ']//div[@title]";
		Trending_Deals_Carousel = "//h3[text()='Trending deals']/following::div[@class='sc-eGCarw jqTxQZ']//div[@title]";
		Top_Laptop_Picks = "//h3[text()='Top picks in laptops']/following::div[@class='sc-eGCarw jqTxQZ']//div[@title]";
		getCarouselProducts(Recommended_for_you_Carousel);
		System.out.println("<------------------------END OF CAROUSEL--------------------->");
		//getCarouselProducts(Trending_Deals_Carousel);
		System.out.println("<------------------------END OF CAROUSEL--------------------->");
		//getCarouselProducts(Top_Laptop_Picks);
		
	     driver.close();
		 driver.quit();
		

	}
	
	public static void getCarouselProducts(String carouselName) {
		
		List<WebElement> carousel = driver.findElements(By.xpath(carouselName));
		System.out.println("carousel obj: "+carousel.toString());
		
		try {
			System.out.println("total items in"+carouselName+": "+carousel.size());
			for(int i=0;i<carousel.size();i++){
				 System.out.println(carousel.get(i).getText().trim());
			 if((i+1)%7==0) {
				 
			 }
				 
				 
			 }
		}catch(StaleElementReferenceException e) {
			System.out.println("StaleElementReferenceException thrown: "+e.getMessage());
			driver.close();
			driver.quit();
			}
	}

}
