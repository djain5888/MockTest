package com.kanishque.mocktest.Models;

public class ListItem {

   private String test_id,gid,test_name,test_marks;
    private Long test_time;

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_marks() {
        return test_marks;
    }

    public void setTest_marks(String test_marks) {
        this.test_marks = test_marks;
    }

    public Long getTest_time() {
        return test_time;
    }

    public void setTest_time(Long test_time) {
        this.test_time = test_time;
    }

    public ListItem(String test_id, String gid, String test_name, String test_marks, long test_time) {
        this.test_id = test_id;
        this.gid = gid;
        this.test_name = test_name;
        this.test_marks = test_marks;
        this.test_time = test_time;
    }
}
