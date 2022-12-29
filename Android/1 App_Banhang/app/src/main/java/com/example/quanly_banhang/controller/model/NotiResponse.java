package com.example.quanly_banhang.controller.model;

public class NotiResponse {
    private long multicast_id;
    private int susccess,failure,canonical_ids;

    public NotiResponse() {
    }

    public long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSusccess() {
        return susccess;
    }

    public void setSusccess(int susccess) {
        this.susccess = susccess;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }
}
