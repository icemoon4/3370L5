
package edu.wit.cs.comp3370;

/* Implements methods to use a red-black tree 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 5
 * 
 */

/**
 * RBTree builds a red black binary search tree. All methods contained in this class perform the same
 * operations as BST.java, aside from insert. insert takes a DiskLocation, finds its parent using
 * findParent (from BST.java) and sets whether it will be a left or right child of its parent, then 
 * colors the node red and calls fixInsert. fixInsert takes the DiskLocation, and uses the color values
 * of parent and grandparent nodes to decide if the tree is unbalanced, which it will be if the current
 * tree doesn't match all of the required properties of a RB tree. Depending on which side is unabalanced,
 * a rotateLeft or rotateRight will be performed to shift the tree either left or right and make it
 * balanced. Colors of nodes are updated to match current properties of the tree.
 * 
 * @author palmerr
 *
 */
public class RBTree extends LocationHolder {
	
	/* sets a disk location's color to red.
	 * 
	 * Use this method on fix-insert instead of directly
	 * coloring nodes red to avoid setting nil as red.
	 */
	private void setRed(DiskLocation z) {
		if (z != nil)
			z.color = RB.RED;
	}
	
	@Override
	public DiskLocation find(DiskLocation d) {
		//start from root to search
		return find(d, root);
	}
	
	public DiskLocation find(DiskLocation d, DiskLocation curr){
		if(d == nil){
			return nil;
		}
		if(d.equals(curr)) //return d when its been found
			return curr;
		else if(!d.isGreaterThan(curr))
			return find(d, curr.left);
		else
			return find(d, curr.right);
	}

	@Override
	public DiskLocation next(DiskLocation d) { //returns successor
		if(d.right != nil){
			return min(d.right);
		}
		else{
			return up(d);
		}
	}
	
	public DiskLocation up(DiskLocation d){ //returns closest successor if d.right was nil
		DiskLocation p = d.parent;
		if(p == nil || d==p.left){
			return p;
		}
		else{
			return up(p);
		}
	}
	
	public DiskLocation min(DiskLocation d){
		while(d.left != nil){ //leftmost node is the smallest in the tree
			d = d.left;
		}
		return d;
	}

	@Override
	public DiskLocation prev(DiskLocation d) { //returns predecessor
		if(d.left != nil){
			return max(d.left);
		}
		else{
			return down(d);
		}
	}
	
	public DiskLocation down(DiskLocation d){ //returns closest predecessor if d.left was nil
		DiskLocation p = d.parent;
		if(p == nil || d==p.right){
			return p;
		}
		else{
			return down(p);
		}
	}
	
	public DiskLocation max(DiskLocation d){
		while(d.right != nil){ //the rightmost node is the largest in the tree
			d = d.right;
		}
		return d;
	}

	@Override
	public void insert(DiskLocation d) {
		if(root == null){ //setup tree for first time
			root = d;
			root.color = RB.BLACK;
			root.parent = nil;
			nil.parent = nil;
			nil.left = nil;
			nil.right = nil;
			nil.color = RB.BLACK;
			root.left = nil;
			root.right = nil;
			return;
		}
		d.parent = findParent(d, root, nil);
		
		if(!d.isGreaterThan(d.parent)) //set whether d is left or right child
			d.parent.left = d;
		else
			d.parent.right = d;
		d.left = nil;
		d.right = nil;
		
		setRed(d);
		fixInsert(d);

	}
	
	public DiskLocation findParent(DiskLocation d, DiskLocation curr, DiskLocation parent){
		//recursive calls depending on relation to other nodes, returns a parent when it comes to a node with no child that d could fit in
		if(curr.equals(nil)){
			return parent;
		}
		else if(!d.isGreaterThan(curr)){
			return findParent(d, curr.left, curr);
		}
		else{
			return findParent(d, curr.right, curr);
		}
	}
	
	public void fixInsert(DiskLocation d){
		while(d.parent.color == RB.RED){ //while parent is red
			if(d.parent.parent.left == d.parent){ //if d's parent is a left child
				DiskLocation y = d.parent.parent.right; //y = d's grandparent's right child
				if(y.color == RB.RED){
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				}
				else{
					if(d.parent.right == d){ //if d is a right child
						d = d.parent;
						rotateLeft(d);
					}
					d.parent.color = RB.BLACK;
					setRed(d.parent.parent);
					rotateRight(d.parent.parent); //balance tree towards the right
				}
			}
			else if(d.parent.parent.right == d.parent){ //if d's parent is right child
				DiskLocation y = d.parent.parent.left;
				if(y.color == RB.RED){ 
					d.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(d.parent.parent);
					d = d.parent.parent;
				}
				else{
					if(d.parent.left == d){ //if d is a left child
						d = d.parent;
						rotateRight(d);
					}
					d.parent.color = RB.BLACK;
					setRed(d.parent.parent);
					rotateLeft(d.parent.parent); //balance tree towards the left
				}
			}
		}
		root.color = RB.BLACK; //root must always be black
	}
	
	public void rotateLeft(DiskLocation d){
		DiskLocation y = d.right;
		d.right = y.left;
		if(!y.left.equals(nil)){
			y.left.parent = d; 
		}
		y.parent = d.parent; //sets the correct child to become the parent
		//set properties of new parent/child relationships
		if(d.parent.equals(nil))
			root = y;
		else if(d == d.parent.left)
			d.parent.left = y;
		else
			d.parent.right = y;
		y.left = d;
		d.parent = y;
	}
	
	public void rotateRight(DiskLocation d){
		DiskLocation y = d.left;
		d.left = y.right;
		if(!y.right.equals(nil)){
			y.right.parent = d; 
		}
		y.parent = d.parent; //sets the correct child to become the parent
		//set properties of new parent/child relationships
		if(d.parent.equals(nil))
			root = y;
		else if(d == d.parent.right)
			d.parent.right = y;
		else
			d.parent.left = y;
		y.right = d;
		d.parent = y;
	}

	@Override
	public int height() {
		int h = height(root); //counts the height starting from the root
		return h;
	}
	
	public int height(DiskLocation d){
		if(d == nil || (d.left==nil && d.right==nil)){ //if node is null, or it is a leaf
			return 0;
		}
		else{
			return 1 + Math.max(height(d.left), height(d.right)); //adds up the longest parent-child pair
		}
	}

}
