package rest.sources;


import core.Bikes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("info")
@ApplicationScoped
public class InfoSource {

    @Operation(
            description = "Get project information",
            tags = "info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Info received successfully",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Info failed to receive",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response getInfo() {
        JsonObject json = Json.createObjectBuilder()
                .add("clani", Json.createArrayBuilder().add("sk5429"))
                .add("opis_projekta", "Moj projekt implementira aplikacijo za deljenje koles.")
                .add("mikrostoritve", Json.createArrayBuilder()
                                        .add("http://159.122.175.182:30000/sources/bikes")
                                        .add("http://159.122.175.182:30001/sources/bikerent")
                                        .add("http://159.122.175.182:30002/sources/users"))
                .add("github", Json.createArrayBuilder()
                                        .add("https://github.com/CityBikeShare/BikeRentService")
                                        .add("https://github.com/CityBikeShare/BikeCatalogService")
                                        .add("https://github.com/CityBikeShare/UserManagmentService")
                                        .add("https://github.com/CityBikeShare/PayingService")
                                        .add("https://github.com/CityBikeShare/Documentation"))
                .add("travis", Json.createArrayBuilder()
                                        .add("https://travis-ci.org/CityBikeShare/BikeRentService")
                                        .add("https://travis-ci.org/CityBikeShare/BikeCatalogService")
                                        .add("https://travis-ci.org/CityBikeShare/UserManagmentService")
                                        .add("https://travis-ci.org/CityBikeShare/PayingService"))
                .add("dockerhub", Json.createArrayBuilder()
                                        .add("https://hub.docker.com/r/citybikeshare/bikerentservice/")
                                        .add("https://hub.docker.com/r/citybikeshare/bikecatalogservice/")
                                        .add("https://hub.docker.com/r/citybikeshare/usermanagmentservice/")
                                        .add("https://hub.docker.com/r/citybikeshare/payingservice/"))
                .build();


        return Response.ok(json.toString()).build();

    }

}
