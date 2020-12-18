package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class View {

	public static final String URL = "http://localhost:8081/view.html?id=1";

	@FindBy(name = "task-input")
	private WebElement createTaskInput;

	@FindBy(xpath = "//*[@id=\"1\"]/i[1]")
	private WebElement updateTaskBtn;
	
	@FindBy(xpath = "//*[@id=\"1\"]/i[2]")
	private WebElement deleteTaskBtn;

	public void createTask(String newTask) throws InterruptedException {
		createTaskInput.sendKeys(newTask);
		Thread.sleep(3000);
		createTaskInput.submit();
	}

	public void updateTask() {
		updateTaskBtn.click();
	}

	public void deleteTask() {
		deleteTaskBtn.click();
	}

}
