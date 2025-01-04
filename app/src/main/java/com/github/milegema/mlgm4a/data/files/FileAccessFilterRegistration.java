package com.github.milegema.mlgm4a.data.files;

public class FileAccessFilterRegistration {

    private FileAccessFilter filter;
    private boolean enabled;
    private int order;

    public FileAccessFilterRegistration() {
        this.enabled = true;
        this.order = FileAccessFilterOrder.Auto.ordinal();
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
