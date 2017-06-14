package com.github.gvolpe.fs2rabbit.config

import com.typesafe.config.ConfigFactory

object Fs2RabbitConfigManager {

  private val baseConfPath        = "fs2-rabbit.connection"
  private lazy val configuration  = ConfigFactory.load()
  private lazy val safeConfig     = new SafeConfigReader(configuration)

  def config: Fs2RabbitConfig = Fs2RabbitConfig(
    host = safeConfig.string(s"$baseConfPath.host").getOrElse("localhost"),
    port = safeConfig.int(s"$baseConfPath.port").getOrElse(5672),
    virtualHost = safeConfig.string(s"$baseConfPath.virtual-host").getOrElse("/"),
    connectionTimeout = safeConfig.int(s"$baseConfPath.connection-timeout").getOrElse(60),
    requeueOnNack = safeConfig.boolean("fs2-rabbit.requeue-on-nack").getOrElse(false)
  )

}

case class Fs2RabbitConfig(host: String, port: Int, virtualHost: String, connectionTimeout: Int, requeueOnNack: Boolean)
