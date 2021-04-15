package org.umiskky;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/14
 */
@Slf4j
public class Test {

    @org.junit.Test
    public void test(){
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        System.out.println(String.valueOf(key));
    }
}
