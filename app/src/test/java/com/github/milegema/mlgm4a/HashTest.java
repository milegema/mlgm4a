package com.github.milegema.mlgm4a;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTest {
    @Test
    public void useHash() {

        List<ByteSlice> data_list = new ArrayList<>();

        data_list.add(new ByteSlice());
        data_list.add(new ByteSlice("hello, sum".getBytes()));
        data_list.add(new ByteSlice(new byte[]{11, 22, 33, 44, 99}));


        for (ByteSlice data : data_list) {

            byte[] buffer = data.toByteArray();

            String md5 = Hash.hexSum(buffer, Hash.MD5);
            String sha1 = Hash.hexSum(buffer, Hash.SHA1);
            String sha256 = Hash.hexSum(buffer, Hash.SHA256);

            BlockID id = new BlockID(sha256);

            Logs.info("data: " + Arrays.toString(buffer));
            Logs.info("        md5: " + md5);
            Logs.info("      sha-1: " + sha1);
            Logs.info("    sha-256: " + sha256);
            Logs.info("   block-id: " + id);

        }

        // Assert.assertEquals(4, 2 + 2);
    }
}
