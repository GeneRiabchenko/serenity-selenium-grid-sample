package com.serenitydojo.selenium.todomvc.tests;

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
class AddingTodoItemsTest {

    @Managed
    WebDriver driver;

    @Steps
    TodoMvcSteps todoMvcSteps;

    @BeforeEach
    void resetApplicationState() {
        todoMvcSteps.startWithAnEmptyTodoList();
    }

    @Test
    void shouldAddANewTodoItem() {
        todoMvcSteps.addTodo("Buy some milk");

        assertThat(todoMvcSteps.displayedTodoTitles()).containsExactly("Buy some milk");
    }

    @Test
    void shouldAddMultipleTodoItemsInOrder() {
        todoMvcSteps.addTodo("Buy some milk");
        todoMvcSteps.addTodo("Walk the dog");
        todoMvcSteps.addTodo("Write some tests");

        assertThat(todoMvcSteps.displayedTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }

    @Test
    void shouldClearTheInputFieldAfterAddingAnItem() {
        todoMvcSteps.addTodo("Buy some milk");
        todoMvcSteps.addTodo("Walk the dog");

        assertThat(todoMvcSteps.itemsLeftCount()).isEqualTo(2);
    }
}
