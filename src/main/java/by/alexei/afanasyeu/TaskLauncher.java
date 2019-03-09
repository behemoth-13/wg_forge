package by.alexei.afanasyeu;

import by.alexei.afanasyeu.controller.controller.GetController;
import by.alexei.afanasyeu.controller.controller.PingController;
import by.alexei.afanasyeu.controller.controller.PostController;
import by.alexei.afanasyeu.controller.mapper.ConstraintViolationMapper;
import by.alexei.afanasyeu.controller.rateLimitFilter.RateLimitingFilterGeneralStrategy;
import by.alexei.afanasyeu.controller.rateLimitFilter.RateLimitingFilterStrictStrategy;
import by.alexei.afanasyeu.dao.CatColorsInfoDao;
import by.alexei.afanasyeu.dao.CatsDao;
import by.alexei.afanasyeu.dao.CatsStatDao;
import by.alexei.afanasyeu.domain.RateLimiterConfig;
import by.alexei.afanasyeu.domain.StatInfo;
import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.net.URI;

public class TaskLauncher {
    private static final URI BASE_URI = URI.create("http://localhost:8080/");
    static final String SEC  = "sec";
    static final String MIN  = "min";
    static final String HOUR = "hour";
    static final String DAY  = "day";
    static final String GENERAL  = "general";
    static final String STRICT  = "strict";

    public void startFirstTask() {
        try (CatColorsInfoDao catColorsInfoDao = new CatColorsInfoDao()) {
            catColorsInfoDao.setColorsInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startSecondTask() {
        try (CatsDao catsDao = new CatsDao();
             CatsStatDao catsStatDao = new CatsStatDao()) {
            StatInfo statInfo = catsDao.getStatInfo();
            catsStatDao.saveStatInfo(statInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startThirdTask() {
        startServer(new ResourceConfig().register(PingController.class));
    }

    public void startFourthTask() {
        ResourceConfig config = new ResourceConfig();
        config.register(GetController.class)
                .register(ConstraintViolationMapper.class)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        startServer(config);
    }

    public void startFifthTask() {
        ResourceConfig config = new ResourceConfig();
        config.register(PostController.class)
                .register(ConstraintViolationMapper.class)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        startServer(config);
    }

    public void startSixthTask(RateLimiterConfig rateLimiterConfig) {
        ResourceConfig config = new ResourceConfig();
        config.register(PingController.class)
                .register(GetController.class)
                .register(PostController.class)
                .register(ConstraintViolationMapper.class)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        switch (rateLimiterConfig.getRateLimiterType()) {
            case GENERAL:
                RateLimitingFilterGeneralStrategy.setLimitParams(rateLimiterConfig.getRequests(), rateLimiterConfig.getInterval());
                config.register(RateLimitingFilterGeneralStrategy.class);
                break;
            case STRICT:
                RateLimitingFilterStrictStrategy.setLimitParams(rateLimiterConfig.getRequests(), rateLimiterConfig.getInterval());
                config.register(RateLimitingFilterStrictStrategy.class);
                break;
        }
        startServer(config);
    }

    public void startAll(RateLimiterConfig rateLimiterConfig) {
        startFirstTask();
        startSecondTask();
        startSixthTask(rateLimiterConfig);
    }

    private void startServer(ResourceConfig config) {
        try {
            final Channel server = NettyHttpContainerProvider.createHttp2Server(BASE_URI, config, null);

            Runtime.getRuntime().addShutdownHook(new Thread(server::close));

            System.out.println("Server started.\n");
            System.out.println("Stop the application using CTRL+C\n");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
