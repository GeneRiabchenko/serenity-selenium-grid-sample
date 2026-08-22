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
class FilteringTodoItemsTest {

    @Managed
    WebDriver driver;

    TodoMvcPage todoMvcPage;

    @BeforeEach
    void resetApplicationState() {
        todoMvcPage.resetTodos();
        todoMvcPage.addTodo("Buy some milk");
        todoMvcPage.addTodo("Walk the dog");
        todoMvcPage.addTodo("Write some tests");
        todoMvcPage.toggleTodo("Walk the dog");
    }

    @Test
    void shouldShowAllItemsByDefault() {
        assertThat(todoMvcPage.getTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }

    @Test
    void shouldShowOnlyActiveItems() {
        todoMvcPage.filterByActive();

        assertThat(todoMvcPage.getTodoTitles())
                .containsExactly("Buy some milk", "Write some tests");
    }

    @Test
    void shouldShowOnlyCompletedItems() {
        todoMvcPage.filterByCompleted();

        assertThat(todoMvcPage.getTodoTitles()).containsExactly("Walk the dog");
    }

    @Test
    void shouldReturnToAllItemsAfterFilteringByActiveAndCompleted() {
        todoMvcPage.filterByActive();
        todoMvcPage.filterByCompleted();
        todoMvcPage.filterByAll();

        assertThat(todoMvcPage.getTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }
}
