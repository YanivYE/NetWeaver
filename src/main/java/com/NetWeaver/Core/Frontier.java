package com.NetWeaver.Core;

import com.NetWeaver.Core.Entities.FrontierItem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Frontier {
    private final PriorityQueue<FrontierItem> pq = new PriorityQueue<>(
            Comparator.<FrontierItem>comparingDouble(FrontierItem::priority).reversed()
                    .thenComparingInt(FrontierItem::depth)
    );
    public void clear() { pq.clear(); }
    public void offer(FrontierItem item) { pq.offer(item); }
    public FrontierItem poll() { return pq.poll(); }
    public boolean isEmpty() { return pq.isEmpty(); }
}
