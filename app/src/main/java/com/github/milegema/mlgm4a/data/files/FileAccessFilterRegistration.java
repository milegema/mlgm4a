package com.github.milegema.mlgm4a.data.files;

public class FileAccessFilterRegistration {

    private FileAccessFilter filter;
    private boolean enabled;
    private FileAccessFilterOrder order;

    /**
     * 当 order==auto 时,用于辅助排序
     */
    private int index;

    public FileAccessFilterRegistration() {
        this.enabled = true;
        this.order = FileAccessFilterOrder.Auto;
    }

    public FileAccessFilter getFilter() {
        return filter;
    }

    public void setFilter(FileAccessFilter filter) {
        this.filter = filter;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public FileAccessFilterOrder getOrder() {
        return order;
    }

    public void setOrder(FileAccessFilterOrder order) {
        this.order = order;
    }
}
