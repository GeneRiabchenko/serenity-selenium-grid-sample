package com.serenitydojo.selenium.todomvc.steps;

import com.serenitydojo.selenium.todomvc.pageobjects.TodoMvcPage;
import net.serenitybdd.annotations.Step;

import java.util.List;

public class TodoMvcSteps {

    TodoMvcPage todoMvcPage;

    @Step("Start with an empty todo list")
    public void startWithAnEmptyTodoList() {
        todoMvcPage.resetTodos();
    }

    @Step("Add a todo item: {0}")
    public void addTodo(String title) {
        todoMvcPage.addTodo(title);
    }

    @Step("Toggle the todo item: {0}")
    public void toggleTodo(String title) {
        todoMvcPage.toggleTodo(title);
    }

    @Step("Mark all todo items as complete")
    public void toggleAllTodos() {
        todoMvcPage.toggleAll();
    }

    @Step("Delete the todo item: {0}")
    public void deleteTodo(String title) {
        todoMvcPage.deleteTodo(title);
    }

    @Step("Clear the completed todo items")
    public void clearCompletedTodos() {
        todoMvcPage.clearCompleted();
    }

    @Step("Filter by All")
    public void filterByAll() {
        todoMvcPage.filterByAll();
    }

    @Step("Filter by Active")
    public void filterByActive() {
        todoMvcPage.filterByActive();
    }

    @Step("Filter by Completed")
    public void filterByCompleted() {
        todoMvcPage.filterByCompleted();
    }

    @Step("Read the displayed todo items")
    public List<String> displayedTodoTitles() {
        return todoMvcPage.getTodoTitles();
    }

    @Step("Check whether the todo item '{0}' is completed")
    public boolean todoIsCompleted(String title) {
        return todoMvcPage.isCompleted(title);
    }

    @Step("Read the number of items left")
    public int itemsLeftCount() {
        return todoMvcPage.getItemsLeftCount();
    }

    @Step("Check whether the todo list is empty")
    public boolean hasNoTodos() {
        return todoMvcPage.hasNoTodos();
    }
}
