import React, { useEffect, useMemo, useState } from 'react';
import TodoItem from './TodoItem.jsx';

const STORAGE_KEY = 'todos-react-serenity';

function loadTodos() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : [];
  } catch {
    return [];
  }
}

function currentFilter() {
  const hash = window.location.hash.replace('#', '');
  if (hash === '/active' || hash === '/completed') {
    return hash.slice(1);
  }
  return 'all';
}

let nextId = 1;

export default function TodoApp() {
  const [todos, setTodos] = useState(loadTodos);
  const [newTodo, setNewTodo] = useState('');
  const [filter, setFilter] = useState(currentFilter);

  useEffect(() => {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(todos));
  }, [todos]);

  useEffect(() => {
    const onHashChange = () => setFilter(currentFilter());
    window.addEventListener('hashchange', onHashChange);
    return () => window.removeEventListener('hashchange', onHashChange);
  }, []);

  function addTodo(title) {
    const trimmed = title.trim();
    if (trimmed.length === 0) return;
    setTodos((prev) => [...prev, { id: nextId++, title: trimmed, completed: false }]);
    setNewTodo('');
  }

  function toggleTodo(id) {
    setTodos((prev) =>
      prev.map((todo) => (todo.id === id ? { ...todo, completed: !todo.completed } : todo)),
    );
  }

  function toggleAll(completed) {
    setTodos((prev) => prev.map((todo) => ({ ...todo, completed })));
  }

  function destroyTodo(id) {
    setTodos((prev) => prev.filter((todo) => todo.id !== id));
  }

  function saveTodo(id, title) {
    setTodos((prev) => prev.map((todo) => (todo.id === id ? { ...todo, title } : todo)));
  }

  function clearCompleted() {
    setTodos((prev) => prev.filter((todo) => !todo.completed));
  }

  const visibleTodos = useMemo(() => {
    if (filter === 'active') return todos.filter((todo) => !todo.completed);
    if (filter === 'completed') return todos.filter((todo) => todo.completed);
    return todos;
  }, [todos, filter]);

  const activeCount = todos.filter((todo) => !todo.completed).length;
  const completedCount = todos.length - activeCount;
  const allCompleted = todos.length > 0 && activeCount === 0;

  return (
    <section className="todoapp" data-testid="todoapp">
      <header className="header">
        <h1>todos</h1>
        <input
          className="new-todo"
          placeholder="What needs to be done?"
          autoFocus
          value={newTodo}
          onChange={(event) => setNewTodo(event.target.value)}
          onKeyDown={(event) => {
            if (event.key === 'Enter') addTodo(newTodo);
          }}
          data-testid="new-todo"
        />
      </header>

      {todos.length > 0 && (
        <section className="main">
          <input
            id="toggle-all"
            className="toggle-all"
            type="checkbox"
            checked={allCompleted}
            onChange={(event) => toggleAll(event.target.checked)}
            data-testid="toggle-all"
          />
          <label htmlFor="toggle-all">Mark all as complete</label>
          <ul className="todo-list" data-testid="todo-list">
            {visibleTodos.map((todo) => (
              <TodoItem
                key={todo.id}
                todo={todo}
                onToggle={toggleTodo}
                onDestroy={destroyTodo}
                onSave={saveTodo}
              />
            ))}
          </ul>
        </section>
      )}

      {todos.length > 0 && (
        <footer className="footer" data-testid="footer">
          <span className="todo-count" data-testid="todo-count">
            <strong>{activeCount}</strong> item{activeCount === 1 ? '' : 's'} left
          </span>
          <ul className="filters">
            <li>
              <a className={filter === 'all' ? 'selected' : ''} href="#/" data-testid="filter-all">
                All
              </a>
            </li>
            <li>
              <a
                className={filter === 'active' ? 'selected' : ''}
                href="#/active"
                data-testid="filter-active"
              >
                Active
              </a>
            </li>
            <li>
              <a
                className={filter === 'completed' ? 'selected' : ''}
                href="#/completed"
                data-testid="filter-completed"
              >
                Completed
              </a>
            </li>
          </ul>
          {completedCount > 0 && (
            <button
              className="clear-completed"
              onClick={clearCompleted}
              data-testid="clear-completed"
            >
              Clear completed
            </button>
          )}
        </footer>
      )}
    </section>
  );
}
