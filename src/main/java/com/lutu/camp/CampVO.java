package com.lutu.camp;

public class CampVO {

    private String name;
    private int max;
    private int min;
    private int count;
    private int[] camp;
    public CampVO(String name, int max, int min) {
        this.name = name;
        this.max = max;
        this.min = min;
        camp = new int[max];
    }
    public void add(int num) {
        camp[count] = num;
        count++;
    }
    public int getMax() {
        return max;
    }
    public int getMin() {
        return min;
    }
    public String getName() {
        return name;
    }
    public int[] getCamp() {
        return camp;
    }

}
