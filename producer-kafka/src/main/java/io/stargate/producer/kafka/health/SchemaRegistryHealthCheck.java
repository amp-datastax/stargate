/*
 * Copyright 2018-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.stargate.producer.kafka.health;

import com.codahale.metrics.health.HealthCheck;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;

public class SchemaRegistryHealthCheck extends HealthCheck {
  private final CachedSchemaRegistryClient schemaRegistryClient;

  public SchemaRegistryHealthCheck(String schemaRegistryUrl) {
    schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 10);
  }

  @Override
  protected Result check() {
    try {
      schemaRegistryClient.getAllSubjects();
      return Result.builder().healthy().withMessage("Schema Registry is UP").build();
    } catch (Exception e) {
      return Result.builder().unhealthy(e).withMessage("Schema Registry is DOWN").build();
    }
  }
}
