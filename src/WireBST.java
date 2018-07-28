// this class holds the data structure of wire binary search tree


//import WireBSTNode; //needed?

public class WireBST{


    private WireBSTNode _head;
    private WireBSTNode _median;
    private int _nodeCounter;


    /**
     * constractor
     */
    public WireBST()
    {
        _head = null ;
        _median = null;
        _nodeCounter = 0;
    }



    public WireBSTNode getHead(){
        return _head;
    }

    public void setHead(WireBSTNode newHead){
        _head = newHead ;
    }

    private int getNodeCounter(){
        return this._nodeCounter;

    }


    /**
     * insert of the node to the WireBST
     * @param studentNum the student number
     * @param studentName the student name
     */
    public boolean insertWireBSTNode(int studentNum , String studentName){
        WireBSTNode newNode = new WireBSTNode(studentNum , studentName);
        WireBSTNode currentNode = this.getHead(); // alias, its a pointer to where i will insert the new node
        boolean insertedFlag = false;

        if( currentNode == null ) // empty tree case
        {
            this.setHead(newNode);
            this.setMedian(newNode);
            _nodeCounter ++ ;
            return true;
        }

        if( searchWireBST(newNode.getStudentNum()) != null ){
            System.out.println( Constants.ERROR_INSERT);
            return false;
        }


        while ( ! insertedFlag )
        {
            if (currentNode.getStudentNum() > newNode.getStudentNum())
                if ( ! currentNode.isRealLeft())
                {
                    insertToLeft(currentNode , newNode);
                    insertedFlag = true;
                }
                else
                    currentNode = currentNode.getLeft();
            else
                if ( ! currentNode.isRealRight())
                {

                    insertToRight(currentNode , newNode);
                    insertedFlag = true;
                }
                else
                    currentNode = currentNode.getRight();
        }
        updateMedianInsert(newNode);
        return true;
    }

    /**
     * insert the new node to the left of the current node.
     * @param currentNode is the node that will get a new left son.
     * @param newNode is the node to add.
     */
    private void insertToLeft(WireBSTNode currentNode , WireBSTNode newNode ){
        newNode.setParent(currentNode);
        newNode.setLeft(currentNode.getLeft());  // make the predeccessor of the leaf to be the predeccessor of the new node.
        currentNode.setLeft(newNode);  // insert the new node to her place!
        newNode.setRight(currentNode); // make the successor of the new node to be her parent, because it is the successor (only in insert to right!).
    }

    /**
     * insert the new node to the right of the current node.
     * @param currentNode is the node that will get a new right son.
     * @param newNode is the node to add.
     */
    private void insertToRight(WireBSTNode currentNode , WireBSTNode newNode ){
        newNode.setParent(currentNode);
        newNode.setRight(currentNode.getRight());  // make the successor of the leaf to be the successor of the new node.
        currentNode.setRight(newNode);  // insert the new node to her place!
        newNode.setLeft(currentNode); // make the predecessor of the new node to be her parent, because it is the predecessor (only in insert to left!).
    }

    /**
     * update the median after insert a node
     * @param node, the node that inserted
     */
    private void updateMedianInsert(WireBSTNode newNode){
        if(newNode.getStudentNum() > this.getMedianWireBST().getStudentNum() ) {
            if (this.getNodeCounter() % 2 == 0) {
                this.setMedian(this.getMedianWireBST().getSuccessor());
            }
        }
        else if (this.getNodeCounter() % 2 == 1) {
            this.setMedian(this.getMedianWireBST().getPredecessor());
        }
        this._nodeCounter ++;
    }

    /**
     * remove the student that is number is the given parameter
     * @param studentNum, the node who include that number
     * @return true if the node was removed, else false
     */
    public boolean removeWireBSTNode(int studentNum){
        wireBSTNode temp = searchWireBST( studentNum );
        if (temp == null){
            return false;
        } else {
           removeWireBSTNode(temp);
           return true;
        }
    }

    /**
     * overloading the removeWireBSTNode function, remove node who exist in the tree
     * @param node is the node to remove.
     */
    public void removeWireBSTNode(WireBSTNode node){
        if ( ! node.isRealLeft() && ! node.isRealRight() ){
            removeDeepLeaf(node);
        }
        if ((node.isRealRight()) && (!node.isRealLeft())){    //** in case of removing node who has only right son
            removeNodeWithOnlyRightSon(node);
        }
        if ((!node.isRealRight()) && (node.isRealLeft())){    //** in case of removing node who has only left son
            removeNodeWithOnlyLeftSon(node);
        }
        if ((node.isRealRight()) && (node.isRealLeft())) {    //** in case of removing node who has two sons
            removeNodeWithTwoSons(node);
        }
    }

    /**
     * removing a deep leaf. used by the removeWiredBSTNode function
     * @param node. the node we wish to remove
     */
    private void removeDeepLeaf(WireBSTNode node){
        if (node.getParent() == null){                              //in case node is root and has no sons
            this.setHead(null);
            this.setMedian(null);
        }
        else if (node.equal(node.getParent().getRight())) {
            updateMedianRemove(node);
            node.getParent().setRight(node.getRight());
        }
        else {
            updateMedianRemove(node);
            node.getParent().setLeft(node.getLeft());
        }
    }

    /**
     * update the median after remove of a node
     * @param node, the node that removed
     */
    private void updateMedianRemove(WireBSTNode node){
        if (this.getNodeCounter() % 2 == 0) {
            if (node.getStudentNum() <= this.getMedianWireBST().getStudentNum())
                this.setMedian(this.getMedianWireBST().getSuccessor());
        }
        else  {
            if (node.getStudentNum() >= this.getMedianWireBST().getStudentNum())
            this.setMedian(this.getMedianWireBST().getPredecessor());
        }
        this._nodeCounter --;
    }

    /**
     * removing a node with only right son
     * @param node. the node we wish to remove
     */
    private void removeNodeWithOnlyRightSon(WireBSTNode node){
        if (node.getParent() == null){
            updateMedianRemove(node);
            this.setHead(node.getRight());
            node.getRight().setRight();
            node.getRight().setLeft();
        }
        if else (node.equal(node.getParent().getRight())){
            updateMedianRemove(node);
            node.getRight().setParent(node.getParent());
            node.getParent().setRight(node.getRight());
            node.getRight().setLeft(node.getLeft());

        }
        else {
            updateMedianRemove(node);
            node.getRight().setParent(node.getParent);
            node.getParent().setLeft(node.getRight());
            node.getRight().setLeft(node.getLeft());
        }
    }



    /**
    * removing a node with only left son
    * @param node. the node we wish to remove
    */
    private void removeNodeWithOnlyLeftSon(WireBSTNode node){
        if (node.getParent() == null){
            updateMedianRemove(node);
            _head = node.getLeft();

        }
        if else (node.equal(node.getParent().getRight())){
            updateMedianRemove(node);
            node.getLeft().setParent(node.getParent());
            node.getParent().setRight(node.getLeft());
            node.getLeft().setRight(node.getRight());
        }
        else {
            updateMedianRemove(node);
            node.getParent().setLeft(node.getLeft());
            node.getLeft().setParent(node.getParent());
            node.getLeft().setRight(node.getRight());
        }
    }

    /**
     * remove node who has two sons
     * @param node with two sons
     */
    private void removeNodeWithTwoSons(WireBSTNode node){
        WireBSTNode temp = node.getSuccessor();
        if (node.getParent() == null )){
            this.setHead(temp);
            temp.setRight(node.getRight());
            temp.setLeft(node.getLeft());
        } else if (node.equal(node.getParent().getRight())){
            temp.setRight(node.getRight());
            temp.setLeft(node.getLeft());
            node.getParent().setRight(temp);
        } else {
            temp.setRight(node.getRight());
            temp.setLeft(node.getLeft());
            node.getParent().setLeft(temp);
        }
    }


    /**
     *
     * @param studentNum the key to look for in the tree.
     * @return pointer to the node if find it in the tree, or null if its not in the tree.
     */
    public WireBSTNode searchWireBST(int studentNum){
        if ( this.getHead() == null)  // edge case of null or empty tree
            return null;
        WireBSTNode temp = this.getHead();
        while( ( temp != null ) && ( studentNum != temp.getStudentNum() ) ){
            if( ( studentNum < temp.getStudentNum() )) {
                if (temp.isRealLeft())
                    temp = temp.getLeft();
                else
                    temp = null;
            }
            else {
                if (temp.isRealRight())
                    temp = temp.getRight();
                else
                    temp = null;
            }
        }
        return temp ;
    }

    /**
     * find the Successor of the node WireBST.
     * @param node is the current node, that looking for her's successor.
     * @return the successor of node.
     */
    public WireBSTNode getSuccessor(WireBSTNode node){
        if ( node == null )
            return null;
        if ( ! node.isRealRight() )     // if the right son isnt real, so he is the successor!
            return node.getRight();
        WireBSTNode temp;
        for( temp = node.getRight(); temp.isRealLeft() ; temp = temp.getLeft() )  // temp one step to the right, and then run all the way left, till he reach a non-real son.
            ;
        return temp ;
    }

    /**
     * find the Predecessor of the node WireBST.
     * @param node is the current node, that looking for her's predecessor.
     * @return the predecessor of node.
     */
    public WireBSTNode getPredecessor(WireBSTNode node){
        if ( node == null )
            return null;
        if ( ! node.isRealLeft() )     // if the left son isnt real, so he is the successor!
            return node.getLeft();
        WireBSTNode temp;
        for(temp = node.getLeft(); temp.isRealRight() ; temp = temp.getRight() )  // temp go one step to the left, and then run all the way right, till he reach a non-real son.
            ;
        return temp ;
    }

    /*
    * find the minimum node WireBST
    * the leftest node in the BST is the min node in it
    * */
    public WireBSTNode minWireBST(){
        if( this.getHead() == null )
            return null;
        WireBSTNode temp = this.getHead();
        while (temp.getLeft() != null)
            temp = temp.getLeft();
        return temp;
    }

    /*
    * find the maximum node WireBST
    * the rightest node in the BST is the max node in it
    * */
    public WireBSTNode maxWireBST(){
        if( this.getHead() == null )
            return null;
        WireBSTNode temp = this.getHead();
        while (temp.getRight() != null)
            temp = temp.getRight();
        return temp;
    }

    /**
     * print the wireBST as pre oder
     * @param node
     */
    public void preOrderScan(WireBSTNode node){

        if(node == null) return;

        printNode(node);

        preOrderScan(node.getLeft());

        preOrderScan(node.getRight());

    }

    /**
     * print the wireBST as in order
     * @param node
     */
    public void inOrderScan(){

        WireBSTNode temp = minWireBST();

        for (; temp != null; temp = temp.getRight()){
            printNode(temp);
        }

    }

    /**
     * print the wireBST as post order
     * @param node
     */
    public void postOrderScan(WireBSTNode node){

        if (node == null) return;

        postOrderScan(node.getLeft());

        postOrderScan(node.getRight());

        printNode(node);

    }

    /**
     *
     * @param node
     */
    private void printNode(WireBSTNode node){
        System.out.println(node.getStudentNum() + " ");
        System.out.println(node.getStudentName() + " ");
    }

    /**
     *
     * @return the median WireBST
     */
    public WireBSTNode getMedianWireBST()
    {
        return _median;

    }

    /**
     *
     * @param median is the new median to be set.
     */
    private void setMedian(WireBSTNode median) {
        this._median = median;
    }

    // Function to print binary tree in 2D
    // It does reverse inorder traversal
    // i translate it from C. cradit to: https://www.geeksforgeeks.org/print-binary-tree-2-dimensions/
    public static void print2DNodeWireBST(WireBSTNode head, int space)
    {
        // Base case
        if (head == null)
            return;

        // Increase distance between levels
        space += Constants.COUNT;

        // Process right child first
        print2DNodeWireBST(head.getRight() , space);

        // Print current node after space count
        System.out.println();    // go one line down
        for (int i = Constants.COUNT ; i < space; i++)
            System.out.println(" ");
        System.out.println(Integer.valueOf(head.getStudentNum()) + head.getStudentName() + "\n" );

        // Process left child
        print2DNodeWireBST(head.getLeft(), space);
    }


    /*  this is for the gui, if will be implement.
    * public void print2DWireBST(){
    *     WireBSTNode.print2DNodeWireBST(getHead(), 0 );
    * }
    *
    * public void sayHello(ActionEvent actionEvent) {
    *     helloWorld.setText("Hello World!");
    * }
    *
    *
    * */

}   // end of WireBST class