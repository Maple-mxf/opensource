package io.jopen.core.common.model;

/**
 * @author maxuefeng
 * @since 2019/8/15
 */
public class HTableCell {

    private String rowKey;

    /*family-qualifier  family-qualifier*/
    private String familyAndQualifier;

    private Long version;

    private String val;

    public HTableCell(String rowKey, String familyAndQualifier, Long version, String val) {
        this.rowKey = rowKey;
        this.familyAndQualifier = familyAndQualifier;
        this.version = version;
        this.val = val;
    }

    public HTableCell() {
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getFamilyAndQualifier() {
        return familyAndQualifier;
    }

    public void setFamilyAndQualifier(String familyAndQualifier) {
        this.familyAndQualifier = familyAndQualifier;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "HTableCell{" +
                "rowKey='" + rowKey + '\'' +
                ", familyAndQualifier='" + familyAndQualifier + '\'' +
                ", version=" + version +
                ", val='" + val + '\'' +
                '}';
    }
}
