package org.example.springapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanupDatabase() {
        // Clear all data after each test
        userRepository.deleteAll();
    }

    @Test
    public void UserRepository_Save(){
        //Arrange
        User user = new User("Benjamin Hocking", 20, "benjamin@hocking.com");

        //Act
        User savedUser = userRepository.save(user);

        //Assert
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void UserRepository_FindAll(){
        //Arrange
        User user1 = new User("Benjamin Hocking", 20, "benjamin@hocking.com");
        User user2 = new User("John Appleseed", 32, "john@example.com");
        User user3 = new User("Adam Smith", 61, "adams@example.com");

        //Act
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //Assert
        Assertions.assertThat(userRepository.findAll()).hasSize(3);
    }

    @Test
    public void UserRepository_FindById(){
        //Arrange
        User user1 = new User("Marcelo vega", 25, "marcelovega@example.com");

        //Act
        userRepository.save(user1);

        //Assert
        Optional<User> optionalUser = userRepository.findById(user1.getId());
        Assertions.assertThat(optionalUser.isPresent()).isTrue();
        Assertions.assertThat(optionalUser.get().getName()).isEqualTo(user1.getName());
        Assertions.assertThat(optionalUser.get().getAge()).isEqualTo(user1.getAge());
        Assertions.assertThat(optionalUser.get().getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    public void UserRepository_DeleteById(){
        //Arrange
        User user1 = new User("Marcelo vega", 25, "marcelovega@example.com");

        //Act
        userRepository.save(user1);
        userRepository.deleteById(1L);

        //Assert
        Optional<User> optionalUser = userRepository.findById(1L);
        Assertions.assertThat(optionalUser.isEmpty()).isTrue();
    }
}
