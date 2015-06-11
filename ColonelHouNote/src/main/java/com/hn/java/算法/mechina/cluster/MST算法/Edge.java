package com.hn.java.算法.mechina.cluster.MST算法;

/**
 * 边距 包含两端数据点(城市)的索引
 * @author duyf
 *
 */
class Edge {
    
    private int i;
    private int j;
    private double w;

    Edge(int i, int j, double w) {
        this.i = i;
        this.j = j;
        this.w = w;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getW() {
        return w;
    }

}

