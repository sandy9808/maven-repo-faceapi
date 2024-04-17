package dev.faceKeycloakProject.keycloak.authentication.authenticators.browser;

import org.keycloak.authentication.AuthenticationFlowContext;
// import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernameForm;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
// import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

public class KeycloakFaceAuthenticator implements Authenticator {
    
    static final String ID = "demo-faceauth";

    private final KeycloakSession session;

    public KeycloakFaceAuthenticator(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        Response response = context.form()
                .createForm("face-validation.ftl");
        context.challenge(response);
    }

    @Override
    public void action(AuthenticationFlowContext context) {

        try {
            @SuppressWarnings("rawtypes")
            Map faceData = JsonSerialization.readValue(context.getHttpRequest().getInputStream(), Map.class);

            SimpleHttp httpPost = SimpleHttp.doPost("http://localhost:8000/auth", session);
            httpPost.json(faceData);
            SimpleHttp.Response response = httpPost.asResponse();

            @SuppressWarnings("rawtypes")
            Map responseData = response.asJson(Map.class);

            if (responseData != null && !"unknown".equals(responseData.get(AuthenticationManager.FORM_USERNAME))) {

                UsernameForm usernameForm = new UsernameForm();
                String username = (String) responseData.get(AuthenticationManager.FORM_USERNAME);

                MultivaluedHashMap<String, String> inputData = new MultivaluedHashMap<>();
                inputData.putSingle(AuthenticationManager.FORM_USERNAME, username);
                boolean validUser = usernameForm.validateUser(context, inputData);
                if (validUser) {
//                    UserModel user = KeycloakModelUtils.findUserByNameOrEmail(session, context.getRealm(), username);
                    context.success();

//                    throw new RedirectionException(Response.Status.FOUND,context.getRefreshExecutionUrl());
                    return;
                }

//                context.failure(AuthenticationFlowError.INVALID_USER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.attempted();
        Response response = context.form()
                .setError("Could not detect face")
                .createErrorPage(Response.Status.BAD_REQUEST);
        context.challenge(response);

//        context.failure(AuthenticationFlowError.INVALID_USER);
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
        // NOOP
    }
}

