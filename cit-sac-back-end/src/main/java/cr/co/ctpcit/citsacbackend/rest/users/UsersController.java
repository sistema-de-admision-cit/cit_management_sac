package cr.co.ctpcit.citsacbackend.rest.users;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import cr.co.ctpcit.citsacbackend.security.DaoAuthenticationProviderCstm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {


    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final DaoAuthenticationProviderCstm daoAuthenticationProvider;

    /**
     * Create a user
     *
     * @param user                 the user that is going to be created by DTO @see UserDto
     * @param uriComponentsBuilder the constructor of URI
     * @return the response of the request or the error found
     */
    @PostMapping("/create-user")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto user,
                                           UriComponentsBuilder uriComponentsBuilder) {
        daoAuthenticationProvider.createUser(user);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/auth/{id}").buildAndExpand(user.getId()).toUri()).build();
    }

    /**
     * Get the list of users in order by email and paginated in 25 then other 25 by default
     *
     * @param pageable the pagination
     * @return the users list
     */
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getUsers(
            @PageableDefault(page = 0, size = 25) Pageable pageable) {
        List<UserDto> users = userDetailsServiceImpl.getUsers(pageable);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }
    /**
     * Get a user by id
     *
     * @param id users id
     * @return the user
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userDetailsServiceImpl.getUser(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
    /**
     * Get a user by email
     * @param email the users email
     * @return the user
     */
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = userDetailsServiceImpl.getUserByEmail(email);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }
    /**
     * Delete a user by email
     * @param email users email
     * @return a response without content
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        userDetailsServiceImpl.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}