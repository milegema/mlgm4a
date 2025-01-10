package com.github.milegema.mlgm4a.data.files;

public class RepositoryFileCallback implements FileAccessDataStateListener {

    public RepositoryFileCallback() {
    }

    @Override
    public void onDataStateChange(FileAccessDataState state, FileAccessDataEncoding encoding) {
        if (state == null) {
            return;
        }
        switch (state) {
            case decode_block_begin:
                this.onDecodeBlockBegin(state, encoding);
                break;
            case decode_block_end:
                this.onDecodeBlockEnd(state, encoding);
                break;
            case decode_layer_begin:
                this.onDecodeLayerBegin(state, encoding);
                break;
            case decode_layer_end:
                this.onDecodeLayerEnd(state, encoding);
                break;

            case encode_block_begin:
                this.onEncodeBlockBegin(state, encoding);
                break;
            case encode_block_end:
                this.onEncodeBlockEnd(state, encoding);
                break;
            case encode_layer_begin:
                this.onEncodeLayerBegin(state, encoding);
                break;
            case encode_layer_end:
                this.onEncodeLayerEnd(state, encoding);
                break;
            default:
                break;
        }
    }

    public void onEncodeBlockBegin(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onEncodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onEncodeLayerBegin(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onEncodeLayerEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }


    public void onDecodeBlockBegin(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onDecodeBlockEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onDecodeLayerBegin(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }

    public void onDecodeLayerEnd(FileAccessDataState state, FileAccessDataEncoding encoding) {
    }
}
