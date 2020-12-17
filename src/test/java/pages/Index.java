package pages;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Index {
	
	public static final String URL = "http://localhost:8081/index.html";
	
	@FindBy(name = "list-input")
	private WebElement createListInput;
	
	public void createList(String newList) {
		createListInput.sendKeys(newList);
		createListInput.submit();
	}
	
	public void readList() {
	
	}
	
	public void updateList() {
		
	}
	
	public void deleteList() {
		
		
	}
	
}
