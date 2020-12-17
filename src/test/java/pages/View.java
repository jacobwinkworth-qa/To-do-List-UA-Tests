package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class View {
	
public static final String URL = "http://localhost:8081/view.html";
	
	@FindBy(name = "task-input")
	private WebElement createListInput;
	
	public void createList(String newList) {
		createListInput.sendKeys(newList);
		createListInput.submit();
	}
	
	public void readTasks() {

	}
	
	public void updateTask() {

	}
	
	public void deleteTask() {
		
	}

}
