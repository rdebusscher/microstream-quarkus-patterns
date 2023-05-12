package be.rubus.microstream.quarkus.example.controller;

import be.rubus.microstream.quarkus.example.dto.CreateUser;
import be.rubus.microstream.quarkus.example.model.User;
import be.rubus.microstream.quarkus.example.service.UserBookService;
import be.rubus.microstream.quarkus.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.Collection;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {


    @Inject
    UserService userService;

    @Inject
    UserBookService userBookService;

    @GET
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        Optional<User> byId = userService.getById(id);

        Response.ResponseBuilder builder = byId.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND));
        return builder.build();

    }

    @GET
    @Path("/by/{email}")
    public Response findBy(@PathParam("email") String email) {
        Optional<User> byEmail = userService.findByEmail(email);

        Response.ResponseBuilder builder = byEmail.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND));
        return builder.build();
    }

    @POST
    public Response addUser(CreateUser user, @Context UriInfo uriInfo) {
        User savedUser = userService.add(user);
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(savedUser.getId());
        return Response.created(uriBuilder.build()).entity(savedUser).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        userService.removeById(id);
        return Response.noContent().build();
    }


    @PATCH
    @Path("/{id}")
    public Response updateEmail(@PathParam("id") String id, JsonObject json) {
        String email = json.getString("email");
        if (email == null || email.isBlank()) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        User user = userService.updateEmail(id, email);
        return Response.ok(user).build();
    }

    @GET
    @Path("/{id}/book")
    public Response getUserBooks(@PathParam("id") String id) {
        Optional<User> byId = userService.getById(id);
        if (byId.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(byId.get().getBooks()).build();
    }

    @POST
    @Path(value = "/{id}/book/{isbn}")
    public void addBookToUser(@PathParam("id") String id, @PathParam("isbn") String isbn) {
        userBookService.addBookToUser(id, isbn);
    }
}
