package by.alexei.afanasyeu.controller.controller;

import by.alexei.afanasyeu.dao.exception.DaoException;
import by.alexei.afanasyeu.domain.Cat;
import by.alexei.afanasyeu.service.CatService;
import by.alexei.afanasyeu.service.exception.ServiceException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cat")
public class PostController {
    private CatService service = new CatService();
    //curl -X POST http://localhost:8080/cat \-d "{\"name\": \"Tihon\", \"color\": \"red & white\", \"tail_length\": 15, \"whiskers_length\": 12}"
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCat(@Valid Cat cat) {
        try {
            service.saveCat(cat);
            return Response.status(Response.Status.CREATED).build();
        } catch (ServiceException e) {
            switch (e.getMessage()) {
                case DaoException.CAT_EXIST:
                    return Response.status(Response.Status.BAD_REQUEST).entity(new Object(){
                        public String[] errors = new String[]{"Cat with name "+cat.getName()+" already exist."};
                    }).build();
                default:
                    e.printStackTrace();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
