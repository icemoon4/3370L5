package edu.wit.cs.comp3370;

/**
 * BST builds a binary search tree, starting with insert to insert the track, sector pairs into the tree
 * in order of smaller DiskLocations being stored as a left child, and the larger DiskLocations being
 * stored as a right child. next() is used to return the closest successor to d in the tree, while prev()
 * returns the closest predecessor to d in the tree. Both are used to either return the values before or
 * after it quickly. up() is an extension of next() functionality, while down() helps prev(). insert()
 * operates by creating the root given the very first DiskLocation, and for all subsequent insertions, 
 * uses findParent() to find which value should be the parent of d. It moves down the tree (either left or
 * right depending on whether the value is smaller or larger than a node) until it finds a nil section
 * where d can be stored. Then insert() continues to actually add d to one of the child nodes of its parent.
 * height() starts from the root of the tree, and counts the largest number of parent-child connections.
 * The largest branch is the height of the tree. Finally, find() returns the track, sector pair of a given
 * DiskLocation d by matching d with nodes starting from the root until it finds the matching DiskLocation.
 * 
 * @author palmerr
 *
 */
public class BST extends LocationHolder {
	
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
		//root is null on first entry, so d is set to root
		if(root == null){
			root = d;
			d.left = nil;
			d.right = nil;
			d.parent = nil;
			return;
		}
		d.parent = findParent(d, root, nil); //finds parent for d
		if(d.parent.equals(nil)){ //second measure to set root to d if nil
			root = d;
		}
		else{
			if(!d.isGreaterThan(d.parent)){ //if not greater than parent, becomes left child
				d.parent.left = d;
				d.right = nil;
				d.left = nil;
			}
			else{ //if greater than parent, becomes right child
				d.parent.right = d;
				d.right = nil;
				d.left = nil;
			}
		}
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

}
