package by.alexei.afanasyeu.task5;

import by.alexei.afanasyeu.controller.mapper.ConstraintViolationMapper;
import by.alexei.afanasyeu.controller.controller.PostController;
import by.alexei.afanasyeu.dao.CatsDao;
import by.alexei.afanasyeu.dao.exception.DaoException;
import by.alexei.afanasyeu.domain.Cat;
import com.fasterxml.jackson.databind.JsonNode;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.netty.connector.NettyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.netty.NettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.glassfish.jersey.test.util.runner.ConcurrentRunner;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@RunWith(ConcurrentRunner.class)
public class PostControllerTest extends JerseyTest {
    private static final String TEST_URI = "http://localhost:9998/";
    private static final Client client = ClientBuilder.newClient();

    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(PostController.class, ConstraintViolationMapper.class);
    }

    @Override
    protected void configureClient(ClientConfig clientConfig) {
        clientConfig.connectorProvider(new NettyConnectorProvider());
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new NettyTestContainerFactory();
    }

    @Test
    public void testOk() {
        Cat tom = new Cat("Tom", "black", null, null);
        Response response01 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response01.getStatus());
        Cat kitty = new Cat("Kitty", "black & white", 12, 4);
        Response response02 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(kitty, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response02.getStatus());
    }

    @Test
    public void testCatExist() {
        Cat murzik = new Cat("Murzik", "black", 2, 1);
        Response response01 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(murzik, MediaType.APPLICATION_JSON));
        Assert.assertEquals(201, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(murzik, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response02.getStatus());
        JsonNode errors = response02.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("Cat with name Murzik already exist.", errors.get("errors").get(0).asText());
    }

    @Test
    public void testNotValidName() {
        Cat blank = new Cat("   ", "black", 3, 3);
        Response response01 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(blank, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response01.getStatus());
        JsonNode errors01 = response01.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors01.get("errors").size());
        Assert.assertEquals("котики не должны быть безымянными!", errors01.get("errors").get(0).asText());
        Cat nameNull = new Cat(null, "black", 3, 3);
        Response response02 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nameNull, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response02.getStatus());
        JsonNode errors02 = response02.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors02.get("errors").size());
        Assert.assertEquals("котики не должны быть безымянными!", errors02.get("errors").get(0).asText());
    }

    @Test
    public void testNotValidColor() {
        Cat tom = new Cat("Tom", "not valid color", 3, 3);
        Response response01 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response01.getStatus());
        JsonNode errors01 = response01.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors01.get("errors").size());
        Assert.assertEquals("цвета котиков: black, white, black & white, red, red & white, red & black & white", errors01.get("errors").get(0).asText());
        Cat tom1 = new Cat("Tom", null, 3, 3);
        Response response02 = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom1, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response02.getStatus());
        JsonNode errors02 = response02.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors02.get("errors").size());
        Assert.assertEquals("котики не могут быть бесцветными", errors02.get("errors").get(0).asText());
    }

    @Test
    public void testNotValidTailLength() {
        Cat tom = new Cat("Tom", "white", -3, 3);
        Response response = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response.getStatus());
        JsonNode errors = response.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("tail length cannot be negative", errors.get("errors").get(0).asText());
    }

    @Test
    public void testNotValidWhiskersLength() {
        Cat tom = new Cat("Tom", "white", 3, -3);
        Response response = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response.getStatus());
        JsonNode errors = response.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("whiskers length cannot be negative", errors.get("errors").get(0).asText());
    }

    @Test
    public void testNotValidAll() {
        Cat tom = new Cat("", "", -1, -1);
        Response response = client
                .target(TEST_URI)
                .path("cat")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tom, MediaType.APPLICATION_JSON));
        Assert.assertEquals(400, response.getStatus());
        JsonNode errors = response.readEntity(JsonNode.class);
        List<String> expectedErrors = Arrays.asList(
                "котики не должны быть безымянными!"
                , "цвета котиков: black, white, black & white, red, red & white, red & black & white"
                , "tail length cannot be negative"
                , "whiskers length cannot be negative");
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(0).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(1).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(2).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(3).asText()));
    }

    @AfterClass
    public static void clear() throws DaoException {
        CatsDao dao = new CatsDao();
        dao.deleteByName("Tom");
        dao.deleteByName("Kitty");
        dao.deleteByName("Murzik");
    }
}
