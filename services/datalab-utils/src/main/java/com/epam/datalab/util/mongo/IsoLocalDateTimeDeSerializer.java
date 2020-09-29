/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.epam.datalab.util.mongo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.epam.datalab.util.mongo.IsoDateDeSerializer.DATE_NODE;

public class IsoLocalDateTimeDeSerializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectCodec oc = p.getCodec();
        JsonNode node = oc.readTree(p);
        if (node.get(DATE_NODE) != null) {
            String dateValue = node.get(DATE_NODE).asText();
            return Instant.ofEpochMilli(Long.valueOf(dateValue)).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
        } else {
            return Instant.ofEpochMilli(node.asLong()).atZone(ZoneOffset.systemDefault()).toLocalDateTime();
        }
    }
}