package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.repositories.blocks.BlockID;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.security.AlgorithmManager;
import com.github.milegema.mlgm4a.security.AlgorithmProvider;
import com.github.milegema.mlgm4a.security.HashProvider;
import com.github.milegema.mlgm4a.security.hash.Hash;
import com.github.milegema.mlgm4a.security.hash.HashUtils;
import com.github.milegema.mlgm4a.security.hash.Sum;
import com.github.milegema.mlgm4a.security.providers.AlgorithmProviders;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTest {

    @Test
    public void useHashProviderWithReset() {

        AlgorithmManager alg_man = AlgorithmProviders.getAlgorithmManager();
        HashProvider hp = (HashProvider) alg_man.findProvider(Hash.SHA1);
        Hash computer = hp.hash();
        byte[] data = {2, 3, 5, 7, 11, 17, 19};
        byte[] sum_old = null;

        for (int i = 5; i > 0; i--) {
            byte[] sum_new = computer.compute(data).toByteArray();
            if (sum_old == null) {
                sum_old = sum_new;
            } else {
                Assert.assertArrayEquals(sum_new, sum_old);
            }
        }
    }

    @Test
    public void useHash() {

        List<ByteSlice> data_list = new ArrayList<>();

        data_list.add(new ByteSlice());
        data_list.add(new ByteSlice("hello, sum".getBytes()));
        data_list.add(new ByteSlice(new byte[]{11, 22, 33, 44, 99}));
        AlgorithmManager alg_man = AlgorithmProviders.getAlgorithmManager();

        for (ByteSlice data : data_list) {

            byte[] buffer = data.toByteArray();

            String md5 = HashUtils.hexSum(buffer, Hash.MD5);
            String sha1 = HashUtils.hexSum(buffer, Hash.SHA1);
            String sha256 = HashUtils.hexSum(buffer, Hash.SHA256);

            String md5sum = this.computeHashWithProvider(Hash.MD5, buffer, alg_man);
            String sha1sum = this.computeHashWithProvider(Hash.SHA1, buffer, alg_man);
            String sha256sum = this.computeHashWithProvider(Hash.SHA256, buffer, alg_man);


            Assert.assertEquals(md5sum, md5);
            Assert.assertEquals(sha1sum, sha1);
            Assert.assertEquals(sha256sum, sha256);

            BlockID id = new BlockID(sha256);

            Logs.info("data: " + Arrays.toString(buffer));
            Logs.info("        md5: " + md5);
            Logs.info("      sha-1: " + sha1);
            Logs.info("    sha-256: " + sha256);
            Logs.info("   block-id: " + id);

        }

        // Assert.assertEquals(4, 2 + 2);
    }

    private String computeHashWithProvider(String algorithm, byte[] buffer, AlgorithmManager am) {
        AlgorithmProvider provider = am.findProvider(algorithm);
        HashProvider hp = (HashProvider) provider;
        Sum sum = hp.hash().compute(buffer);
        return sum.toString();
    }
}
