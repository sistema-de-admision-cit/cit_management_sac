package cr.co.ctpcit.citsacbackend.rest.users;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import cr.co.ctpcit.citsacbackend.security.DaoAuthenticationProviderCstm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsersControllerUnitTest {

    @Mock
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private DaoAuthenticationProviderCstm daoAuthenticationProvider;

    @InjectMocks
    private UsersController usersController;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = UserDto.builder()
                .id(1L)
                .username("marcos@ctpcit.co.cr")
                .password("campus")
                .role(cr.co.ctpcit.citsacbackend.data.enums.Role.S)
                .build();
    }


    @Test
    void testCreateUser() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost");
        doNothing().when(daoAuthenticationProvider).createUser(any(UserDto.class));
        ResponseEntity<Void> response = usersController.createUser(userDto, uriBuilder);
        verify(daoAuthenticationProvider, times(1)).createUser(any(UserDto.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetUsers() {
        Pageable pageable = PageRequest.of(0, 25);
        List<UserDto> userList = List.of(userDto);

        when(userDetailsServiceImpl.getUsers(pageable)).thenReturn(userList);
        ResponseEntity<Iterable<UserDto>> response = usersController.getUsers(pageable);
        verify(userDetailsServiceImpl, times(1)).getUsers(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, ((List<UserDto>) response.getBody()).size());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        when(userDetailsServiceImpl.getUser(userId)).thenReturn(userDto);
        ResponseEntity<UserDto> response = usersController.getUser(userId);
        verify(userDetailsServiceImpl, times(1)).getUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testGetUserByEmail() {
        String email = "marcos@ctpcit.co.cr";
        when(userDetailsServiceImpl.getUserByEmail(email)).thenReturn(userDto);
        ResponseEntity<UserDto> response = usersController.getUserByEmail(email);
        verify(userDetailsServiceImpl, times(1)).getUserByEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testDeleteUser() {
        String email = "marcos@cptcit.co.cr";
        doNothing().when(userDetailsServiceImpl).deleteUser(email);
        ResponseEntity<Void> response = usersController.deleteUser(email);
        verify(userDetailsServiceImpl, times(1)).deleteUser(email);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
