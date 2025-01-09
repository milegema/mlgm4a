package com.github.milegema.mlgm4a.data.repositories.blocks;

public enum BlockType {

    ////////////////////////////////
    // base

    BLOB,
    Properties,
    Table,
    FooBar,

    Ref,

    ////////////////////////////////
    // keys

    KeyPair,
    SecretKey,
    SecretKeySignature,

    ////////////////////////////////
    // blocks

    Root,
    App,
    User,
    Domain,
    Account,
    Scene,
    Passcode,
}
