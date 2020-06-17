package com.imaginarycode.minecraft.redisbungee.test;

import com.imaginarycode.minecraft.redisbungee.RedisUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedisUtilTest {
    @Test
    public void testRedisLuaCheck() {
        Assertions.assertTrue(RedisUtil.canUseLua("2.6.0"));
        Assertions.assertFalse(RedisUtil.canUseLua("2.2.12"));
        Assertions.assertFalse(RedisUtil.canUseLua("1.2.4"));
        Assertions.assertTrue(RedisUtil.canUseLua("2.8.4"));
        Assertions.assertTrue(RedisUtil.canUseLua("3.0.0"));
        Assertions.assertTrue(RedisUtil.canUseLua("3.2.1"));
    }
}
