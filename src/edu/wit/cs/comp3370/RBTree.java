
package edu.wit.cs.comp3370;

/* Implements methods to use a red-black tree 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 5
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiskLocation next(DiskLocation d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiskLocation prev(DiskLocation d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(DiskLocation d) {
		// TODO Auto-generated method stub

	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

}
