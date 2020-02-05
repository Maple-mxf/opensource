package io.jopen.springboot.plugin.limit;

/**
 * @author maxuefeng
 * @see Keeper
 * @see SimpleKeeperImpl
 * @since 2020/2/5
 */
public final class ViolationRecord implements java.io.Serializable {

    /**
     * 首次违规操作
     */
    private long firstViolationTime;

    /**
     * 最后一次违规操作
     */
    private long endViolationTime;

    /**
     * 违规次数
     */
    private int violationCount;

    public ViolationRecord(long firstViolationTime, long endViolationTime, int violationCount) {
        this.firstViolationTime = firstViolationTime;
        this.endViolationTime = endViolationTime;
        this.violationCount = violationCount;
    }

    public long getFirstViolationTime() {
        return firstViolationTime;
    }

    public void setFirstViolationTime(long firstViolationTime) {
        this.firstViolationTime = firstViolationTime;
    }

    public long getEndViolationTime() {
        return endViolationTime;
    }

    public void setEndViolationTime(long endViolationTime) {
        this.endViolationTime = endViolationTime;
    }

    public int getViolationCount() {
        return violationCount;
    }

    public void setViolationCount(int violationCount) {
        this.violationCount = violationCount;
    }
}
