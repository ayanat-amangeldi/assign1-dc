package edu.aitu.dsa.metrics;

public final class Metrics {
    public long comparisons, swaps, allocs, maxDepth, timeNanos;
    private long curDepth;

    public void reset(){ comparisons=swaps=allocs=maxDepth=timeNanos=0; curDepth=0; }
    public void push(){ if(++curDepth>maxDepth) maxDepth=curDepth; }
    public void pop(){ if(curDepth>0) curDepth--; }
    public void alloc(long n){ allocs+=n; }
    public void startTimer(){ timeNanos=-System.nanoTime(); }
    public void stopTimer(){ timeNanos+=System.nanoTime(); }
}
