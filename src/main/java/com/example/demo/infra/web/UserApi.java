package com.example.demo.infra.web;

import com.example.demo.domain.User;
import com.example.demo.domain.UserService;
import com.example.demo.infra.web.request.CreateUserRequest;
import com.example.demo.infra.web.response.UserResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

//    @Autowired : not mandatory if only one constructor (since spring core 4.2)
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var userResources = userService.getAll()
                .stream()
                .map(this::toResource)
                .collect(toList());

        return ok(userResources);
    }

    // api/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getById(@PathVariable String id) {
        return userService.getAll()
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                // pre java 8
//                .map(new Function<User, UserResource>() {
//                    @Override
//                    public UserResource apply(User user) {
//                        return null;
//                    }
//                })
                // since java 8
//                .map(user -> this.toResource(user))
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        var newUser = userService.add(request.getUsername());

        return ResponseEntity.created(
                linkTo(methodOn(UserApi.class).getById(newUser.getId())).toUri()
        ).build();
    }

    private UserResource toResource(User user) {
        return new UserResource()
                .setName(user.getName())
                // manually adding link
//                .add(Link.of("/api/users/" + user.getId(), "self"));
                // dynamically introspect link
                .add(linkTo(methodOn(UserApi.class).getById(user.getId())).withSelfRel());
    }
}

