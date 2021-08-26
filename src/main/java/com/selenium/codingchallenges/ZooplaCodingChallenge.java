package com.selenium.codingchallenges;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

/*
 *  1) go to this url -https://www.zoopla.co.uk/
2) Input location London and click submit
3) You will get a list of properties on that page. 
4) Print all the price values in sorted order (descending order) on the console
5) I need to select the 5th property on that list (its changing every minute, so it’s dynamic) 
6) On the next page, there is a logo, name and telephone no of the agent. I need to click on the name link to get into the agent’s page.
7) Once on that page, I need to check that the the properties listed there belong to the same mentioned agent on the page.
 * 
 */
public class ZooplaCodingChallenge {

	static WebDriver driver;
	static String url,agentPhone;
	static String[] agentName,agentActualName;
	static WebElement cookieBtn,searchArea,searchBtn,fifthListing,agentPropertiesLink; 
	static List<Integer> priceListInInt = new ArrayList<Integer>();
	static List<String> priceListInString = new ArrayList<String>();
	
	public static void main(String[] args) throws InterruptedException {

		
		System.setProperty("webdriver.edge.driver","C:\\Users\\Avinav\\Downloads\\edgedriver_win64\\msedgedriver.exe"); 
		driver =  new EdgeDriver();
		 
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Avinav\\Downloads\\chromedriver.exe");
		//driver = new ChromeDriver();
		
		url = "https://www.zoopla.co.uk/";
		driver.get(url);
		System.out.println("URL is: "+driver.getCurrentUrl());
		driver.manage().deleteAllCookies();
		System.out.println("page title:"+driver.getTitle());
		System.out.println("<--------------------------------------------------------->");
		//cookieBtn = driver.findElement(By.className("ui-button-primary ui-cookie-accept-all-medium-large"));
		cookieBtn = driver.findElement(By.xpath("//div[@class='ui-cookie-consent-choose__buttons']/button[text()='Accept all cookies']"));
		
		cookieBtn.click(); //dismiss cookie msg
		Thread.sleep(1000);
		
		searchArea = driver.findElement(By.id("header-location"));
		searchArea.sendKeys("Birmingham");
		
		searchBtn = driver.findElement(By.xpath("//button[@data-testid='search-button']"));
		searchBtn.click();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
/* Print all the price values in sorted order (descending order) on the console	*/
		List<WebElement> prices = driver.findElements(By.xpath("//div[starts-with(@data-testid,'search-result_listing')]//p[starts-with(text(),'£')]"));
			for(WebElement p: prices)
				priceListInInt.add(Integer.parseInt(p.getText().replaceAll("[£|,]","")));

			Collections.sort(priceListInInt,Collections.reverseOrder());
			
			NumberFormat uknf = NumberFormat.getCurrencyInstance(Locale.UK);
			
			for(Integer i: priceListInInt) 
				System.out.println(uknf.format(i).replace(".00", ""));
			

			System.out.println("<--------------------------------------------------------->");

/* Click on 5th listing and capture Agent info */
			
			fifthListing = driver.findElement(By.xpath("//div[5][starts-with(@data-testid,'search-result_listing')]//a[@data-testid='listing-details-link']"));
			fifthListing.click();
			System.out.println("new page URL: "+driver.getCurrentUrl());
			
			agentName = driver.findElement(By.xpath("//div[@data-testid='agent-details']//p[contains(@class,'AgentName')]")).getText().split("\\n");
			agentPhone = driver.findElement(By.xpath("//div[@data-testid='agent-details']//div[contains(@class,'AgentContact')]//a[@data-testid='agent-phone-cta-link']")).getText();
			System.out.println("Agent name is: "+agentName[0]+" and Phone Number is: "+agentPhone);

		
/* Click on the Agent Logo to go to the Agent page and verify it's the same agent*/
			
			agentPropertiesLink = driver.findElement(By.xpath("//div[@data-testid='agent-details']//p[contains(@class,'AgentName')]//a[@data-testid='agent-properties-link']"));
			agentPropertiesLink.click();
			Thread.sleep(3000);
			
			//System.out.println(driver.getPageSource().toString());
			
			if(driver.getPageSource().toString().contains(agentName[0]))
				System.out.println("Verified Agent name");
			else
				System.out.println("Something's fishy..agent name doesnt match!");
			
			//agentActualName = driver.findElement(By.xpath("//div[@id='main-content']//div[@id='content']/div[@class='clearfix']//a[contains(@href,'find-agents/company')]")).getText().split(",");
			//System.out.println("Agent ACTUAL name is: "+agentActualName[0]);
			
		/*
		 * if(agentName[0].equalsIgnoreCase(agentActualName[0]))
		 * System.out.println("Verified Agent name"); else
		 * System.out.println("Something's fishy..agent name doesnt match!");
		 */
			Thread.sleep(1000);
			driver.close();
			driver.quit();

		}
			
}
