package com.easy.vertx.launcher;

import io.vertx.core.Launcher;

public class MyLauncher extends Launcher {
  public static void main(String[] args) {
    new MyLauncher().dispatch(args);
  }
}
