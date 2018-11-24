package rest.sources;

import beans.BikesBean;
import core.Bikes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("bikes")
@ApplicationScoped
public class BikesSource {
    @Inject
    private BikesBean bikesBean;

    @Operation(
            description = "Get all bikes",
            tags = "bikes",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bikes",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No bikes available",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response getAllBikes() {
        List<Bikes> bikes = bikesBean.getBikes();

        return bikes == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(bikes).build();
    }

    @Operation(
            description = "Get bike by id",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bike by id",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Bike with this id does not exist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("/bike/{id}")
    @GET
    public Response getBikeById(@PathParam("id") int id) {
        Bikes bike = bikesBean.getBikeById(id);
        return bike == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(bike).build();
    }


    @Operation(
            description = "Get bikes by region",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bike by region",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Bike with this region does not exist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("{region}")
    @GET
    public Response getBikesByRegion(@PathParam("region") String region) {
        List<Bikes> bike = bikesBean.getBikesByRegion(region);
        return bike == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(bike).build();
    }




}
