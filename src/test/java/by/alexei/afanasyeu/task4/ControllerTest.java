package by.alexei.afanasyeu.task4;

import by.alexei.afanasyeu.controller.ConstraintViolationMapper;
import by.alexei.afanasyeu.controller.GetController;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RunWith(ConcurrentRunner.class)
public class ControllerTest extends JerseyTest {
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
    public void testWithoutAttributes() {
        Response response = target().path("cats").request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testConnection() {
        Response response = target().path("cats").property("attribute", "name").request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertEquals(200, response.getStatus());
    }
}
