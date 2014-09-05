/**
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
package org.apache.camel.component.netty4.http;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.camel.component.netty4.ClientPipelineFactory;
import org.apache.camel.component.netty4.NettyConfiguration;
import org.apache.camel.component.netty4.NettyProducer;
import org.apache.camel.component.netty4.http.handlers.HttpClientChannelHandler;
import org.apache.camel.component.netty4.ssl.SSLEngineFactory;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link org.apache.camel.component.netty.ClientPipelineFactory} for the Netty HTTP client.
 */
public class HttpClientPipelineFactory extends ClientPipelineFactory {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClientPipelineFactory.class);
    protected NettyHttpConfiguration configuration;
    private NettyHttpProducer producer;
    private SSLContext sslContext;

    public HttpClientPipelineFactory() {
        // default constructor needed
    }

    public HttpClientPipelineFactory(NettyHttpProducer nettyProducer) {
        this.producer = nettyProducer;
        try {
            this.sslContext = createSSLContext(producer);
        } catch (Exception e) {
            throw ObjectHelper.wrapRuntimeCamelException(e);
        }

        if (sslContext != null) {
            LOG.info("Created SslContext {}", sslContext);
        }
        configuration = nettyProducer.getConfiguration();
    }

    @Override
    public ClientPipelineFactory createPipelineFactory(NettyProducer nettyProducer) {
        return new HttpClientPipelineFactory((NettyHttpProducer) nettyProducer);
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        // create a new pipeline
        ChannelPipeline pipeline = ch.pipeline();

        SslHandler sslHandler = configureClientSSLOnDemand();
        if (sslHandler != null) {
            //TODO must close on SSL exception
            //sslHandler.setCloseOnSSLException(true);
            LOG.debug("Client SSL handler configured and added as an interceptor against the ChannelPipeline: {}", sslHandler);
            pipeline.addLast("ssl", sslHandler);
        }

        pipeline.addLast("http", new HttpClientCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(configuration.getChunkedMaxContentLength()));

        if (producer.getConfiguration().getRequestTimeout() > 0) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Using request timeout {} millis", producer.getConfiguration().getRequestTimeout());
            }
            ChannelHandler timeout = new ReadTimeoutHandler(producer.getConfiguration().getRequestTimeout(), TimeUnit.MILLISECONDS);
            pipeline.addLast("timeout", timeout);
        }

        // handler to route Camel messages
        pipeline.addLast("handler", new HttpClientChannelHandler(producer));

        
    }

    private SSLContext createSSLContext(NettyProducer producer) throws Exception {
        NettyConfiguration configuration = producer.getConfiguration();

        if (!configuration.isSsl()) {
            return null;
        }

        SSLContext answer;

        // create ssl context once
        if (configuration.getSslContextParameters() != null) {
            answer = configuration.getSslContextParameters().createSSLContext();
        } else {
            if (configuration.getKeyStoreFile() == null && configuration.getKeyStoreResource() == null) {
                LOG.debug("keystorefile is null");
            }
            if (configuration.getTrustStoreFile() == null && configuration.getTrustStoreResource() == null) {
                LOG.debug("truststorefile is null");
            }
            if (configuration.getPassphrase().toCharArray() == null) {
                LOG.debug("passphrase is null");
            }

            SSLEngineFactory sslEngineFactory;
            if (configuration.getKeyStoreFile() != null || configuration.getTrustStoreFile() != null) {
                sslEngineFactory = new SSLEngineFactory();
                answer = sslEngineFactory.createSSLContext(producer.getContext().getClassResolver(),
                        configuration.getKeyStoreFormat(),
                        configuration.getSecurityProvider(),
                        "file:" + configuration.getKeyStoreFile().getPath(),
                        "file:" + configuration.getTrustStoreFile().getPath(),
                        configuration.getPassphrase().toCharArray());
            } else {
                sslEngineFactory = new SSLEngineFactory();
                answer = sslEngineFactory.createSSLContext(producer.getContext().getClassResolver(),
                        configuration.getKeyStoreFormat(),
                        configuration.getSecurityProvider(),
                        configuration.getKeyStoreResource(),
                        configuration.getTrustStoreResource(),
                        configuration.getPassphrase().toCharArray());
            }
        }

        return answer;
    }

    private SslHandler configureClientSSLOnDemand() throws Exception {
        if (!producer.getConfiguration().isSsl()) {
            return null;
        }

        if (producer.getConfiguration().getSslHandler() != null) {
            return producer.getConfiguration().getSslHandler();
        } else if (sslContext != null) {
            SSLEngine engine = sslContext.createSSLEngine();
            engine.setUseClientMode(true);
            return new SslHandler(engine);
        }

        return null;
    }

}
