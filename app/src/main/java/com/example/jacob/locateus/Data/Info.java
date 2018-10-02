package com.example.jacob.locateus.Data;

public class Info {
    String groupName;
    int memberCount;

    public Info() {
    }

    public Info(String groupName, int memberCount) {
        this.groupName = groupName;
        this.memberCount = memberCount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }
}
