	import java.sql.Connection;
	import java.sql.*;
	import java.util.List;
	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.support.ui.Select;

	public class AmazonSearch {

		public static void main(String[] args) {
			
			String category = null;
			String term = null;
			
			System.setProperty("webdriver.chrome.driver", "C:\\\\BrowserDrivers\\\\chromedriver.exe");
//			WebDriverManager.chromedriver().setup();
			WebDriver drivers = new ChromeDriver();
			
			drivers.get("https://www.amazon.in/");
			
			drivers.manage().window().maximize();
			
			drivers.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce","root","1234");
				Statement stmn=con.createStatement();
				
				ResultSet rs=stmn.executeQuery("select * from amazon");
				while(rs.next()){
					//System.out.println(rs.getString(2)+ " "+rs.getString(3));
					category=rs.getString(2);
					term=rs.getString(3);
				}	
			}
			catch(ClassNotFoundException e)	{
				System.out.println("Class Not Found");
			}
			catch (SQLException e) {
				System.out.println("SQL Exception");
			}
			
			
			
			WebElement searchDD = drivers.findElement(By.xpath("//*[@id='searchDropdownBox']"));
			Select optionToSelect = new Select(searchDD);
			optionToSelect.selectByVisibleText(category);

			WebElement searched= drivers.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
			searched.sendKeys(term);
			
			WebElement searchButton=drivers.findElement(By.xpath("//*[@id='nav-search-submit-button']"));
			searchButton.click();
			
			List<WebElement> results = drivers.findElements(By.xpath("//h2[@class='a-size-mini a-spacing-none a-color-base s-line-clamp-2']"));
			System.out.println("Total Results are: " + results.size());
			
			for(WebElement e : results) {
				  System.out.println(e.getText());
			}
			drivers.close();

		}

	}
