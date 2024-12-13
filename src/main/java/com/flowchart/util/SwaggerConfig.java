package com.flowchart.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Flowchart Management APIs", 
description = "Designed by Debashis", summary = "This flowchart management api will perform CRUD operation like creating a new flowchart, deleting a flowchart based on its ID, get all the nodes and edges associated to a flowchart..etc..", 
contact = @Contact(name = "Debashis Mohapatra", 
email = "techie.debashis@gmail.com")))
public class SwaggerConfig {

}
