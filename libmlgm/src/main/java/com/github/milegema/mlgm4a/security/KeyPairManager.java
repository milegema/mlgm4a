package com.github.milegema.mlgm4a.security;

public interface KeyPairManager {

    // methods

    KeyPairHolder get(KeyPairAlias alias);


    KeyPairHolder getRoot();

    KeyPairAlias[] listAliases();

    // agent

    public class Agent {
        public static KeyPairManager getKeyPairManager() {
            KeyPairManager man = inst;
            if (man == null) {
                man = new KeyPairManagerImpl();
                inst = man;
            }
            return man;
        }

        private static KeyPairManager inst;
    }
}
