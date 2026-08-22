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
class AddingTodoItemsTest {

    @Managed
    WebDriver driver;

    TodoMvcPage todoMvcPage;

    @BeforeEach
    void resetApplicationState() {
        todoMvcPage.resetTodos();
    }

    @Test
    void shouldAddANewTodoItem() {
        todoMvcPage.addTodo("Buy some milk");

        assertThat(todoMvcPage.getTodoTitles()).containsExactly("Buy some milk");
    }

    @Test
    void shouldAddMultipleTodoItemsInOrder() {
        todoMvcPage.addTodo("Buy some milk");
        todoMvcPage.addTodo("Walk the dog");
        todoMvcPage.addTodo("Write some tests");

        assertThat(todoMvcPage.getTodoTitles())
                .containsExactly("Buy some milk", "Walk the dog", "Write some tests");
    }

    @Test
    void shouldClearTheInputFieldAfterAddingAnItem() {
        todoMvcPage.addTodo("Buy some milk");
        todoMvcPage.addTodo("Walk the dog");

        assertThat(todoMvcPage.getItemsLeftCount()).isEqualTo(2);
    }
}
