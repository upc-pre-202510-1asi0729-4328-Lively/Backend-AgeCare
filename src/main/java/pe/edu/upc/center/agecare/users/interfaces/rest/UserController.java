package pe.edu.upc.center.agecare.users.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.agecare.users.domain.model.aggregates.User;
import pe.edu.upc.center.agecare.users.domain.services.UserCommandService;
import pe.edu.upc.center.agecare.users.domain.services.UserQueryService;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.CreateUserResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.resources.UserResource;
import pe.edu.upc.center.agecare.users.interfaces.rest.transform.UserResourceAssembler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Operations related to Users")

public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UserController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = userQueryService.getAllUsers()
                .stream()
                .map(UserResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var user = userQueryService.getUserById(id);
        return user.map(UserResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource createUserResource) {
        // Convertir CreateUserResource → User (la entidad)
        User user = UserResourceAssembler.toEntity(createUserResource);

        // Guardar el User
        User savedUser = userCommandService.createUser(user);

        // Convertir el User guardado → UserResource (con id y todo)
        UserResource userResource = UserResourceAssembler.toResource(savedUser);

        // Retornar la respuesta
        return ResponseEntity.ok(userResource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable Long id, @RequestBody CreateUserResource resource) {
        User updatedUser = userCommandService.updateUser(id, UserResourceAssembler.toEntity(resource));
        UserResource userResource = UserResourceAssembler.toResource(updatedUser);
        return ResponseEntity.ok(userResource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCommandService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
