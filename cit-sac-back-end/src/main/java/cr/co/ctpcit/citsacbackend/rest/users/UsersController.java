package cr.co.ctpcit.citsacbackend.rest.users;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigServiceImpl;
import cr.co.ctpcit.citsacbackend.security.DaoAuthenticationProviderCstm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {


    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final DaoAuthenticationProviderCstm daoAuthenticationProvider;
    private final SystemConfigServiceImpl systemConfigServiceImplementation;

    /**
     * Crear un usuario
     *
     * @param user                 el usuario a crear según el DTO @see UserDto
     * @param uriComponentsBuilder el constructor de URI
     * @return la respuesta de la solicitud o el error que se encuentre
     */
    @PostMapping("/create-user")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserDto user,
                                           UriComponentsBuilder uriComponentsBuilder) {
        daoAuthenticationProvider.createUser(user);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/auth/{id}").buildAndExpand(user.getId()).toUri()).build();
    }

    /**
     * Obtener la lista de usuarios paginada y ordenada por email de 25 en 25 por defecto
     *
     * @param pageable la paginación
     * @return la lista de usuarios
     */
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getUsers(
            @PageableDefault(page = 0, size = 25) Pageable pageable) {
        List<UserDto> users = userDetailsServiceImpl.getUsers(pageable);
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    /**
     * Obtener un usuario por su id
     *
     * @param id el id del usuario
     * @return el usuario
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserDto user = userDetailsServiceImpl.getUser(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    /**
     * Obtener un usuario por su email
     * @param email el email del usuario
     * @return el usuario
     */
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        UserDto user = userDetailsServiceImpl.getUserByEmail(email);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    /**
     * Eliminar un usuario por su email
     * @param email el email del usuario
     * @return una respuesta sin contenido
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        userDetailsServiceImpl.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}