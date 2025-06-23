package pe.edu.upc.center.agecare.users.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Retrieve all users",
            description = "Get a list of all users in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResource.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = userQueryService.getAllUsers()
                .stream()
                .map(UserResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Retrieve user by ID",
            description = "Get details of a specific user by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResource.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long id) {
        var user = userQueryService.getUserById(id);
        return user.map(UserResourceAssembler::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new user",
            description = "Register a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource createUserResource) {
        User user = UserResourceAssembler.toEntity(createUserResource);
        User savedUser = userCommandService.createUser(user);
        UserResource userResource = UserResourceAssembler.toResource(savedUser);
        return ResponseEntity.status(201).body(userResource);
    }

    @Operation(
            summary = "Update an existing user",
            description = "Update the information of an existing user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResource.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable Long id, @RequestBody CreateUserResource resource) {
        User updatedUser = userCommandService.updateUser(id, UserResourceAssembler.toEntity(resource));
        UserResource userResource = UserResourceAssembler.toResource(updatedUser);
        return ResponseEntity.ok(userResource);
    }

    @Operation(
            summary = "Delete a user",
            description = "Remove a user from the system by their ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userCommandService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
