/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.kork.plugins.config

import com.fasterxml.jackson.core.type.TypeReference
import com.netflix.spinnaker.config.PluginsConfigurationProperties.PluginRepositoryProperties
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext

import io.mockk.every
import io.mockk.mockk
import org.spekframework.spek2.dsl.Fixture
import org.spockframework.util.CollectionUtil.listOf
import java.net.URL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isSuccess

@SpringBootTest(
  properties = ["spring.config.location=classpath:testplugin/plugin-empty-config.yml"],
  classes = [com.netflix.spinnaker.kork.plugins.config.SpringEnvironmentConfigResolver::class]
)
class SpringEnvironmentConfigResolverTest : JUnit5Minutests {

  @Autowired
  lateinit var configResolver: SpringEnvironmentConfigResolver

  fun tests() = rootContext<Fixture> {
   /* fixture {
      Fixture()
    }*/

   /* test("plugin config with shortened config path") {
      expectThat(
        subject.resolve(
          PluginConfigCoordinates("netflix.sweet-plugin", "some-config"),
          TestPluginConfig::class.java
        )
      )
        .isA<TestPluginConfig>()
        .and {
          get { somestring }.isEqualTo("overridden default")
        }
    }

    test("plugin extension with shortened config path") {
      expectThat(
        subject.resolve(
          ExtensionConfigCoordinates("netflix.sweet-plugin", "netflix.foo"),
          TestExtensionConfig::class.java
        )
      )
        .isA<TestExtensionConfig>()
        .and {
          get { somestring }.isEqualTo("overridden default")
          get { someint }.isEqualTo(10)
          get { optional }.isNull()
          get { somelist }.hasSize(1)
            .get { first().hello }.isEqualTo("Future Rob")
        }
    }

    test("plugin extension where config properties are not present and the config class does not have a no-arg constructor") {
      expectCatching {
        subject.resolve(
          ExtensionConfigCoordinates("netflix.sweet-plugin", "config.nonexistent"),
          MissingNoArgConstructor::class.java
        )
      }.isSuccess()

      expectThat(
        subject.resolve(
          ExtensionConfigCoordinates("netflix.sweet-plugin", "config.nonexistent"),
          MissingNoArgConstructor::class.java
        )
      )
        .isA<MissingNoArgConstructor>()
    }

    test("plugin extension with expanded path") {
      expectThat(
        subject.resolve(
          ExtensionConfigCoordinates("netflix.very-important", "orca.stage"),
          TestExtensionConfig::class.java
        )
      )
        .isA<TestExtensionConfig>()
        .and {
          get { somestring }.isEqualTo("default")
          get { someint }.isEqualTo(15)
          get { optional }.isEqualTo("some new value")
          get { somelist }.isEmpty()
        }
    }

    test("system extension config path") {
      expectThat(
        subject.resolve(
          SystemExtensionConfigCoordinates("netflix.bar"),
          TestExtensionConfig::class.java
        )
      )
        .isA<TestExtensionConfig>()
        .and {
          get { somestring }.isEqualTo("default")
          get { someint }.isEqualTo(15)
          get { optional }.isNull()
          get { somelist }.hasSize(2)
            .and {
              get { get(0).hello }.isEqualTo("one")
              get { get(1).hello }.isEqualTo("two")
            }
        }
    }

    test("loading repository configs") {
      expectThat(
        subject.resolve(
          RepositoryConfigCoordinates(),
          object : TypeReference<HashMap<String, PluginRepositoryProperties>>() {}
        )
      )
        .isA<Map<String, PluginRepositoryProperties>>()
        .and {
          get { get("foo") }
            .isNotNull()
            .get { url }.isEqualTo(URL("http://localhost:9000"))
        }
    }

    test("loading repository with empty config") {
      expectThat(
        configResolver.resolve(
          RepositoryConfigCoordinates(),
          object : TypeReference<HashMap<String, PluginRepositoryProperties>>() {}
        )
      )
        .isA<Map<String,String>>()
    }*/
  }

 /* private inner class Fixture {
    val environment: ConfigurableEnvironment = mockk(relaxed = true)
    val subject = SpringEnvironmentConfigResolver(environment)

    init {
      every { environment.propertySources } returns MutablePropertySources().apply {
        addFirst(MapPropertySource("test", properties))
      }
    }
  }*/

  internal class TestPluginConfig(
    var somestring: String = "default"
  )

  internal class TestExtensionConfig(
    var somestring: String = "default",
    var someint: Int = 15,
    var optional: String? = null,
    var somelist: List<NestedConfig> = listOf()
  )

  internal class MissingNoArgConstructor(var param: Any?)

  internal class NestedConfig(
    var hello: String
  )

 /* private val properties = mapOf<String, Any?>(
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.enabled" to "true",
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.some-config.config.somestring" to "overridden default",
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.extensions.netflix.foo.config.somestring" to "overridden default",
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.extensions.netflix.foo.config.someint" to 10,
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.extensions.netflix.foo.config.somelist[0].hello" to "Future Rob",
    "spinnaker.extensibility.plugins.netflix.very-important.extensions.orca.stage.config.optional" to "some new value",
    "spinnaker.extensibility.extensions.netflix.bar.config.somelist[0].hello" to "one",
    "spinnaker.extensibility.extensions.netflix.bar.config.somelist[1].hello" to "two",
    "spinnaker.extensibility.repositories.foo.url" to "http://localhost:9000",
    "spinnaker.extensibility.plugins.netflix.sweet-plugin.config.somestring" to "overridden default"
  )*/
}
