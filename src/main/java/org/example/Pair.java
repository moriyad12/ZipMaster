package org.example;

public class Pair implements Comparable<Pair> {
    private Integer first;
    private String second;

    public Pair(int first, String second) {
        this.first = first;
        this.second = second;
    }

    public Integer getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public int compareTo(Pair o) {
        if (this.first > o.first)
            return 1;
        else if (this.first == o.first)
            return 0;
        else return -1;
    }
}
