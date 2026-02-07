package com.project.fitness.controller;

import com.project.fitness.entity.User;
import com.project.fitness.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // GET: Получить всех
    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

    // GET: Получить одного по ID
    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // POST: Создать юзера
    @PostMapping
    public String create(@RequestBody UserRequest dto) {
        User user = new User(null, dto.username, dto.email);
        repository.save(user);
        return "User Created Successfully";
    }

    // PUT: Обновить юзера
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @RequestBody UserRequest dto) {
        User user = new User(id, dto.username, dto.email);
        repository.update(id, user);
        return "User Updated Successfully";
    }

    // DELETE: Удалить юзера
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        repository.delete(id);
        return "User Deleted Successfully";
    }

    // DTO класс для приема JSON
    static class UserRequest {
        public String username;
        public String email;
    }
}