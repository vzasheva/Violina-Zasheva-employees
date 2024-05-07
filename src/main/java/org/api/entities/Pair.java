package org.api.entities;

public class Pair {
    private long firstEmployeeId;
    private long secondEmployeeId;
    private long duration;

    public Pair(long firstEmployeeId, long secondEmployeeId, long duration) {
        this.setFirstEmployeeId(firstEmployeeId);
        this.setSecondEmployeeId(secondEmployeeId);
        this.setDuration(duration);
    }

    public long getFirstEmployeeId() {
        return this.firstEmployeeId;
    }

    private void setFirstEmployeeId(long firstEmployeeId) {
        this.firstEmployeeId = firstEmployeeId;
    }

    public long getSecondEmployeeId() {
        return this.secondEmployeeId;
    }

    private void setSecondEmployeeId(long secondEmployeeId) {
        this.secondEmployeeId = secondEmployeeId;
    }

    public long getDuration() {
        return this.duration;
    }

    private void setDuration(long totalDuration) {
        this.duration = totalDuration;
    }

    public void addDuration(long overlap) {
        this.duration += overlap;
    }
}
