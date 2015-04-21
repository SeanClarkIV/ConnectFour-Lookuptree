/** A representation of a binary tree with the current node as the root.
  *  Invariant: There are no duplicate values in the tree. */
public class TreeNode {
  private int value;
  private TreeNode left; //null if no left child.
  private TreeNode right;//null if no right child.
  
  /** Constructor: A tree with root value v, left subtree left, and right subtree right. */
  public TreeNode(TreeNode left, int v, TreeNode right) {
    this.value= v;
    this.left= left;
    this.right= right;
  }
  
  /** Return true iff this tree is a Binary Search Tree (BST).
    *  A BST has these following properties:
    *  1. The values in the left subtree of every TreeNode in 
    *     this tree are less than the TreeNode's value.
    *  2. The values in the right subtree of every TreeNode in
    *     this tree are greater than the TreeNode's value.
    *  3. The left and right subtree each must also be a binary search tree.
    * 
    *  Precondition: This tree has no duplicate values.
    */
  public boolean isBST() {
	  boolean result=false;
	  if(left ==null || right == null)
	  {
		  return true;
	  }
	  if(left.value < value && right.value > value)
	  {
		  result = left.isBST();
		  result = right.isBST();
	  }
	  else
	  {
		  return false;
	  }
    return result;
  }
  
  public static void main(String[] args) {
	  
	  TreeNode d = new TreeNode(new TreeNode(new TreeNode(null,1,null),2,new TreeNode(null,3,null)),4,new TreeNode(new TreeNode(null,5,null),7,new TreeNode(null,90,null)));
	  
	  System.out.println(d.isBST());
    
  }
  
}
