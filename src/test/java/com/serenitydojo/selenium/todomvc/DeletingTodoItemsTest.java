package com.serenitydojo.selenium.todomvc;

import com.serenitydojo.selenium.todomvc.pageobjects.TodoMvcPage;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
class DeletingTodoItemsTest {

    @Managed
    WebDriver driver;

    TodoMvcPage todoMvcPage;

    @BeforeEach
    void resetApplicationState() {
        todoMvcPage.resetTodos();
        todoMvcPage.addTodo("Buy some milk");
        todoMvcPage.addTodo("Walk the dog");
    }

    @Test
    void shouldDeleteATodoItem() {
        todoMvcPage.deleteTodo("Buy some milk");

        assertThat(todoMvcPage.getTodoTitles()).containsExactly("Walk the dog");
    }

    @Test
    void shouldLeaveTheListEmptyWhenAllItemsAreDeleted() {
        todoMvcPage.deleteTodo("Buy some milk");
        todoMvcPage.deleteTodo("Walk the dog");

        assertThat(todoMvcPage.hasNoTodos()).isTrue();
    }
}
