package org.example.springapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // GETs all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    //GETs all users
    @Operation(summary = "Get a user by its ID", description="Gets the user with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content={@Content(mediaType="application/json", schema= @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content=@Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        }else {
            throw new UserNotFoundException(id);
        }
    }

    // POST a new user
    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user details provided")
    })
    @PostMapping
    public User createUser(@RequestBody @Validated User user) {
        return userService.saveUser(user);
    }

    // PUT to update a user
    @PutMapping("/{id}")
    @Operation(summary = "Update a user by ID", description="Updates the user with the specified ID and returns the user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content={@Content(mediaType="application/json", schema= @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content=@Content)
    })
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id);
        if (user != null) {
            try {
                user.setName(userDetails.getName());
                user.setEmail(userDetails.getEmail());
                user.setAge(userDetails.getAge());
                userService.saveUser(user);
            }catch (Exception e) {
                throw new InvalidArgumentException();
            }
            return ResponseEntity.ok(user);
        }
        throw new UserNotFoundException(id);
    }

    // DELETE a user
    @Operation(summary = "Delete a user", description = "Deletes a user with the specified ID and returns the status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new UserNotFoundException(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}