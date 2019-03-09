package by.alexei.afanasyeu.task4;

import by.alexei.afanasyeu.controller.mapper.ConstraintViolationMapper;
import by.alexei.afanasyeu.controller.controller.GetController;
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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@RunWith(ConcurrentRunner.class)
public class GetControllerTest extends JerseyTest {
    private static final String TEST_URI = "http://localhost:9998/";
    private static final Client client = ClientBuilder.newClient();

    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(GetController.class, ConstraintViolationMapper.class);
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
    public void testEmpty() {
        Response response = client
                .target(TEST_URI)
                .path("cats")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testAttribute() throws InterruptedException {
        Response response01 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "name")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "color")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response02.getStatus());
        Response response03 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "tail_length")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response03.getStatus());
        Response response04 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "whiskers_length")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response04.getStatus());
        Response response05 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "wrong_attribute")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(400, response05.getStatus());
        JsonNode errors = response05.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("attribute must be one of [name, color, tail_length, whiskers_length]", errors.get("errors").get(0).asText());
    }

    @Test
    public void testOrder() throws InterruptedException {
        Response response01 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("order", "asc")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("order", "desc")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response02.getStatus());
        Response response03 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("order", "wrong")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(400, response03.getStatus());
        JsonNode errors = response03.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("order must be asc or desc", errors.get("errors").get(0).asText());
    }

    @Test
    public void testOffset() throws InterruptedException {
        Response response01 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("offset", "0")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("offset", "5")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response02.getStatus());
        Response response03 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("offset", "-5")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(400, response03.getStatus());
        JsonNode errors = response03.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("offset must be at least 0", errors.get("errors").get(0).asText());
        Response response04 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("offset", "offset")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(404, response04.getStatus());
    }

    @Test
    public void testLimit() throws InterruptedException {
        Response response01 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("limit", "1")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("limit", "6")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response02.getStatus());
        Response response03 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("limit", "-6")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(400, response03.getStatus());
        JsonNode errors = response03.readEntity(JsonNode.class);
        Assert.assertEquals(1, errors.get("errors").size());
        Assert.assertEquals("limit must be at least 1", errors.get("errors").get(0).asText());
        Response response04 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("limit", "limit")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(404, response04.getStatus());
    }

    @Test
    public void testAll() throws InterruptedException {
        Response response01 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "color")
                .queryParam("order", "desc")
                .queryParam("offset", "3")
                .queryParam("limit", "3")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response01.getStatus());
        Response response02 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "color")
                .queryParam("limit", "3")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(200, response02.getStatus());
        Response response03 = client
                .target(TEST_URI)
                .path("cats")
                .queryParam("attribute", "wrong")
                .queryParam("order", "wrong2")
                .queryParam("offset", "-3")
                .queryParam("limit", "-3")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Assert.assertEquals(400, response03.getStatus());
        JsonNode errors = response03.readEntity(JsonNode.class);
        Assert.assertEquals(4, errors.get("errors").size());
        List<String> expectedErrors = Arrays.asList(
                "attribute must be one of [name, color, tail_length, whiskers_length]"
                , "limit must be at least 1"
                , "order must be asc or desc"
                , "offset must be at least 0");
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(0).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(1).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(2).asText()));
        Assert.assertTrue(expectedErrors.contains(errors.get("errors").get(3).asText()));
    }
}