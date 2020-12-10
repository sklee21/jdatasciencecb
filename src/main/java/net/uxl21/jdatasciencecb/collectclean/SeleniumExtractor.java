package net.uxl21.jdatasciencecb.collectclean;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import net.uxl21.jdatasciencecb.JDSRunnable;

public class SeleniumExtractor extends JDSRunnable {

	public SeleniumExtractor(String[] args) {
		super(args);
	}

	@Override
	public void run() {
		String url;
		
		if( this.params.length > 0 ) {
			url = this.params[0];
		} else {
			url = "https://www.google.com";
		}
		
		
		/*
        try {
    		ChromeDriverService.Builder builder = new ChromeDriverService.Builder();
    		builder.usingDriverExecutable(new File("D:\\20_AMAGRAMMING\\JDATASCIENCE\\chromedriver_win32\\chromedriver.exe"));
    		builder.usingAnyFreePort();
    		
    		ChromeDriverService service = builder.build();
			service.start();
			
			
			WebDriver driver;
			//driver = new FirefoxDriver();
			driver = new ChromeDriver();
			driver.get(url);
			
			WebElement webElement = driver.findElement(By.xpath("//*[@id='article']"));
			this.logger.info(webElement.getText());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		
		WebDriver driver;
		//driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.get(url);
		
		WebElement webElement = driver.findElement(By.xpath("//*[@id='article']"));
		this.logger.info(webElement.getText());
	}

}
