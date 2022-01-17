package by.itacademy.javaenterprise.borisevich.controllers;

import by.itacademy.javaenterprise.borisevich.entity.User;
import by.itacademy.javaenterprise.borisevich.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService<User> userService;

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<User>> findAll(@RequestParam("page") int page,
                                              @RequestParam("size") int size) {
        List<User> userList = userService.showAllPageByPage(page, size);
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findOne(@PathVariable("id") Long id) {
        User user = userService.showOne(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User newUser) {
        User oldUser = userService.showOne(id);
        if (oldUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldUser.setEmail(newUser.getEmail());
        oldUser.setUserPassword(newUser.getUserPassword());
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setAge(newUser.getAge());
        oldUser.setWeight(newUser.getWeight());
        oldUser.setBalanceAmount(newUser.getBalanceAmount());
        userService.save(oldUser);
        return new ResponseEntity<>(oldUser, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("resource deleted");
    }
}
