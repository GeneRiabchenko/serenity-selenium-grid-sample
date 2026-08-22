package com.serenitydojo.selenium.todomvc;

import com.serenitydojo.selenium.todomvc.steps.TodoMvcSteps;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
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

    @Steps
    TodoMvcSteps todoMvcSteps;

    @BeforeEach
    void resetApplicationState() {
        todoMvcSteps.startWithAnEmptyTodoList();
        todoMvcSteps.addTodo("Buy some milk");
        todoMvcSteps.addTodo("Walk the dog");
    }

    @Test
    void shouldDeleteATodoItem() {
        todoMvcSteps.deleteTodo("Buy some milk");

        assertThat(todoMvcSteps.displayedTodoTitles()).containsExactly("Walk the dog");
    }

    @Test
    void shouldLeaveTheListEmptyWhenAllItemsAreDeleted() {
        todoMvcSteps.deleteTodo("Buy some milk");
        todoMvcSteps.deleteTodo("Walk the dog");

        assertThat(todoMvcSteps.hasNoTodos()).isTrue();
    }
}
