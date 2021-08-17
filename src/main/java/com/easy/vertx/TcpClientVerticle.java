//package com.easy.vertx;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.buffer.Buffer;
//import io.vertx.core.net.NetClient;
//import io.vertx.core.net.NetSocket;
//
//public class TcpClientVerticle extends AbstractVerticle {
//  @Override
//  public void start(){
//
//    NetClient netClient = vertx.createNetClient();
//
//    netClient.connect(54321, "localhost", conn -> {
//      if (conn.succeeded()) {
//
//        NetSocket s = conn.result();
//
//        System.out.println(
//          "客户端启动成功--"+"代理服务器ip:"+s.remoteAddress().hostAddress()+"  "+"代理服务器开放端口:"+s.remoteAddress().port());
//
//        // 向服务器写数据
//        s.write(Buffer.buffer("hello"));
//
//
////         读取服务器的响应数据
//        s.handler(buffer -> {
//          System.out.println(buffer.toString());
//        });
//      } else {
//        System.out.println("连接服务器异常");
//      }
//    });
//  }
//}
//
