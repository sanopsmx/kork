/*
 * Copyright 2022 Apple Inc.
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

package com.netflix.spinnaker.kork.secrets.user;

import com.netflix.spinnaker.kork.secrets.EncryptedSecret;
import com.netflix.spinnaker.kork.secrets.SecretConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SecretConfiguration.class)
public class UserSecretManagerTest {

  @Autowired UserSecretManager userSecretManager;

  @Test
  public void getTestSecret() {
    var ref = UserSecretReference.parse("secret://noop?v=test");
    var secret = userSecretManager.getUserSecret(ref);
    Assertions.assertEquals("test", secret.getSecretString("v"));
    Assertions.assertEquals("opaque", secret.getType());
    Assertions.assertTrue(secret.getRoles().isEmpty());
  }

  @Test
  public void getTestSecretString() {
    var ref = UserSecretReference.parse("secret://noop?foo=bar");
    var userSecret = userSecretManager.getUserSecret(ref);
    Assertions.assertEquals("bar", userSecret.getSecretString("foo"));
  }

  @Test
  public void getTestExternalSecret() {
    var ref = EncryptedSecret.parse("encrypted:noop!v:test");
    Assertions.assertNotNull(ref);
    var secret = userSecretManager.getExternalSecretString(ref);
    Assertions.assertEquals("test", secret);
  }
}
