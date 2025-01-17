/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.kork.plugins

import com.netflix.spinnaker.config.PluginsAutoConfiguration
import com.netflix.spinnaker.kork.plugins.testplugin.api.TestExtension
import com.netflix.spinnaker.kork.plugins.testplugin.basicGeneratedPlugin
import com.netflix.spinnaker.kork.plugins.v2.PluginFrameworkInitializer

import dev.minutest.rootContext

import org.pf4j.DefaultPluginDescriptor
import org.pf4j.PluginState
import org.pf4j.PluginWrapper
import org.spockframework.util.CollectionUtil.listOf
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.test.context.assertj.AssertableApplicationContext
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isNotNull

class PluginSystemTest {

  fun tests() = rootContext {
    derivedContext<ApplicationContextRunner>("initialization tests") {
      /*fixture {
        ApplicationContextRunner()
          .withPropertyValues(
            "spring.application.name=kork"
          )
          .withConfiguration(
            AutoConfigurations.of(
              PluginsAutoConfiguration::class.java
            )
          )
      }*/

      test("supports no configuration") {
        run { ctx: AssertableApplicationContext ->
          expect {
            that(ctx.getBean("pluginManager")).isA<SpinnakerPluginManager>()
            that(ctx.getBean("pluginFrameworkInitializer")).isA<PluginFrameworkInitializer>()
          }
        }
      }

      test("SpinnakerPluginManager is initialized properly and usable") {
        run { ctx: AssertableApplicationContext ->
          val pluginManager = ctx.getBean("pluginManager") as SpinnakerPluginManager
          val testPluginWrapper = PluginWrapper(
            pluginManager,
            DefaultPluginDescriptor(
              "Armory.TestPlugin",
              "desc",
              "TestPlugin.java",
              "1.0.0",
              "",
              "Armory",
              "Apache"
            ),
            null,
            null
          )
          testPluginWrapper.pluginState = PluginState.DISABLED
          pluginManager.setPlugins(listOf(testPluginWrapper))

          pluginManager.enablePlugin("Armory.TestPlugin")
        }
      }
    }

    derivedContext<GeneratedPluginFixture>("plugin loading tests") {
      fixture {
        GeneratedPluginFixture()
      }

     /* test("An extension from an external plugin is available from the pluginManager") {
        app.run { ctx: AssertableApplicationContext ->
          val pluginManager = ctx.getBean("pluginManager") as SpinnakerPluginManager
          expectThat(pluginManager.getPlugin(plugin.descriptor.pluginId)).isNotNull()
          val extensions: List<TestExtension> = pluginManager.getExtensions(TestExtension::class.java, plugin.descriptor.pluginId)
          expectThat(extensions).isNotEmpty()
          expectThat(extensions).hasSize(1)
          expectThat(extensions.first().testValue).isEqualTo("${testPluginName}Extension")
        }
      }*/

     /* test("Extensions are registered as beans") {
        app.run { ctx: AssertableApplicationContext ->
          val extensions = ctx.getBeansOfType(TestExtension::class.java).filterKeys {
            !it.endsWith("SystemExtension")
          }
          expectThat(extensions).isNotEmpty()
          expectThat(extensions).hasSize(1)
          expectThat(extensions.values.first().testValue).isEqualTo("${testPluginName}Extension")
        }
      }*/
    }
  }

  private inner class GeneratedPluginFixture {
    val app = ApplicationContextRunner()
      .withPropertyValues(
        "spring.application.name=kork",
        "spinnaker.extensibility.plugins-root-path=${plugin.rootPath.toAbsolutePath()}",
        "spinnaker.extensibility.plugins.${plugin.descriptor.pluginId}.enabled=true",
        "spinnaker.extensibility.plugins.spinnaker.pluginsystemtesttestplugin.extensions.spinnaker.pluginsystemtest-test-extension.config.foo=foo"
      )
      /*.withConfiguration(
        AutoConfigurations.of(
          PluginsAutoConfiguration::class.java
        )
      )*/
  }

  // companion to avoid generating a plugin per test case
  companion object GeneratedPlugin {
    val testPluginName: String = "PluginSystemTest"
    val plugin = basicGeneratedPlugin(testPluginName).generate()
  }
}
