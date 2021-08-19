package com.easy.vertx;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.streams.Pump;

/**
 * <p>
 * 简单tcp代理服务器<br/>
 * 1、接受客户端发送的数据包<br/>
 * 2、转发数据包给指定服务器<br/>
 * TODO：<br/>
 * 3、权限校验
 * </p>
 *
 * @author LiZiHan
 */


public class TcpProxyVerticle extends AbstractVerticle {

  final InternalLogger log = Log4JLoggerFactory.getInstance(TcpProxyVerticle.class);

  @Override
  public void start() {

//    vertx.deployVerticle(new TcpServerVerticle());
//    vertx.deployVerticle(new TcpClientVerticle());

    NetServer netServer = vertx.createNetServer();
    NetClient netClient = vertx.createNetClient();

    int realPort = config().getInteger("realPort");
    String realHost = config().getString("realHost");
    Integer proxyPort = config().getInteger("proxyPort");

    netServer

      .connectHandler(netSocket -> {
        netClient.connect(realPort, realHost, res -> {
          if (res.succeeded()) {
            NetSocket result = res.result();
            Pump.pump(netSocket, result).start();
            Pump.pump(result, netSocket).start();
            result.closeHandler(event -> log.info("result is closed!"));
          } else {
            log.error("Failed to connect: " + res.cause().getMessage());
          }
        });
        netSocket.closeHandler(event -> log.info("netSocket is closed!"));
      })

      .listen(proxyPort, res -> {
        if (res.succeeded()) {
          log.info("代理服务器启动,开放端口: " + res.result().actualPort());
        } else {
          log.error("Failed to start: " + res.cause().getMessage());
        }
      });
  }
}
