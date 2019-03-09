package by.alexei.afanasyeu.task6;

import by.alexei.afanasyeu.controller.controller.PingController;
import by.alexei.afanasyeu.controller.rateLimitFilter.RateLimitingFilterGeneralStrategy;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.netty.connector.NettyConnectorProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.netty.NettyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GeneralRateLimiterTest  extends JerseyTest {
    private static final String TEST_URI = "http://localhost:9998/";
    private static final Client client = ClientBuilder.newClient();


    @Override
    protected ResourceConfig configure() {
        enable(TestProperties.LOG_TRAFFIC);
        RateLimitingFilterGeneralStrategy.setLimitParams(6, 1_000L);
        return new ResourceConfig(PingController.class, RateLimitingFilterGeneralStrategy.class);
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
    @Ignore
    public void testOneThread() throws InterruptedException {
        int rejected = 0;
        for (int i = 0; i < 50; i++) {
            Response response = client
                    .target(TEST_URI)
                    .path("ping")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 429) {
                rejected++;
            }
            Thread.sleep(100L);
        }
        System.out.println(rejected);
        Assert.assertTrue(rejected > 50*0.2 && rejected < 50*0.5);
    }
}