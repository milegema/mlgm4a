package com.github.milegema.mlgm4a.data.files;

public final class FileAccessOptions {

    public boolean signature; // 对数据块签名
    public boolean hash; // 计算数据块的 sum
    public boolean hash_with_salt; // 计算 sum 之前需要加盐
    public boolean compression; // 压缩数据
    public boolean encryption; // 加密数据

    public FileAccessOptions() {
        this.compression = true;
        this.encryption = true;
        this.hash = true;
        this.signature = true;
        this.hash_with_salt = true;
    }

    public static FileAccessOptions forSecretKey() {
        FileAccessOptions op = new FileAccessOptions();
        op.signature = true;
        return op;
    }

    public static FileAccessOptions forRegularBlock() {
        FileAccessOptions op = new FileAccessOptions();
        op.signature = false;
        return op;
    }


    public static FileAccessOptions forDefault(FileAccessRequest req, FileAccessBlock block) {
        if (req != null) {
            FileAccessOptions op = req.getOptions();
            if (op != null) {
                return op;
            }
        }
        return forRegularBlock();
    }

}
