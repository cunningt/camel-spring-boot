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
package org.apache.camel.springboot.cli.connector;

import org.apache.camel.CamelContext;
import org.apache.camel.cli.connector.DefaultCliConnectorFactory;
import org.apache.camel.spi.CliConnectorFactory;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "camel.cli.enabled", matchIfMissing = true)
@ConditionalOnBean(type = "org.apache.camel.spring.boot.CamelAutoConfiguration")
@AutoConfigureAfter(name = "org.apache.camel.spring.boot.CamelAutoConfiguration")
@EnableConfigurationProperties({CliConnectorConfiguration.class})
public class CliConnectorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CliConnectorFactory.class)
    public CliConnectorFactory cliConnectorFactory(CamelContext camelContext, CliConnectorConfiguration config) {
        CliConnectorFactory answer = new DefaultCliConnectorFactory();
        answer.setEnabled(config.getEnabled());
        answer.setRuntime("Spring Boot");
        answer.setRuntimeVersion(SpringBootVersion.getVersion());

        // if packaged as fat-jar then we need to know what was the main class that started this integration
        try {
            Enumeration<URL> en = this.getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (en.hasMoreElements()) {
                URL u = en.nextElement();
                try (InputStream is = u.openStream()) {
                    Manifest manifest = new Manifest(is);
                    String sc = manifest.getMainAttributes().getValue("Start-Class");
                    if (sc != null) {
                        answer.setRuntimeStartClass(sc);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }

        return answer;
    }

}
