package core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class IndexesList<E> implements Iterable<E> {
    private List<E> l;
    private LinkedList<Integer> il;

    public IndexesList() {
        this(null);
    }

    public IndexesList(List<? super E> l) {
        setSuperList(l);
        this.il = new LinkedList<>();
    }

    public List<? super E> getSuperList() {
        return l;
    }

    public void setSuperList(List<? super E> l) {
        this.l = (List<E>) l;
    }

    public boolean add(Integer i) {
        return il.add(i);
    }

    public Integer add(E e) {
        il.add(l.size());
        l.add(e);
        return l.size() - 1;
    }

    public int size() {
        return il.size();
    }

    public Integer get(int index) {
        return il.get(index);
    }

    public boolean remove(Integer i) {
        return il.remove(i);
    }

    public Integer remove(int index) {
        return il.remove(index);
    }

    public boolean remove(E e) {
        return il.remove((Integer) l.indexOf(e));
    }

    public void removeAll(Collection<? extends E> c) {
        c.forEach(i -> il.remove(l.indexOf(i)));
    }

    public void addAll(Collection<? extends E> c) {
        for (int i = 0; i < c.size(); ++i)
            il.addFirst(l.size() + i);
        l.addAll(c);
    }

    public List<E> list() {
        return il.stream().map(l::get).collect(Collectors.toList());
    }

    public Iterator<E> iterator() {
        return list().iterator();
    }

    @Override
    public IndexesList<E> clone() {
        IndexesList<E> o = new IndexesList<>();
        o.il = new LinkedList<>(il);
        return o;
    }
}
