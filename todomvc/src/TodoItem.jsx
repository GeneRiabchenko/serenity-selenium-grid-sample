import React, { useEffect, useRef, useState } from 'react';

export default function TodoItem({ todo, onToggle, onDestroy, onSave }) {
  const [editing, setEditing] = useState(false);
  const [editText, setEditText] = useState(todo.title);
  const editFieldRef = useRef(null);

  useEffect(() => {
    if (editing) {
      editFieldRef.current?.focus();
    }
  }, [editing]);

  function startEditing() {
    setEditText(todo.title);
    setEditing(true);
  }

  function commitEdit() {
    const trimmed = editText.trim();
    if (trimmed.length === 0) {
      onDestroy(todo.id);
    } else {
      onSave(todo.id, trimmed);
    }
    setEditing(false);
  }

  function handleKeyDown(event) {
    if (event.key === 'Enter') {
      commitEdit();
    } else if (event.key === 'Escape') {
      setEditText(todo.title);
      setEditing(false);
    }
  }

  return (
    <li
      className={[todo.completed ? 'completed' : '', editing ? 'editing' : ''].join(' ').trim()}
      data-testid="todo-item"
    >
      <div className="view">
        <input
          className="toggle"
          type="checkbox"
          checked={todo.completed}
          onChange={() => onToggle(todo.id)}
          data-testid="todo-toggle"
        />
        <label onDoubleClick={startEditing} data-testid="todo-title">
          {todo.title}
        </label>
        <button
          className="destroy"
          onClick={() => onDestroy(todo.id)}
          data-testid="todo-destroy"
        />
      </div>
      {editing && (
        <input
          ref={editFieldRef}
          className="edit"
          value={editText}
          onChange={(event) => setEditText(event.target.value)}
          onBlur={commitEdit}
          onKeyDown={handleKeyDown}
          data-testid="todo-edit"
        />
      )}
    </li>
  );
}
