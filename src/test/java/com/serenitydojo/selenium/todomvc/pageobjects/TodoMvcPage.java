package com.serenitydojo.selenium.todomvc.pageobjects;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

@DefaultUrl("http://app")
public class TodoMvcPage extends PageObject {

    private static final By NEW_TODO = By.cssSelector("[data-testid='new-todo']");
    private static final By TODO_ITEMS = By.cssSelector("[data-testid='todo-item']");
    private static final By TODO_TITLE = By.cssSelector("[data-testid='todo-title']");
    private static final By TODO_TOGGLE = By.cssSelector("[data-testid='todo-toggle']");
    private static final By TODO_DESTROY = By.cssSelector("[data-testid='todo-destroy']");
    private static final By TODO_COUNT = By.cssSelector("[data-testid='todo-count'] strong");
    private static final By TOGGLE_ALL = By.cssSelector("[data-testid='toggle-all']");
    private static final By CLEAR_COMPLETED = By.cssSelector("[data-testid='clear-completed']");
    private static final By FILTER_ALL = By.cssSelector("[data-testid='filter-all']");
    private static final By FILTER_ACTIVE = By.cssSelector("[data-testid='filter-active']");
    private static final By FILTER_COMPLETED = By.cssSelector("[data-testid='filter-completed']");

    public TodoMvcPage(WebDriver driver) {
        super(driver);
    }

    public void resetTodos() {
        open();
        evaluateJavascript("window.localStorage.clear();");
        getDriver().navigate().refresh();
    }

    public void addTodo(String title) {
        $(NEW_TODO).waitUntilVisible().typeAndEnter(title);
    }

    public List<String> getTodoTitles() {
        return $$(TODO_ITEMS).stream()
                .map(item -> item.find(TODO_TITLE).getText())
                .collect(Collectors.toList());
    }

    public void toggleTodo(String title) {
        findTodoItem(title).find(TODO_TOGGLE).click();
    }

    public boolean isCompleted(String title) {
        String cssClass = findTodoItem(title).getAttribute("class");
        return cssClass != null && cssClass.contains("completed");
    }

    public void deleteTodo(String title) {
        findTodoItem(title).find(TODO_DESTROY).click();
    }

    public void toggleAll() {
        $(TOGGLE_ALL).click();
    }

    public void clearCompleted() {
        $(CLEAR_COMPLETED).click();
    }

    public void filterByAll() {
        $(FILTER_ALL).click();
    }

    public void filterByActive() {
        $(FILTER_ACTIVE).click();
    }

    public void filterByCompleted() {
        $(FILTER_COMPLETED).click();
    }

    public int getItemsLeftCount() {
        return Integer.parseInt($(TODO_COUNT).getText());
    }

    public boolean hasNoTodos() {
        return $$(TODO_ITEMS).isEmpty();
    }

    private WebElementFacade findTodoItem(String title) {
        return $$(TODO_ITEMS).stream()
                .filter(item -> item.find(TODO_TITLE).getText().equals(title))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No todo item found with title: " + title));
    }
}
