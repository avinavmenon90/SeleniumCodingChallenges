package com.selenium.codingchallenges;

import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Open chrome/firefox browser
Launch the URL : https://www.worldometers.info/world-p...

Keep getting the count of: 
- Current World Population
- Today:  Births, Deaths and population growth today
- This Year:  Births, Deaths and population growth today

while(true){
 
 keep getting the element text using selenium
 print it on console

 //break the loop after few secs (20 secs)
}
 * 
 * 
 */


import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WorldOMetersInfoChallenge {

	static WebDriver driver;
	static String url;
	static String xpath_world_pop_count="//div[@class='maincounter-number']";
	static String xpath_births_today = "//div[@class='sec-box']/div[contains(text(),'Births today')]//following::div[@class='sec-counter']/span[@rel='births_today']//parent::div";
	static String xpath_deaths_today = "//div[@class='sec-box']/div[contains(text(),'Deaths today')]//following::div[@class='sec-counter']/span[@rel='dth1s_today']//parent::div";
	static String xpath_pop_growth_today = "//div[@class='sec-box-last']/div[contains(text(),'Population Growth today')]//following::div[@class='sec-counter']//span[@rel='absolute_growth']//parent::div";
	
	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinav\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
		
		//System.setProperty("webdriver.edge.driver", "C:\\Users\\Avinav\\Downloads\\edgedriver_win64\\msedgedriver.exe");
		//driver = new EdgeDriver();
		
		url = "https://www.worldometers.info/world-population/";
		driver.get(url);
		System.out.println("URL is: "+driver.getCurrentUrl());
		driver.manage().deleteAllCookies();
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
		//printPopulationData(xpath_world_pop_count);
		//printPopulationData(xpath_births_today);
		//printPopulationData(xpath_deaths_today);
		printPopulationData(xpath_pop_growth_today);
		
		
		driver.quit();
	}
	
	public static void printPopulationData(String xpathOfElement) throws InterruptedException {
		
		int count = 1;
		
try {
			
			while(count<=20) {
			List<WebElement> elements = driver.findElements(By.xpath(xpathOfElement));
			
			if(count==20)
				break;
			
			for(WebElement e: elements) 
				System.out.println(e.getText());
			
			Thread.sleep(1000);
			count++;
			}
		}catch(StaleElementReferenceException e) {
			System.out.println("StaleElementReferenceException thrown: "+e.getMessage());
			driver.quit();
		}
	}

}
