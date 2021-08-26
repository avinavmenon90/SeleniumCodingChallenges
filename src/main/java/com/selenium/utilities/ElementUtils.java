package com.selenium.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {
	
	WebDriver driver;
	private static JavascriptExecutor executor;
	private static XSSFCell cell;
	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFRow row1;
	private static int totCols,totRows; 
	private static File file;
	private static FileInputStream fis;
	
	public ElementUtils(WebDriver driver) {
		super();
		this.driver = driver;
	} 
	
	public static void loadDataFromExcelFile() throws IOException {
		
		file = new File("E:\\TestData\\TestData.xls");
		fis = new FileInputStream(file);
		wb=new XSSFWorkbook(fis);
		sheet=wb.getSheet("STUDENT_DATA");
		//XSSFSheet sheet1=wb.getSheetAt(1);
		row1= sheet.getRow(1);
		totCols = row1.getLastCellNum();
		totRows = ((sheet.getLastRowNum()) - (sheet.getFirstRowNum()));
		
		for(int i=0;i<=totRows;i++) {
			
			System.out.println("Row "+i+" data is: ");
			for(int j=0;j<=totCols;j++) {
				
				cell = sheet.getRow(i).getCell(j);
				System.out.println(cell.getStringCellValue()+",");
			}
			System.out.println();
		}
		
	}
	
	public static void clickButtonUsingJS(WebElement element, WebDriver driver) {
		
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		
	}
	
	public static void scrollToPageEnd(WebDriver driver) {
			
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
	
	
	
}
