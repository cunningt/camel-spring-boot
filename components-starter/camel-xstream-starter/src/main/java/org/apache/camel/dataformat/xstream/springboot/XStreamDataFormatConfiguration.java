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
package org.apache.camel.dataformat.xstream.springboot;

import java.util.List;
import javax.annotation.Generated;
import org.apache.camel.model.PropertyDefinition;
import org.apache.camel.spring.boot.DataFormatConfigurationPropertiesCommon;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Marshal and unmarshal POJOs to/from XML using XStream library.
 * 
 * Generated by camel-package-maven-plugin - do not edit this file!
 */
@Generated("org.apache.camel.springboot.maven.SpringBootAutoConfigurationMojo")
@ConfigurationProperties(prefix = "camel.dataformat.xstream")
public class XStreamDataFormatConfiguration
        extends
            DataFormatConfigurationPropertiesCommon {

    /**
     * Whether to enable auto configuration of the xstream data format. This is
     * enabled by default.
     */
    private Boolean enabled;
    /**
     * Adds permissions that controls which Java packages and classes XStream is
     * allowed to use during unmarshal from xml/json to Java beans. A permission
     * must be configured either here or globally using a JVM system property.
     * The permission can be specified in a syntax where a plus sign is allow,
     * and minus sign is deny. Wildcards is supported by using . as prefix. For
     * example to allow com.foo and all subpackages then specify com.foo..
     * Multiple permissions can be configured separated by comma, such as
     * com.foo.,-com.foo.bar.MySecretBean. The following default permission is
     * always included: -,java.lang.,java.util. unless its overridden by
     * specifying a JVM system property with they key
     * org.apache.camel.xstream.permissions.
     */
    private String permissions;
    /**
     * Sets the encoding to use
     */
    private String encoding;
    /**
     * Mode for dealing with duplicate references The possible values are:
     * NO_REFERENCES ID_REFERENCES XPATH_RELATIVE_REFERENCES
     * XPATH_ABSOLUTE_REFERENCES SINGLE_NODE_XPATH_RELATIVE_REFERENCES
     * SINGLE_NODE_XPATH_ABSOLUTE_REFERENCES
     */
    private String mode;
    /**
     * Whether the data format should set the Content-Type header with the type
     * from the data format. For example application/xml for data formats
     * marshalling to XML, or application/json for data formats marshalling to
     * JSON
     */
    private Boolean contentTypeHeader = true;
    /**
     * List of class names for using custom XStream converters. The classes must
     * be of type com.thoughtworks.xstream.converters.Converter
     */
    private List<PropertyDefinition> converters;
    /**
     * Alias a Class to a shorter name to be used in XML elements.
     */
    private List<PropertyDefinition> aliases;
    /**
     * Prevents a field from being serialized. To omit a field you must always
     * provide the declaring type and not necessarily the type that is
     * converted. Multiple values can be separated by comma.
     */
    private List<PropertyDefinition> omitFields;
    /**
     * Adds a default implicit collection which is used for any unmapped XML
     * tag. Multiple values can be separated by comma.
     */
    private List<PropertyDefinition> implicitCollections;

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Boolean getContentTypeHeader() {
        return contentTypeHeader;
    }

    public void setContentTypeHeader(Boolean contentTypeHeader) {
        this.contentTypeHeader = contentTypeHeader;
    }

    public List<PropertyDefinition> getConverters() {
        return converters;
    }

    public void setConverters(List<PropertyDefinition> converters) {
        this.converters = converters;
    }

    public List<PropertyDefinition> getAliases() {
        return aliases;
    }

    public void setAliases(List<PropertyDefinition> aliases) {
        this.aliases = aliases;
    }

    public List<PropertyDefinition> getOmitFields() {
        return omitFields;
    }

    public void setOmitFields(List<PropertyDefinition> omitFields) {
        this.omitFields = omitFields;
    }

    public List<PropertyDefinition> getImplicitCollections() {
        return implicitCollections;
    }

    public void setImplicitCollections(
            List<PropertyDefinition> implicitCollections) {
        this.implicitCollections = implicitCollections;
    }
}