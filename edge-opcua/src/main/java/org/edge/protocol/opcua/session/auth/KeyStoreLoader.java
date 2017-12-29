/*
 * Copyright (c) 2016 Kevin Herron and others
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v1.0 which accompany
 * this distribution.
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html and the
 * Eclipse Distribution License is available at http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.edge.protocol.opcua.session.auth;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import org.edge.protocol.opcua.api.common.EdgeOpcUaCommon;

public class KeyStoreLoader {

  private static final String CLIENT_ALIAS = "client-ai";
  private static final String SERVER_ALIAS = "server-ai";
  private static final char[] PASSWORD = "password".toCharArray();

  private X509Certificate clientCertificate;
  private KeyPair clientKeyPair;
  private X509Certificate serverCertificate;
  private KeyPair serverKeyPair;

  /**
   * load key store
   * @param  void
   * @return KeyStoreLoader
   */
  public KeyStoreLoader load(int mode) throws Exception {
    KeyStore keyStore = KeyStore.getInstance("PKCS12");

    if (EdgeOpcUaCommon.BOTH_MODE == mode) {
      keyStore.load(getClass().getClassLoader().getResourceAsStream("demo-certs.pfx"), PASSWORD);
    } else if (EdgeOpcUaCommon.SERVER_MODE == mode) {
      keyStore.load(getClass().getClassLoader().getResourceAsStream("demo-server.pfx"), PASSWORD);
    } else if (EdgeOpcUaCommon.CLIENT_MODE == mode) {
      keyStore.load(getClass().getClassLoader().getResourceAsStream("demo-client.pfx"), PASSWORD);
    } else {
      return this;
    }

    Key clientPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
    if (clientPrivateKey instanceof PrivateKey) {
      clientCertificate = (X509Certificate) keyStore.getCertificate(CLIENT_ALIAS);
      PublicKey clientPublicKey = clientCertificate.getPublicKey();
      clientKeyPair = new KeyPair(clientPublicKey, (PrivateKey) clientPrivateKey);
    }

    Key serverPrivateKey = keyStore.getKey(SERVER_ALIAS, PASSWORD);
    if (serverPrivateKey instanceof PrivateKey) {
      serverCertificate = (X509Certificate) keyStore.getCertificate(SERVER_ALIAS);
      PublicKey serverPublicKey = serverCertificate.getPublicKey();
      serverKeyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
    }

    return this;
  }

  /**
   * get client certificate
   * @param  void
   * @return X509Certificate
   */
  public X509Certificate getClientCertificate() {
    return clientCertificate;
  }

  /**
   * get client pair
   * @param  void
   * @return KeyPair
   */
  public KeyPair getClientKeyPair() {
    return clientKeyPair;
  }

  /**
   * get server pair
   * @param  void
   * @return X509Certificate
   */
  public X509Certificate getServerCertificate() {
    return serverCertificate;
  }

  /**
   * get server pair
   * @param  void
   * @return KeyPair
   */
  public KeyPair getServerKeyPair() {
    return serverKeyPair;
  }

}
