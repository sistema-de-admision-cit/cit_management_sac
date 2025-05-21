package cr.co.ctpcit.citsacbackend.logic.services.auth;

import cr.co.ctpcit.citsacbackend.data.enums.Role;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "sysadmin@ctpcit.co.cr";
        String adminUsername = "SysAdmin";

        if (!userDetailsManager.userExists(adminEmail)) {
            UserDto adminUser = UserDto.builder()
                    .email(adminEmail)
                    .realUsername(adminUsername)
                    .password(passwordEncoder.encode("2150Mnr$"))
                    .role(Role.SYS)
                    .isDefaultPassword(true)
                    .build();
            userDetailsManager.createUser(adminUser);
        }
    }
}
