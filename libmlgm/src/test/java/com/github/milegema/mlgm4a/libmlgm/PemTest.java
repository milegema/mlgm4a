package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.pem.PEMBlock;
import com.github.milegema.mlgm4a.data.pem.PEMDocument;
import com.github.milegema.mlgm4a.data.pem.PEMUtils;
import com.github.milegema.mlgm4a.utils.ByteSlice;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PemTest {

    @Test
    public void usePEM() {

        List<PEMBlock> src = new ArrayList<>();
        this.prepareSourceData(src, 10);

        PEMDocument doc = new PEMDocument();
        doc.setBlocks(src.toArray(new PEMBlock[0]));
        String text = PEMUtils.encode(doc);
        PEMDocument doc2 = PEMUtils.decode(text);
        PEMBlock[] dst = doc2.getBlocks();

        final int count1 = src.size();
        final int count2 = dst.length;

        Assert.assertEquals(count1, count2);

        if (count1 == count2) {
            for (int i = 0; i < count2; i++) {
                PEMBlock b1 = src.get(i);
                PEMBlock b2 = dst[i];
                byte[] data1 = b1.getData().toByteArray();
                byte[] data2 = b2.getData().toByteArray();
                Assert.assertArrayEquals(data1, data2);
                Assert.assertEquals(b1.getType(), b2.getType());
            }
        }
    }


    private void prepareSourceData(List<PEMBlock> src, int count) {
        long now = System.currentTimeMillis();
        Random rand = new Random(now);
        for (int i = 0; i < count; i++) {
            byte[] buffer = new byte[i * 256];
            rand.nextBytes(buffer);
            PEMBlock block = new PEMBlock();
            block.setType("test-data");
            block.setData(new ByteSlice(buffer));
            src.add(block);
        }
    }
}
