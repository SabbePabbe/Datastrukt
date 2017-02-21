
/**
 * Created by jonatan gustafsson & sabrina samuelsson on 09/02/2017.
 * Simple sorted list of Comparable objects orderd "small to big".
 * If the list is made using non Comparable objects,
 * no elements can be added to the list and functions will be bork.
 */

public class SLCWithGet<E extends Comparable<? super E>>
        extends LinkedCollection<E>
        implements CollectionWithGet<E>{


    /**
     * Adds an element to the collection.
     * The element will be placed before the first element which is bigger
     * according to the element.compareTo method
     *
     * @param element the comparable object to add into the list
     * @return true if the comparable object has been added to the list. False if object doesn't implement Comparable.
     * @throws NullPointerException if parameter <tt>element<tt> is null.
     */
    @Override
    public boolean add( E element ) {
        if ( element == null ){
            throw new NullPointerException();
        }else {
            if(head == null){
                head = new Entry(element, null);
            } else {
                Entry current = head;
                Entry previous;
                if (element.compareTo(current.element) < 0) {
                    head = new Entry(element, current);
                    return true;
                }
                while (current.next != null) {
                    previous = current;
                    current = current.next;
                    if (element.compareTo(current.element) < 0) {
                        previous.next = new Entry(element, current);
                        return true;
                    }
                }
                current.next = new Entry(element, null);

            }
            return true;

        }

    }



    /**
     *  Find the first occurence of an element
     *  in the collection that is equal to the argument
     *  <tt>comparable</tt> with respect to its natural order.
     *  I.e. <tt>comparable.compareTo(element)</tt> is 0.
     *
     *  @param comparable The dummy element to compare to.
     *  @return  An element  <tt>comparable'</tt> in the collection
     *           satisfying <tt>comparable.compareTo(comparable') == 0</tt>.
     *           If no element is found, <tt>null</tt> is returned
     */
    @Override
    public E get(E comparable) {
        Entry current = head;
        if(current != null) {
            int comp = 0;
            if ((comp = comparable.compareTo(current.element)) == 0) {
                return current.element;
            }
            while (current.next != null) {
                current = current.next;
                if ((comp = comparable.compareTo(current.element)) == 0) {
                    return current.element;
                } else if (comp < 0){
                    return null;
                }
            }
        }
        return null;
    }
}
