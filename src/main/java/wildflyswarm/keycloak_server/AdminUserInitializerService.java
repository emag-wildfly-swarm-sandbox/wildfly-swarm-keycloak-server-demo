package wildflyswarm.keycloak_server;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class AdminUserInitializerService {

  @Inject @ConfigurationValue("keycloak.admin.username")
  private String adminUserName;

  @Inject @ConfigurationValue("keycloak.admin.password")
  private String adminUserPassWord;

  @Inject @ConfigurationValue("keycloak.state-checker-value")
  private String stateCheckerValue;

  public void onStartup(@Observes @Initialized ServletContext context) {
    Client client = null;

    try {
      client = ClientBuilder.newClient();
      WebTarget target = client.target("http://localhost:8080/auth");

      Form form = new Form();
      form.param("username", adminUserName);
      form.param("password", adminUserPassWord);
      form.param("passwordConfirmation", adminUserPassWord);
      form.param("stateChecker", stateCheckerValue);

      target.request(MediaType.APPLICATION_FORM_URLENCODED)
        .cookie("KEYCLOAK_STATE_CHECKER", stateCheckerValue)
        .post(Entity.form(form));
    } finally {
      if (client != null) {
        client.close();
      }
    }

  }

}
