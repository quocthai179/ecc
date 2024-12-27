package rest;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import model.EccResponse;
import org.jboss.resteasy.reactive.RestForm;

import io.quarkus.qute.TemplateInstance;
import io.quarkus.qute.CheckedTemplate;
import io.quarkiverse.renarde.Controller;
import model.EccEntity;

/**
 * This defines a REST controller, each method will be available under the "Classname/method" URI by convention
 */
public class EccController extends Controller {
    
    /**
     * This defines templates available in src/main/resources/templates/Classname/method.html by convention
     */
    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        /**
         * This specifies that the Todos/index.html template does not take any parameter
         */
        public static native TemplateInstance index();
        /**
         * This specifies that the Todos/todos.html template takes a todos parameter of type List&lt;Todo&gt;
         */
        public static native TemplateInstance todos(List<EccEntity> eccEntities);
    }

    // This overrides the convention and makes this method available at "/renarde"
    @GET
    @Path("/ecc-demo")
    public TemplateInstance index() {
        // renders the Todos/index.html template
        return Templates.index();
    }

    @GET
    @Path("/ecc-demo-2")
    public TemplateInstance todos() {
        // renders the Todos/todos.html template with our list of Todo entities
        return Templates.todos(EccEntity.listAll());
    }
    
    // Creates a POST action at Todos/add taking a form element named task
    @GET
    @Path("/ecc-demo-3")
    public EccResponse generateKeyPair() {
        EccResponse eccResponse;
        eccResponse = new EccResponse("1", "2", "3", "4");
        return eccResponse;
    }
}