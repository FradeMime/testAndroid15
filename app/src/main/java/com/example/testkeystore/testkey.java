package com.example.testkeystore;
//
//import java.io.IOException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableEntryException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//
//import javax.crypto.SecretKey;
//
//public class testkey {
//    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
//    private static final String KEY_ALIAS         = "SignalSecret";
//
//    private static KeyStore getKeyStore() {
//        try {
//            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
//            keyStore.load(null);
//            return keyStore;
//        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
//            throw new AssertionError(e);
//        }
//    }
//
//    public SecretKey getkey(){
////        KeyStore keyStore = null;
//        KeyStore keyStore = getKeyStore();
////        Provider[] providers = Security.getProviders();
////        for (Provider provider : providers) {
////            Set<Provider.Service> services = provider.getServices();
////            for (Provider.Service service : services) {
////                String type = service.getType();
////                if (!type.equals("KeyStore")) {
////                    continue;
////                }
////                String algorithm = service.getAlgorithm();
////                try{
////                    KeyStore ks = KeyStore.getInstance(algorithm, provider);
////                    keyStore = ks;
////                } catch (KeyStoreException e) {
////                    throw new RuntimeException(e);
////                }
////            }
////        }
//
//        try {
//            // Attempt 1
//            return getSecretKey(keyStore);
//        } catch (UnrecoverableKeyException e) {
//            try {
//                // Attempt 2
//                return getSecretKey(keyStore);
//            } catch (UnrecoverableKeyException e2) {
//                throw new AssertionError(e2);
//            }
//        }
//    }
//
//
//    public SecretKey getSecretKey(KeyStore keyStore) throws UnrecoverableKeyException {
//        try {
//            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
//            return entry.getSecretKey();
//        } catch (UnrecoverableKeyException e) {
//            throw e;
//        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
//            throw new AssertionError(e);
//        }
//    }
//}


import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.io.IOException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class testkey {
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";
    private static final String KEY_ALIAS = "SignalSecret";



    private static KeyStore getKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            return keyStore;
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public SecretKey getkey() {
        KeyStore keyStore = getKeyStore();
        try {
            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            if (entry == null) {
                // 如果密钥不存在，生成新密钥
                generateKey();
                entry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);
            }
            return entry.getSecretKey();
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException e) {
            throw new AssertionError(e);
        }
    }
    public boolean hasKeyStoreEntry() {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            return keyStore.containsAlias(KEY_ALIAS) && keyStore.entryInstanceOf(KEY_ALIAS, KeyStore.SecretKeyEntry.class);
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
    private void generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE);
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT
            )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build();
            keyGenerator.init(keyGenParameterSpec);
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
