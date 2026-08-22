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
class CompletingTodoItemsTest {

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
    void shouldMarkATodoItemAsCompleted() {
        todoMvcPage.toggleTodo("Buy some milk");

        assertThat(todoMvcPage.isCompleted("Buy some milk")).isTrue();
        assertThat(todoMvcPage.isCompleted("Walk the dog")).isFalse();
    }

    @Test
    void shouldReduceTheItemsLeftCountWhenATodoIsCompleted() {
        todoMvcPage.toggleTodo("Buy some milk");

        assertThat(todoMvcPage.getItemsLeftCount()).isEqualTo(1);
    }

    @Test
    void shouldMarkAllItemsAsCompletedUsingToggleAll() {
        todoMvcPage.toggleAll();

        assertThat(todoMvcPage.isCompleted("Buy some milk")).isTrue();
        assertThat(todoMvcPage.isCompleted("Walk the dog")).isTrue();
        assertThat(todoMvcPage.getItemsLeftCount()).isZero();
    }

    @Test
    void shouldClearCompletedItems() {
        todoMvcPage.toggleTodo("Buy some milk");
        todoMvcPage.clearCompleted();

        assertThat(todoMvcPage.getTodoTitles()).containsExactly("Walk the dog");
    }
}
