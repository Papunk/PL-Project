package API;


import java.util.HashSet;
import java.util.Set;

interface LNSet<E> {
    void addElement(E element);
    void removeElement(E element);
    boolean contains(E element);
    boolean isEmpty();
    void clear();
    int size();
}

public class LNSetStruct<E> implements LNSet {
    private Set<E> elements = new HashSet<E>();

    @SuppressWarnings("unchecked")
    @Override
    public void addElement(Object element) {
        elements.add((E) element);
        // TODO Auto-generated method stub

    }

    @Override
    public void removeElement(Object element) {
        if(elements.contains(element)) {
            elements.remove(element);
        }
        // TODO Auto-generated method stub

    }

    @Override
    public boolean contains(Object element) {
        // TODO Auto-generated method stub
        return elements.contains(element);
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return elements.isEmpty();
    }

    @Override
    public void clear() {
        elements.clear();
        // TODO Auto-generated method stub

    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return elements.size();
    }

    public String toString() {
        return elements.toString();
    }
}