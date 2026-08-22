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
class FilteringTodoItemsTest {

    @Managed
    WebDriver driver;

    @Steps
    TodoMvcSteps todoMvcSteps;

    @BeforeEach
    void resetApplicationState() {
        todoMvcSteps.startWithAnEmptyTodoList();
        todoMvcSteps.addTodo("Buy some milk");
        todoMvcSteps.addTodo("Walk the dog");
        todoMvcSteps.addTodo("Write some tests");
        todoMvcSteps.toggleTodo("Walk the dog");
    }

    @Test
    void shouldShowAllItemsByDefault() {
        assertThat(todoMvcSteps.displayedTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }

    @Test
    void shouldShowOnlyActiveItems() {
        todoMvcSteps.filterByActive();

        assertThat(todoMvcSteps.displayedTodoTitles())
                .containsExactly("Buy some milk", "Write some tests");
    }

    @Test
    void shouldShowOnlyCompletedItems() {
        todoMvcSteps.filterByCompleted();

        assertThat(todoMvcSteps.displayedTodoTitles()).containsExactly("Walk the dog");
    }

    @Test
    void shouldReturnToAllItemsAfterFilteringByActiveAndCompleted() {
        todoMvcSteps.filterByActive();
        todoMvcSteps.filterByCompleted();
        todoMvcSteps.filterByAll();

        assertThat(todoMvcSteps.displayedTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }
}
