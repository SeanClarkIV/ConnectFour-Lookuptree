
/** Binary tree with the current node as the root.*/
public class TreeNode 
{
  private int value;
  private TreeNode left; //null if no left child.
  private TreeNode right;//null if no right child.
  
  /** A tree with root value, left subtree, and right subtree. */
  	public TreeNode(TreeNode left, int v, TreeNode right) 
  		{
	  		this.value= v;
	  		this.left= left;
	  		this.right= right;
  		}
  
  /** Return true iff this tree is a Binary Search Tree (BST).*/
  	public boolean isBST() 
  		{
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
  
  	public static void main(String[] args) 
  		{
	  
  			TreeNode d = new TreeNode(new TreeNode(new TreeNode(null,1,null),2,new TreeNode(null,3,null)),4,new TreeNode(new TreeNode(null,5,null),7,new TreeNode(null,90,null)));
  			System.out.println(d.isBST());
    
  		}
  
}
