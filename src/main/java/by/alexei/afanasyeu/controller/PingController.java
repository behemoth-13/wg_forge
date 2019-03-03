package by.alexei.afanasyeu.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("ping")
public class PingController {
    //curl -X GET http://localhost:8080/ping
    @GET
    public String ping() {
        return "Cats Service. Version 0.1";
    }
}