package rest.sources;

import beans.core.BikesBean;
import beans.external.UsersBean;
import com.kumuluz.ee.logs.cdi.Log;
import core.Bikes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("bikes")
@ApplicationScoped
@Log
public class BikesSource {
    @Inject
    private BikesBean bikesBean;

    @Inject
    private UsersBean usersBean;

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

    @Path("fault-bike/{id}")
    @GET
    public Response getFaultBikeById(@PathParam("id") int id){
        try {
            return Response.ok(bikesBean.getBikeByIdFault(id)).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
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
    @Path("region/{region}")
    @GET
    public Response getBikesByRegion(@PathParam("region") String region) {
        List<Bikes> bike = bikesBean.getBikesByRegion(usersBean.getUsersByRegion(region));
        return bike == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(bike).build();
    }


    @Operation(
            description = "Get bikes by user",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Bike by user",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Bike with this region does not exist",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("user/{id}")
    @GET
    public Response getBikesByUserId(@PathParam("id") int userId) {
        List<Bikes> bike = bikesBean.getBikesByUserId(userId);
        return bike == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(bike).build();
    }

    @Operation(
            description = "Publish bike ad",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Publish successful",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Publish failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PUT
    @Path("insertNew")
    public Response insertBike(@RequestBody Bikes bike) {
        bike = bikesBean.insertNewBike(bike);
        return Response.ok(bike).build();
    }

    @Operation(
            description = "Update bike",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update successful",
                            content = @Content(schema = @Schema(implementation = Bikes.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Update failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PUT
    @Path("update/{id}")
    public Response updateBike(@PathParam("id") int id, @RequestBody Bikes bike) {
        bike = bikesBean.updateBike(id, bike);
        return Response.ok(bike).build();
    }

    @Operation(
            summary = "Delete bike ad",
            description = "Delete bike by id",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed bike ad"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Removal of bike ad failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )}
    )
    @Path("delete/{id}")
    @DELETE
    public Response deleteBike(@PathParam("id") int bikeId) {
        boolean status = bikesBean.deleteBike(bikeId);

        return status ? Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Operation(
            description = "Convert price currency",
            tags = "bike",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Request successful",
                            content = @Content(schema = @Schema(implementation = double.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request failed! Check if the bikeId and currency are valid.",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("convert")
    @GET
    public Response convertPrice(@QueryParam("bikeid") int bikeId, @QueryParam("currency") String currency) {
        double status = bikesBean.convert(bikeId, currency);
        return status != Double.MIN_VALUE ? Response.ok(status).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
