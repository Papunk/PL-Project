package API;


import java.util.HashSet;
import java.util.Set;

interface LNSet<E> {
    void addElement();
    void removeElement();
    boolean contains(E element);
    boolean isEmpty();
    void clear();
    int size();
}

public class LNSetStruct<E> /*implements LNSet*/ {
    private Set<E> elements = new HashSet<E>();
}
