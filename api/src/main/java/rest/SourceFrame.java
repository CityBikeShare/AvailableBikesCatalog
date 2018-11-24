package rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import javax.ws.rs.ApplicationPath;

@OpenAPIDefinition(info = @Info(
        title = "Rest API for management of city bike sharing",
        version = "1.0",
        description = "Manage data of bikes, their owners and customers",
        contact = @Contact(name = "Sebastjan", email = "sk5429@student.uni-lj.si"),
        license = @License(name = "Sebastjan Kozoglav")))
@ApplicationPath("sources")
public class SourceFrame extends javax.ws.rs.core.Application{
}
