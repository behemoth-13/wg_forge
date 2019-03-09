package by.alexei.afanasyeu.controller.controller;

import by.alexei.afanasyeu.domain.Cat;
import by.alexei.afanasyeu.service.CatService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("cats")
public class GetController {
    private CatService service;

    //curl -X GET http://localhost:8080/cats
    //curl -X GET http://localhost:8080/cats?attribute=name&order=asc
    //curl -X GET http://localhost:8080/cats?attribute=tail_length&order=desc
    //curl -X GET http://localhost:8080/cats?offset=10&limit=10
    //curl -X GET http://localhost:8080/cats?attribute=color&order=asc&offset=5&limit=2
    public GetController() {
        service = new CatService();
    }

    public GetController(CatService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCats(
            @Pattern(regexp = "name|color|tail_length|whiskers_length",
                    message = "attribute must be one of [name, color, tail_length, whiskers_length]")
            @DefaultValue("name") @QueryParam("attribute")
                    String sortBy,
            @Pattern(regexp = "asc|desc", message = "order must be asc or desc")
            @DefaultValue("asc") @QueryParam("order")
                    String order,
            @Min(value = 0, message = "offset must be at least 0")
            @DefaultValue("0") @QueryParam("offset")
                    int offset,
            @Min(value = 1, message = "limit must be at least 1")
            @QueryParam("limit")
                    Integer limit
    ) {
        try {
            List<Cat> list = service.getCatList(sortBy, order, offset, limit);
            return Response.ok(list).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}