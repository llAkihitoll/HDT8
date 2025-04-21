import java.util.ArrayList;

public class VectorHeap<E extends Comparable<E>> {
    private ArrayList<E> data;

    public VectorHeap() {
        data = new ArrayList<>();
    }

    public void add(E item) {
        data.add(item);
        percolateUp(data.size() - 1);
    }

    public E remove() {
        if (data.isEmpty()) return null;
        E min = data.get(0);
        int last = data.size() - 1;
        data.set(0, data.get(last));
        data.remove(last);
        percolateDown(0);
        return min;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    private void percolateUp(int idx) {
        E val = data.get(idx);
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            E parentVal = data.get(parent);
            if (val.compareTo(parentVal) >= 0) break;
            data.set(idx, parentVal);
            idx = parent;
        }
        data.set(idx, val);
    }

    private void percolateDown(int idx) {
        int size = data.size();
        E val = data.get(idx);

        while (idx * 2 + 1 < size) {
            int child = idx * 2 + 1;
            int right = child + 1;

            if (right < size && data.get(right).compareTo(data.get(child)) < 0) {
                child = right;
            }

            if (data.get(child).compareTo(val) >= 0) break;

            data.set(idx, data.get(child));
            idx = child;
        }

        data.set(idx, val);
    }
}
