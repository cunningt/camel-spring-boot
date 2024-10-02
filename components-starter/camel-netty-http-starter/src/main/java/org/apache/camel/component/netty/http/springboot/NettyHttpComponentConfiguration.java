/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.netty.http.springboot;

import java.io.File;
import java.util.Map;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.apache.camel.LoggingLevel;
import org.apache.camel.component.netty.ClientInitializerFactory;
import org.apache.camel.component.netty.NettyCamelStateCorrelationManager;
import org.apache.camel.component.netty.NettyConfiguration;
import org.apache.camel.component.netty.NettyServerBootstrapFactory;
import org.apache.camel.component.netty.ServerInitializerFactory;
import org.apache.camel.component.netty.http.NettyHttpBinding;
import org.apache.camel.component.netty.http.NettyHttpComponent;
import org.apache.camel.component.netty.http.NettyHttpSecurityConfiguration;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.spring.boot.ComponentConfigurationPropertiesCommon;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Netty HTTP server and client using the Netty 4.x.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@ConfigurationProperties(prefix = "camel.component.netty-http")
public class NettyHttpComponentConfiguration
        extends
            ComponentConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the netty-http component. This is
     * enabled by default.
     */
    private Boolean enabled;
    /**
     * To use the NettyConfiguration as configuration when creating endpoints.
     * The option is a org.apache.camel.component.netty.NettyConfiguration type.
     */
    private NettyConfiguration configuration;
    /**
     * Whether or not to disconnect(close) from Netty Channel right after use.
     * Can be used for both consumer and producer.
     */
    private Boolean disconnect = false;
    /**
     * Setting to ensure socket is not closed due to inactivity
     */
    private Boolean keepAlive = true;
    /**
     * Setting to facilitate socket multiplexing
     */
    private Boolean reuseAddress = true;
    /**
     * This option allows producers and consumers (in client mode) to reuse the
     * same Netty Channel for the lifecycle of processing the Exchange. This is
     * useful if you need to call a server multiple times in a Camel route and
     * want to use the same network connection. When using this, the channel is
     * not returned to the connection pool until the Exchange is done; or
     * disconnected if the disconnect option is set to true. The reused Channel
     * is stored on the Exchange as an exchange property with the key
     * NettyConstants#NETTY_CHANNEL which allows you to obtain the channel
     * during routing and use it as well.
     */
    private Boolean reuseChannel = false;
    /**
     * Setting to set endpoint as one-way or request-response
     */
    private Boolean sync = true;
    /**
     * Setting to improve TCP protocol performance
     */
    private Boolean tcpNoDelay = true;
    /**
     * Allows for bridging the consumer to the Camel routing Error Handler,
     * which mean any exceptions (if possible) occurred while the Camel consumer
     * is trying to pickup incoming messages, or the likes, will now be
     * processed as a message and handled by the routing Error Handler.
     * Important: This is only possible if the 3rd party component allows Camel
     * to be alerted if an exception was thrown. Some components handle this
     * internally only, and therefore bridgeErrorHandler is not possible. In
     * other situations we may improve the Camel component to hook into the 3rd
     * party component and make this possible for future releases. By default
     * the consumer will use the org.apache.camel.spi.ExceptionHandler to deal
     * with exceptions, that will be logged at WARN or ERROR level and ignored.
     */
    private Boolean bridgeErrorHandler = false;
    /**
     * If enabled and an Exchange failed processing on the consumer side the
     * response's body won't contain the exception's stack trace.
     */
    private Boolean muteException = false;
    /**
     * Allows to configure a backlog for netty consumer (server). Note the
     * backlog is just a best effort depending on the OS. Setting this option to
     * a value such as 200, 500 or 1000, tells the TCP stack how long the accept
     * queue can be If this option is not configured, then the backlog depends
     * on OS setting.
     */
    private Integer backlog;
    /**
     * When netty works on nio mode, it uses default bossCount parameter from
     * Netty, which is 1. User can use this option to override the default
     * bossCount from Netty
     */
    private Integer bossCount = 1;
    /**
     * Set the BossGroup which could be used for handling the new connection of
     * the server side across the NettyEndpoint. The option is a
     * io.netty.channel.EventLoopGroup type.
     */
    private EventLoopGroup bossGroup;
    /**
     * If sync is enabled then this option dictates NettyConsumer if it should
     * disconnect where there is no reply to send back.
     */
    private Boolean disconnectOnNoReply = true;
    /**
     * To use the given EventExecutorGroup. The option is a
     * io.netty.util.concurrent.EventExecutorGroup type.
     */
    private EventExecutorGroup executorService;
    /**
     * Sets a maximum thread pool size for the netty consumer ordered thread
     * pool. The default size is 2 x cpu_core plus 1. Setting this value to eg
     * 10 will then use 10 threads unless 2 x cpu_core plus 1 is a higher value,
     * which then will override and be used. For example if there are 8 cores,
     * then the consumer thread pool will be 17. This thread pool is used to
     * route messages received from Netty by Camel. We use a separate thread
     * pool to ensure ordering of messages and also in case some messages will
     * block, then nettys worker threads (event loop) wont be affected.
     */
    private Integer maximumPoolSize;
    /**
     * To use a custom NettyServerBootstrapFactory. The option is a
     * org.apache.camel.component.netty.NettyServerBootstrapFactory type.
     */
    private NettyServerBootstrapFactory nettyServerBootstrapFactory;
    /**
     * If sync is enabled this option dictates NettyConsumer which logging level
     * to use when logging a there is no reply to send back.
     */
    private LoggingLevel noReplyLogLevel = LoggingLevel.WARN;
    /**
     * If the server (NettyConsumer) catches an
     * java.nio.channels.ClosedChannelException then its logged using this
     * logging level. This is used to avoid logging the closed channel
     * exceptions, as clients can disconnect abruptly and then cause a flood of
     * closed exceptions in the Netty server.
     */
    private LoggingLevel serverClosedChannelExceptionCaughtLogLevel = LoggingLevel.DEBUG;
    /**
     * If the server (NettyConsumer) catches an exception then its logged using
     * this logging level.
     */
    private LoggingLevel serverExceptionCaughtLogLevel = LoggingLevel.WARN;
    /**
     * To use a custom ServerInitializerFactory. The option is a
     * org.apache.camel.component.netty.ServerInitializerFactory type.
     */
    private ServerInitializerFactory serverInitializerFactory;
    /**
     * Whether to use ordered thread pool, to ensure events are processed
     * orderly on the same channel.
     */
    private Boolean usingExecutorService = true;
    /**
     * Time to wait for a socket connection to be available. Value is in
     * milliseconds.
     */
    private Integer connectTimeout = 10000;
    /**
     * Whether the producer should be started lazy (on the first message). By
     * starting lazy you can use this to allow CamelContext and routes to
     * startup in situations where a producer may otherwise fail during starting
     * and cause the route to fail being started. By deferring this startup to
     * be lazy then the startup failure can be handled during routing messages
     * via Camel's routing error handlers. Beware that when the first message is
     * processed then creating and starting the producer may take a little time
     * and prolong the total processing time of the processing.
     */
    private Boolean lazyStartProducer = false;
    /**
     * Allows to use a timeout for the Netty producer when calling a remote
     * server. By default no timeout is in use. The value is in milli seconds,
     * so eg 30000 is 30 seconds. The requestTimeout is using Netty's
     * ReadTimeoutHandler to trigger the timeout.
     */
    private Long requestTimeout;
    /**
     * To use a custom ClientInitializerFactory. The option is a
     * org.apache.camel.component.netty.ClientInitializerFactory type.
     */
    private ClientInitializerFactory clientInitializerFactory;
    /**
     * To use a custom correlation manager to manage how request and reply
     * messages are mapped when using request/reply with the netty producer.
     * This should only be used if you have a way to map requests together with
     * replies such as if there is correlation ids in both the request and reply
     * messages. This can be used if you want to multiplex concurrent messages
     * on the same channel (aka connection) in netty. When doing this you must
     * have a way to correlate the request and reply messages so you can store
     * the right reply on the inflight Camel Exchange before its continued
     * routed. We recommend extending the TimeoutCorrelationManagerSupport when
     * you build custom correlation managers. This provides support for timeout
     * and other complexities you otherwise would need to implement as well. See
     * also the producerPoolEnabled option for more details. The option is a
     * org.apache.camel.component.netty.NettyCamelStateCorrelationManager type.
     */
    private NettyCamelStateCorrelationManager correlationManager;
    /**
     * Channels can be lazily created to avoid exceptions, if the remote server
     * is not up and running when the Camel producer is started.
     */
    private Boolean lazyChannelCreation = true;
    /**
     * Sets the value for the blockWhenExhausted configuration attribute. It
     * determines whether to block when the borrowObject() method is invoked
     * when the pool is exhausted (the maximum number of active objects has been
     * reached).
     */
    private Boolean producerPoolBlockWhenExhausted = true;
    /**
     * Whether producer pool is enabled or not. Important: If you turn this off
     * then a single shared connection is used for the producer, also if you are
     * doing request/reply. That means there is a potential issue with
     * interleaved responses if replies comes back out-of-order. Therefore you
     * need to have a correlation id in both the request and reply messages so
     * you can properly correlate the replies to the Camel callback that is
     * responsible for continue processing the message in Camel. To do this you
     * need to implement NettyCamelStateCorrelationManager as correlation
     * manager and configure it via the correlationManager option. See also the
     * correlationManager option for more details.
     */
    private Boolean producerPoolEnabled = true;
    /**
     * Sets the cap on the number of idle instances in the pool.
     */
    private Integer producerPoolMaxIdle = 100;
    /**
     * Sets the cap on the number of objects that can be allocated by the pool
     * (checked out to clients, or idle awaiting checkout) at a given time. Use
     * a negative value for no limit.
     */
    private Integer producerPoolMaxTotal = -1;
    /**
     * Sets the maximum duration (value in millis) the borrowObject() method
     * should block before throwing an exception when the pool is exhausted and
     * producerPoolBlockWhenExhausted is true. When less than 0, the
     * borrowObject() method may block indefinitely.
     */
    private Long producerPoolMaxWait = -1L;
    /**
     * Sets the minimum amount of time (value in millis) an object may sit idle
     * in the pool before it is eligible for eviction by the idle object
     * evictor.
     */
    private Long producerPoolMinEvictableIdle = 300000L;
    /**
     * Sets the minimum number of instances allowed in the producer pool before
     * the evictor thread (if active) spawns new objects.
     */
    private Integer producerPoolMinIdle;
    /**
     * Only used for TCP when transferExchange is true. When set to true,
     * serializable objects in headers and properties will be added to the
     * exchange. Otherwise Camel will exclude any non-serializable objects and
     * log it at WARN level.
     */
    private Boolean allowSerializedHeaders = false;
    /**
     * Whether autowiring is enabled. This is used for automatic autowiring
     * options (the option must be marked as autowired) by looking up in the
     * registry to find if there is a single instance of matching type, which
     * then gets configured on the component. This can be used for automatic
     * configuring JDBC data sources, JMS connection factories, AWS Clients,
     * etc.
     */
    private Boolean autowiredEnabled = true;
    /**
     * To use a explicit ChannelGroup. The option is a
     * io.netty.channel.group.ChannelGroup type.
     */
    private ChannelGroup channelGroup;
    /**
     * To use a custom org.apache.camel.spi.HeaderFilterStrategy to filter
     * headers. The option is a org.apache.camel.spi.HeaderFilterStrategy type.
     */
    private HeaderFilterStrategy headerFilterStrategy;
    /**
     * Whether to use native transport instead of NIO. Native transport takes
     * advantage of the host operating system and is only supported on some
     * platforms. You need to add the netty JAR for the host operating system
     * you are using. See more details at:
     * http://netty.io/wiki/native-transports.html
     */
    private Boolean nativeTransport = false;
    /**
     * To use a custom org.apache.camel.component.netty.http.NettyHttpBinding
     * for binding to/from Netty and Camel Message API. The option is a
     * org.apache.camel.component.netty.http.NettyHttpBinding type.
     */
    private NettyHttpBinding nettyHttpBinding;
    /**
     * Allows to configure additional netty options using option. as prefix. For
     * example option.child.keepAlive=false to set the netty option
     * child.keepAlive=false. See the Netty documentation for possible options
     * that can be used.
     */
    private Map<String, Object> options;
    /**
     * The TCP/UDP buffer sizes to be used during inbound communication. Size is
     * bytes.
     */
    private Integer receiveBufferSize = 65536;
    /**
     * Configures the buffer size predictor. See details at Jetty documentation
     * and this mail thread.
     */
    private Integer receiveBufferSizePredictor;
    /**
     * The TCP/UDP buffer sizes to be used during outbound communication. Size
     * is bytes.
     */
    private Integer sendBufferSize = 65536;
    /**
     * Only used for TCP. You can transfer the exchange over the wire instead of
     * just the body. The following fields are transferred: In body, Out body,
     * fault body, In headers, Out headers, fault headers, exchange properties,
     * exchange exception. This requires that the objects are serializable.
     * Camel will exclude any non-serializable objects and log it at WARN level.
     */
    private Boolean transferExchange = false;
    /**
     * Path to unix domain socket to use instead of inet socket. Host and port
     * parameters will not be used, however required. It is ok to set dummy
     * values for them. Must be used with nativeTransport=true and
     * clientMode=false.
     */
    private String unixDomainSocketPath;
    /**
     * When netty works on nio mode, it uses default workerCount parameter from
     * Netty (which is cpu_core_threads x 2). User can use this option to
     * override the default workerCount from Netty.
     */
    private Integer workerCount;
    /**
     * To use a explicit EventLoopGroup as the boss thread pool. For example to
     * share a thread pool with multiple consumers or producers. By default each
     * consumer or producer has their own worker pool with 2 x cpu count core
     * threads. The option is a io.netty.channel.EventLoopGroup type.
     */
    private EventLoopGroup workerGroup;
    /**
     * A list of decoders to be used. You can use a String which have values
     * separated by comma, and have the values be looked up in the Registry.
     * Just remember to prefix the value with # so Camel knows it should lookup.
     */
    private String decoders;
    /**
     * A list of encoders to be used. You can use a String which have values
     * separated by comma, and have the values be looked up in the Registry.
     * Just remember to prefix the value with # so Camel knows it should lookup.
     */
    private String encoders;
    /**
     * Which protocols to enable when using SSL
     */
    private String enabledProtocols = "TLSv1.2,TLSv1.3";
    /**
     * To enable/disable hostname verification on SSLEngine
     */
    private Boolean hostnameVerification = false;
    /**
     * Client side certificate keystore to be used for encryption
     */
    private File keyStoreFile;
    /**
     * Keystore format to be used for payload encryption. Defaults to JKS if not
     * set
     */
    private String keyStoreFormat;
    /**
     * Client side certificate keystore to be used for encryption. Is loaded by
     * default from classpath, but you can prefix with classpath:, file:, or
     * http: to load the resource from different systems.
     */
    private String keyStoreResource;
    /**
     * Configures whether the server needs client authentication when using SSL.
     */
    private Boolean needClientAuth = false;
    /**
     * Password setting to use in order to encrypt/decrypt payloads sent using
     * SSH
     */
    private String passphrase;
    /**
     * Refers to a
     * org.apache.camel.component.netty.http.NettyHttpSecurityConfiguration for
     * configuring secure web resources. The option is a
     * org.apache.camel.component.netty.http.NettyHttpSecurityConfiguration
     * type.
     */
    private NettyHttpSecurityConfiguration securityConfiguration;
    /**
     * Security provider to be used for payload encryption. Defaults to SunX509
     * if not set.
     */
    private String securityProvider;
    /**
     * Setting to specify whether SSL encryption is applied to this endpoint
     */
    private Boolean ssl = false;
    /**
     * When enabled and in SSL mode, then the Netty consumer will enrich the
     * Camel Message with headers having information about the client
     * certificate such as subject name, issuer name, serial number, and the
     * valid date range.
     */
    private Boolean sslClientCertHeaders = false;
    /**
     * To configure security using SSLContextParameters. The option is a
     * org.apache.camel.support.jsse.SSLContextParameters type.
     */
    private SSLContextParameters sslContextParameters;
    /**
     * Reference to a class that could be used to return an SSL Handler. The
     * option is a io.netty.handler.ssl.SslHandler type.
     */
    private SslHandler sslHandler;
    /**
     * Server side certificate keystore to be used for encryption
     */
    private File trustStoreFile;
    /**
     * Server side certificate keystore to be used for encryption. Is loaded by
     * default from classpath, but you can prefix with classpath:, file:, or
     * http: to load the resource from different systems.
     */
    private String trustStoreResource;
    /**
     * Enable usage of global SSL context parameters.
     */
    private Boolean useGlobalSslContextParameters = false;

    public NettyConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(NettyConfiguration configuration) {
        this.configuration = configuration;
    }

    public Boolean getDisconnect() {
        return disconnect;
    }

    public void setDisconnect(Boolean disconnect) {
        this.disconnect = disconnect;
    }

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(Boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public Boolean getReuseChannel() {
        return reuseChannel;
    }

    public void setReuseChannel(Boolean reuseChannel) {
        this.reuseChannel = reuseChannel;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public Boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(Boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public Boolean getBridgeErrorHandler() {
        return bridgeErrorHandler;
    }

    public void setBridgeErrorHandler(Boolean bridgeErrorHandler) {
        this.bridgeErrorHandler = bridgeErrorHandler;
    }

    public Boolean getMuteException() {
        return muteException;
    }

    public void setMuteException(Boolean muteException) {
        this.muteException = muteException;
    }

    public Integer getBacklog() {
        return backlog;
    }

    public void setBacklog(Integer backlog) {
        this.backlog = backlog;
    }

    public Integer getBossCount() {
        return bossCount;
    }

    public void setBossCount(Integer bossCount) {
        this.bossCount = bossCount;
    }

    public EventLoopGroup getBossGroup() {
        return bossGroup;
    }

    public void setBossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
    }

    public Boolean getDisconnectOnNoReply() {
        return disconnectOnNoReply;
    }

    public void setDisconnectOnNoReply(Boolean disconnectOnNoReply) {
        this.disconnectOnNoReply = disconnectOnNoReply;
    }

    public EventExecutorGroup getExecutorService() {
        return executorService;
    }

    public void setExecutorService(EventExecutorGroup executorService) {
        this.executorService = executorService;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public NettyServerBootstrapFactory getNettyServerBootstrapFactory() {
        return nettyServerBootstrapFactory;
    }

    public void setNettyServerBootstrapFactory(
            NettyServerBootstrapFactory nettyServerBootstrapFactory) {
        this.nettyServerBootstrapFactory = nettyServerBootstrapFactory;
    }

    public LoggingLevel getNoReplyLogLevel() {
        return noReplyLogLevel;
    }

    public void setNoReplyLogLevel(LoggingLevel noReplyLogLevel) {
        this.noReplyLogLevel = noReplyLogLevel;
    }

    public LoggingLevel getServerClosedChannelExceptionCaughtLogLevel() {
        return serverClosedChannelExceptionCaughtLogLevel;
    }

    public void setServerClosedChannelExceptionCaughtLogLevel(
            LoggingLevel serverClosedChannelExceptionCaughtLogLevel) {
        this.serverClosedChannelExceptionCaughtLogLevel = serverClosedChannelExceptionCaughtLogLevel;
    }

    public LoggingLevel getServerExceptionCaughtLogLevel() {
        return serverExceptionCaughtLogLevel;
    }

    public void setServerExceptionCaughtLogLevel(
            LoggingLevel serverExceptionCaughtLogLevel) {
        this.serverExceptionCaughtLogLevel = serverExceptionCaughtLogLevel;
    }

    public ServerInitializerFactory getServerInitializerFactory() {
        return serverInitializerFactory;
    }

    public void setServerInitializerFactory(
            ServerInitializerFactory serverInitializerFactory) {
        this.serverInitializerFactory = serverInitializerFactory;
    }

    public Boolean getUsingExecutorService() {
        return usingExecutorService;
    }

    public void setUsingExecutorService(Boolean usingExecutorService) {
        this.usingExecutorService = usingExecutorService;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Boolean getLazyStartProducer() {
        return lazyStartProducer;
    }

    public void setLazyStartProducer(Boolean lazyStartProducer) {
        this.lazyStartProducer = lazyStartProducer;
    }

    public Long getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(Long requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public ClientInitializerFactory getClientInitializerFactory() {
        return clientInitializerFactory;
    }

    public void setClientInitializerFactory(
            ClientInitializerFactory clientInitializerFactory) {
        this.clientInitializerFactory = clientInitializerFactory;
    }

    public NettyCamelStateCorrelationManager getCorrelationManager() {
        return correlationManager;
    }

    public void setCorrelationManager(
            NettyCamelStateCorrelationManager correlationManager) {
        this.correlationManager = correlationManager;
    }

    public Boolean getLazyChannelCreation() {
        return lazyChannelCreation;
    }

    public void setLazyChannelCreation(Boolean lazyChannelCreation) {
        this.lazyChannelCreation = lazyChannelCreation;
    }

    public Boolean getProducerPoolBlockWhenExhausted() {
        return producerPoolBlockWhenExhausted;
    }

    public void setProducerPoolBlockWhenExhausted(
            Boolean producerPoolBlockWhenExhausted) {
        this.producerPoolBlockWhenExhausted = producerPoolBlockWhenExhausted;
    }

    public Boolean getProducerPoolEnabled() {
        return producerPoolEnabled;
    }

    public void setProducerPoolEnabled(Boolean producerPoolEnabled) {
        this.producerPoolEnabled = producerPoolEnabled;
    }

    public Integer getProducerPoolMaxIdle() {
        return producerPoolMaxIdle;
    }

    public void setProducerPoolMaxIdle(Integer producerPoolMaxIdle) {
        this.producerPoolMaxIdle = producerPoolMaxIdle;
    }

    public Integer getProducerPoolMaxTotal() {
        return producerPoolMaxTotal;
    }

    public void setProducerPoolMaxTotal(Integer producerPoolMaxTotal) {
        this.producerPoolMaxTotal = producerPoolMaxTotal;
    }

    public Long getProducerPoolMaxWait() {
        return producerPoolMaxWait;
    }

    public void setProducerPoolMaxWait(Long producerPoolMaxWait) {
        this.producerPoolMaxWait = producerPoolMaxWait;
    }

    public Long getProducerPoolMinEvictableIdle() {
        return producerPoolMinEvictableIdle;
    }

    public void setProducerPoolMinEvictableIdle(
            Long producerPoolMinEvictableIdle) {
        this.producerPoolMinEvictableIdle = producerPoolMinEvictableIdle;
    }

    public Integer getProducerPoolMinIdle() {
        return producerPoolMinIdle;
    }

    public void setProducerPoolMinIdle(Integer producerPoolMinIdle) {
        this.producerPoolMinIdle = producerPoolMinIdle;
    }

    public Boolean getAllowSerializedHeaders() {
        return allowSerializedHeaders;
    }

    public void setAllowSerializedHeaders(Boolean allowSerializedHeaders) {
        this.allowSerializedHeaders = allowSerializedHeaders;
    }

    public Boolean getAutowiredEnabled() {
        return autowiredEnabled;
    }

    public void setAutowiredEnabled(Boolean autowiredEnabled) {
        this.autowiredEnabled = autowiredEnabled;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void setChannelGroup(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    public HeaderFilterStrategy getHeaderFilterStrategy() {
        return headerFilterStrategy;
    }

    public void setHeaderFilterStrategy(
            HeaderFilterStrategy headerFilterStrategy) {
        this.headerFilterStrategy = headerFilterStrategy;
    }

    public Boolean getNativeTransport() {
        return nativeTransport;
    }

    public void setNativeTransport(Boolean nativeTransport) {
        this.nativeTransport = nativeTransport;
    }

    public NettyHttpBinding getNettyHttpBinding() {
        return nettyHttpBinding;
    }

    public void setNettyHttpBinding(NettyHttpBinding nettyHttpBinding) {
        this.nettyHttpBinding = nettyHttpBinding;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public Integer getReceiveBufferSize() {
        return receiveBufferSize;
    }

    public void setReceiveBufferSize(Integer receiveBufferSize) {
        this.receiveBufferSize = receiveBufferSize;
    }

    public Integer getReceiveBufferSizePredictor() {
        return receiveBufferSizePredictor;
    }

    public void setReceiveBufferSizePredictor(Integer receiveBufferSizePredictor) {
        this.receiveBufferSizePredictor = receiveBufferSizePredictor;
    }

    public Integer getSendBufferSize() {
        return sendBufferSize;
    }

    public void setSendBufferSize(Integer sendBufferSize) {
        this.sendBufferSize = sendBufferSize;
    }

    public Boolean getTransferExchange() {
        return transferExchange;
    }

    public void setTransferExchange(Boolean transferExchange) {
        this.transferExchange = transferExchange;
    }

    public String getUnixDomainSocketPath() {
        return unixDomainSocketPath;
    }

    public void setUnixDomainSocketPath(String unixDomainSocketPath) {
        this.unixDomainSocketPath = unixDomainSocketPath;
    }

    public Integer getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(Integer workerCount) {
        this.workerCount = workerCount;
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public String getDecoders() {
        return decoders;
    }

    public void setDecoders(String decoders) {
        this.decoders = decoders;
    }

    public String getEncoders() {
        return encoders;
    }

    public void setEncoders(String encoders) {
        this.encoders = encoders;
    }

    public String getEnabledProtocols() {
        return enabledProtocols;
    }

    public void setEnabledProtocols(String enabledProtocols) {
        this.enabledProtocols = enabledProtocols;
    }

    public Boolean getHostnameVerification() {
        return hostnameVerification;
    }

    public void setHostnameVerification(Boolean hostnameVerification) {
        this.hostnameVerification = hostnameVerification;
    }

    public File getKeyStoreFile() {
        return keyStoreFile;
    }

    public void setKeyStoreFile(File keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    public String getKeyStoreFormat() {
        return keyStoreFormat;
    }

    public void setKeyStoreFormat(String keyStoreFormat) {
        this.keyStoreFormat = keyStoreFormat;
    }

    public String getKeyStoreResource() {
        return keyStoreResource;
    }

    public void setKeyStoreResource(String keyStoreResource) {
        this.keyStoreResource = keyStoreResource;
    }

    public Boolean getNeedClientAuth() {
        return needClientAuth;
    }

    public void setNeedClientAuth(Boolean needClientAuth) {
        this.needClientAuth = needClientAuth;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public NettyHttpSecurityConfiguration getSecurityConfiguration() {
        return securityConfiguration;
    }

    public void setSecurityConfiguration(
            NettyHttpSecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    public String getSecurityProvider() {
        return securityProvider;
    }

    public void setSecurityProvider(String securityProvider) {
        this.securityProvider = securityProvider;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Boolean getSslClientCertHeaders() {
        return sslClientCertHeaders;
    }

    public void setSslClientCertHeaders(Boolean sslClientCertHeaders) {
        this.sslClientCertHeaders = sslClientCertHeaders;
    }

    public SSLContextParameters getSslContextParameters() {
        return sslContextParameters;
    }

    public void setSslContextParameters(
            SSLContextParameters sslContextParameters) {
        this.sslContextParameters = sslContextParameters;
    }

    public SslHandler getSslHandler() {
        return sslHandler;
    }

    public void setSslHandler(SslHandler sslHandler) {
        this.sslHandler = sslHandler;
    }

    public File getTrustStoreFile() {
        return trustStoreFile;
    }

    public void setTrustStoreFile(File trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    public String getTrustStoreResource() {
        return trustStoreResource;
    }

    public void setTrustStoreResource(String trustStoreResource) {
        this.trustStoreResource = trustStoreResource;
    }

    public Boolean getUseGlobalSslContextParameters() {
        return useGlobalSslContextParameters;
    }

    public void setUseGlobalSslContextParameters(
            Boolean useGlobalSslContextParameters) {
        this.useGlobalSslContextParameters = useGlobalSslContextParameters;
    }
}