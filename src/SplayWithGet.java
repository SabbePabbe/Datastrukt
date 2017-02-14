/**
 * Created by jonatan gustafsson & sabrina samuelsson on 13/02/2017.
 */
public class SplayWithGet<E extends Comparable<? super E>>
                            extends BinarySearchTree<E>
                            implements CollectionWithGet<E> {
    @Override
    public E get(E e) {
        if(e == null) return null;
        Entry entry = find(e, root);
        if(entry == null){
            return null;
        }
        bubble(entry);
        return root.element;
    }

    protected void bubble ( Entry x){
        //När vi inte har en parent längre så är vi en root!
        while(x.parent != null){
            if(x.parent.left == x){

                //We are left child
                if(x.parent.parent != null){


                    if(x.parent.parent.left == x.parent) {
                        //We are left child of left child
                        zagzag(x.parent.parent);
                        x = x.parent.parent;
                    }else{
                        //We are left child of right child
                        doubleRotateLeft(x.parent.parent);
                        x = x.parent;
                    }
                    //"Swapped" place with grandparent

                }else {
                    //No grandparent, we are left child of root
                    rotateRight(x.parent);
                    x = x.parent;
                }

            }else {//We are right child
                if (x.parent.parent != null) {
                    if (x.parent.parent.left == x.parent) {
                        //We are right child of left child
                        doubleRotateRight(x.parent.parent);
                        x = x.parent;
                    } else {
                        //We are right child of right child
                        zigzig(x.parent.parent);
                        x = x.parent.parent;
                    }
                } else {
                    //No grandparent, we are right child of root
                    rotateLeft(x.parent);
                    x = x.parent;
                }
            }
        }
        root = x;
    }
    //Från labbinstruktionerna:
    /*Ni behöver endast utföra balanseringarna vid sökning i trädet,
    även om det normalt sker även vid insättning i och borttagning ur ett Splay-träd.*/
    
    /*Koden för balanseringarna: zig, zag, zigzag och zagzig, kan ni kopiera från AVL-trädet i zip-filen och
    bara döpa om metoderna samt stryka allt som har med höjden att göra. Koden för balanseringarna: zagzag
    och zigzig, skall ni skriva själva. Observera att benämningarna zigzag, zigzig osv inte används på riktigt
    samma sätt av alla.
     */
    /* Roterar höger runt x och sedan runt y
              x'                  z'
             / \                /   \
            A   y'     -->     y'    D
               / \            / \
              B   z          x'  C
                 / \        / \
                C   D      A   B
    */
    private void zigzig( Entry x ){
        Entry   y = x.right,
                z = x.right.right;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        x.right   = z.right;
        if(x.right != null) x.right.parent = x;
        y.right   = z.left;
        if(y.right != null) y.right.parent = y;
        z.left    = x.left;
        if(z.left != null) z.left.parent = z;
        z.right   = y.left;
        if(z.right != null) z.right.parent = z;
        y.left    = z;
        x.left    = y;
    }

    /* Roterar vänster runt x och sedan runt y
              x'                  z'
             / \                 / \
            y'  D      -->      A   y'
           / \                     / \
          z   C                   B   x'
         / \                         / \
        A   B                       C   D
    */
    private void zagzag( Entry x ){
        Entry   y = x.left,
                z = x.left.left;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        x.left   = z.left;
        if(x.left != null) x.left.parent = x;
        y.left   = z.right;
        if(y.left != null) y.left.parent = y;
        z.right    = x.right;
        if(z.right != null) z.right.parent = z;
        z.left   = y.right;
        if(z.left != null) z.left.parent = z;
        y.right    = z;
        x.right    = y;
    }

    

    /* Rotera 1 steg i hogervarv, dvs
              x'                 y'
             / \                / \
            y'  C   -->        A   x'
           / \                    / \
          A   B                  B   C
    */
    private void rotateRight( Entry x ) {
        Entry   y = x.left;
        E    temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left    = y.left;
        if ( x.left != null )
            x.left.parent   = x;
        y.left    = y.right;
        y.right   = x.right;
        if ( y.right != null )
            y.right.parent  = y;
        x.right   = y;
    } //   rotateRight
    // ========== ========== ========== ==========

    /* Rotera 1 steg i vanstervarv, dvs
              x'                 y'
             / \                / \
            A   y'  -->        x'  C
               / \            / \
              B   C          A   B
    */
    private void rotateLeft( Entry x ) {
        Entry  y  = x.right;
        E temp    = x.element;
        x.element = y.element;
        y.element = temp;
        x.right   = y.right;
        if ( x.right != null )
            x.right.parent  = x;
        y.right   = y.left;
        y.left    = x.left;
        if ( y.left != null )
            y.left.parent   = y;
        x.left    = y;
    } //   rotateLeft
    // ========== ========== ========== ==========

    /* Rotera 2 steg i hogervarv, dvs
              x'                  z'
             / \                /   \
            y'  D   -->        y'    x'
           / \                / \   / \
          A   z'             A   B C   D
             / \
            B   C
    */
    private void doubleRotateRight( Entry x ) {
        Entry   y = x.left,
                z = x.left.right;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        y.right   = z.left;
        if ( y.right != null )
            y.right.parent = y;
        z.left    = z.right;
        z.right   = x.right;
        if ( z.right != null )
            z.right.parent = z;
        x.right   = z;
        z.parent  = x;
    }  //  doubleRotateRight
    // ========== ========== ========== ==========

    /* Rotera 2 steg i vanstervarv, dvs
               x'                  z'
              / \                /   \
             A   y'   -->       x'    y'
                / \            / \   / \
               z   D          A   B C   D
              / \
             B   C
     */
    private void doubleRotateLeft( Entry x ) {
        Entry  y  = x.right,
                z  = x.right.left;
        E      e  = x.element;
        x.element = z.element;
        z.element = e;
        y.left    = z.right;
        if ( y.left != null )
            y.left.parent = y;
        z.right   = z.left;
        z.left    = x.left;
        if ( z.left != null )
            z.left.parent = z;
        x.left    = z;
        z.parent  = x;
    } //  doubleRotateLeft


}
