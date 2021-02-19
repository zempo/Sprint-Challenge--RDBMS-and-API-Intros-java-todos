package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "todoService")
public class TodoServiceImpl implements TodosService {

    @Autowired
    private TodoRepository todorepos;

    @Override
    public void markComplete(long todoid) {
        Todos myTodo = todorepos.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo Not Found"));

        myTodo.setCompleted(true);
    }
}
