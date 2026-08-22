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
class CompletingTodoItemsTest {

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
    void shouldMarkATodoItemAsCompleted() {
        todoMvcSteps.toggleTodo("Buy some milk");

        assertThat(todoMvcSteps.todoIsCompleted("Buy some milk")).isTrue();
        assertThat(todoMvcSteps.todoIsCompleted("Walk the dog")).isFalse();
    }

    @Test
    void shouldReduceTheItemsLeftCountWhenATodoIsCompleted() {
        todoMvcSteps.toggleTodo("Buy some milk");

        assertThat(todoMvcSteps.itemsLeftCount()).isEqualTo(1);
    }

    @Test
    void shouldMarkAllItemsAsCompletedUsingToggleAll() {
        todoMvcSteps.toggleAllTodos();

        assertThat(todoMvcSteps.todoIsCompleted("Buy some milk")).isTrue();
        assertThat(todoMvcSteps.todoIsCompleted("Walk the dog")).isTrue();
        assertThat(todoMvcSteps.itemsLeftCount()).isZero();
    }

    @Test
    void shouldClearCompletedItems() {
        todoMvcSteps.toggleTodo("Buy some milk");
        todoMvcSteps.clearCompletedTodos();

        assertThat(todoMvcSteps.displayedTodoTitles()).containsExactly("Walk the dog");
    }
}
