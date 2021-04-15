package org.umiskky.model.pcap.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/15
 */
public class SymmetricEncryptionKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1499101349704747989L;

    private static final int KEY_LENGTH = 16;

    @Getter
    private final byte[] key;

    protected SymmetricEncryptionKey(){
        key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    }

    protected SymmetricEncryptionKey(byte[] key){
        this.key = key;
    }

    /**
     * @description The method getInstance is used to get an object of Class SymmetricEncryptionKey.
     * @param
     * @return org.umiskky.model.pcap.util.SymmetricEncryptionKey
     * @author umiskky
     * @date 2021/4/15-22:38
     */
    public static SymmetricEncryptionKey getInstance(){
        return new SymmetricEncryptionKey();
    }

    public static SymmetricEncryptionKey getInstance(byte[] key){
        if(key.length == KEY_LENGTH){
            return new SymmetricEncryptionKey(key);
        }
        return null;
    }

    public static SymmetricEncryptionKey getInstance(String key){
        byte[] keyBytes = key.getBytes();
        if(keyBytes.length == KEY_LENGTH){
            return new SymmetricEncryptionKey(keyBytes);
        }
        return null;
    }

    @Override
    public String toString() {
        return Arrays.toString(key);
    }
}
