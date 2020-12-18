package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Index {
	
	public static final String URL = "http://localhost:8081/index.html";
	
	@FindBy(name = "list-input")
	private WebElement createListInput;
	
	@FindBy(xpath = "//*[@id=\"1\"]/div/a")
	private WebElement readListAnchor;
	
	@FindBy(xpath = "//*[@id=\"1\"]/i[1]")
	private WebElement updateListBtn;
	
	@FindBy(xpath = "//*[@id=\"1\"]/i[2]")
	private WebElement deleteListBtn;
	
	public void createList(String newList) {
		createListInput.sendKeys(newList);
		createListInput.submit();
	}
	
	public void readList() {
		readListAnchor.click();
		
	}
	
	public void updateList() {
		updateListBtn.click();
	}
	
	public void deleteList() {
		deleteListBtn.click();		
	}
	
}
