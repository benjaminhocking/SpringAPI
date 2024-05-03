package org.example.springapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_CreateUser(){
        User user = new User("Benjamin Hocking", 20, "benjamin@hocking.com");

        //replace calls to userRepository.save() with returning user
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void UserService_GetAllUsers() {
        // Arrange
        User user1 = new User("Benjamin Hocking", 20, "user1@example.com");
        User user2 = new User("James Smith", 31, "user2@example.com");
        List<User> expectedUsers = Arrays.asList(user1, user2);

        //replace calls to userRepository.findAll() with returning expectedUsers
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll(); // Verify findAll was called on userRepository
        assertNotNull("The returned list should not be null", actualUsers);
        assertEquals("The returned list size should match expected", expectedUsers.size(), actualUsers.size());
        assertEquals("The lists of users should match", expectedUsers, actualUsers);
    }

    @Test
    public void UserService_GetUserByID_UserExists() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User("Benjamin Hocking", 20, "user1@example.com");

        //replace calls to userRepository.findById() with returning an optional of expectedUser
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUserById(userId);

        // Assert
        verify(userRepository).findById(userId); // Verify findById was called on userRepository
        assertNotNull("User should not be null when exists", actualUser);
        assertEquals("Returned user should match the expected", expectedUser, actualUser);
    }

    @Test
    public void UserService_GetUserByID_UserDoesNotExist() {
        // Arrange
        Long userId = 2L;

        //When userRepository.findById(userId) happens, instead of doing what is normally done, return an empty optional.
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        User actualUser = userService.getUserById(userId);

        // Assert
        verify(userRepository).findById(userId); // Verify findById was called on userRepository
        assertNull("User should be null when not found", actualUser);
    }

    @Test
    public void UserService_DeleteUser() {
        // Verify that userRepository.deleteById is called with the correct argument
        userService.deleteUser(1L);
        Mockito.verify(userRepository).deleteById(1L);
    }


}
