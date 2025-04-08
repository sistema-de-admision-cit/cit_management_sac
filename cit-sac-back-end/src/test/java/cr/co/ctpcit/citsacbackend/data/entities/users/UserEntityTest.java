package cr.co.ctpcit.citsacbackend.data.entities.users;

import cr.co.ctpcit.citsacbackend.data.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserEntityTest {
  @Autowired
  private JacksonTester<UserEntity> json;

  @Test
  void testSerialize() throws Exception {
    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setEmail("sysadmin@cit.co.cr");
    user.setUsername("Sysadmin");
    user.setPassword("$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S");
    user.setRole(Role.SYS);

    assertThat(json.write(user)).isStrictlyEqualToJson("UserEntityJsonExpected.json");
    assertThat(json.write(user)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(user)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(user)).hasJsonPathStringValue("@.email");
    assertThat(json.write(user)).extractingJsonPathStringValue("@.email")
        .isEqualTo("sysadmin@cit.co.cr");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
          "id": 1,
          "email": "sysadmin@cit.co.cr",
          "username": "Sysadmin",
          "password": "$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S",
          "role": "SYS"
        }
        """;

    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setEmail("sysadmin@cit.co.cr");
    user.setUsername("Sysadmin");
    user.setPassword("$2a$10$x2PgQcVgktD6SS6wtJonwOlWpnLj24aH9c5aVC561vDqTO8PzUY4S");
    user.setRole(Role.SYS);

    assertThat(json.parse(expected)).isEqualTo(user);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getEmail()).isEqualTo("sysadmin@cit.co.cr");

  }
}
