package by.alexei.afanasyeu;

import by.alexei.afanasyeu.controller.*;
import by.alexei.afanasyeu.dao.CatColorsInfoDao;
import by.alexei.afanasyeu.dao.CatsDao;
import by.alexei.afanasyeu.dao.CatsStatDao;
import by.alexei.afanasyeu.domain.StatInfo;
import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.net.URI;
import java.util.Map;

public class TaskLauncher {
    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    public void startFirstTask() {
        try (CatsDao catsDao = new CatsDao();
             CatColorsInfoDao catColorsInfoDao = new CatColorsInfoDao()) {
            Map<String, Integer> catColorsInfo = catsDao.getColorsInfo();
            catColorsInfoDao.saveColorsInfo(catColorsInfo);
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

    public void startSixthTask() {
        ResourceConfig config = new ResourceConfig();
        config.register(GetController.class)
                .register(PostController.class)
                .register(RateLimitingFilter.class)
                .register(ConstraintViolationMapper.class)
                .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        startServer(config);
    }

    public void startAll() {

    }

    private void startServer(ResourceConfig config) {
        try {
            final Channel server = NettyHttpContainerProvider.createHttp2Server(BASE_URI, config, null);

            Runtime.getRuntime().addShutdownHook(new Thread(server::close));

            System.out.println("Server started.");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
