/**
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.fireflysemantics.math.linear.matrix.interfaces;

import com.fireflysemantics.math.exception.MathException;

/**
 * Matrix traversal interface.
 */
public interface MatrixTraversal {

	/**
	 * Visit (and possibly change) all matrix entries in row order.
	 * <p>
	 * Row order starts at upper left and iterating through all elements of aRea
	 * row from left to right before going to the leftmost element of the next
	 * row.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInRowOrder(MatrixChangingVisitor visitor);

	/**
	 * Visit (but don't change) all matrix entries in row order.
	 * <p>
	 * Row order starts at upper left and iterating through all elements of a
	 * row from left to right before going to the leftmost element of the next
	 * row.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInRowOrder(MatrixPreservingVisitor visitor);

	/**
	 * Visit (and possibly change) some matrix entries in row order.
	 * <p>
	 * Row order starts at upper left and iterating through all elements of a
	 * row from left to right before going to the leftmost element of the next
	 * row.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInRowOrder(MatrixChangingVisitor visitor, int startRow, int endRow, int startColumn,
			int endColumn) throws MathException;

	/**
	 * Visit (but don't change) some matrix entries in row order.
	 * <p>
	 * Row order starts at upper left and iterating through all elements of a
	 * row from left to right before going to the leftmost element of the next
	 * row.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInRowOrder(MatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn,
			int endColumn) throws MathException;

	/**
	 * Visit (and possibly change) all matrix entries in column order.
	 * <p>
	 * Column order starts at upper left and iterating through all elements of a
	 * column from top to bottom before going to the topmost element of the next
	 * column.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInColumnOrder(MatrixChangingVisitor visitor);

	/**
	 * Visit (but don't change) all matrix entries in column order.
	 * <p>
	 * Column order starts at upper left and iterating through all elements of a
	 * column from top to bottom before going to the topmost element of the next
	 * column.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInColumnOrder(MatrixPreservingVisitor visitor);

	/**
	 * Visit (and possibly change) some matrix entries in column order.
	 * <p>
	 * Column order starts at upper left and iterating through all elements of a
	 * column from top to bottom before going to the topmost element of the next
	 * column.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInColumnOrder(MatrixChangingVisitor visitor, int startRow, int endRow, int startColumn,
			int endColumn) throws MathException;

	/**
	 * Visit (but don't change) some matrix entries in column order.
	 * <p>
	 * Column order starts at upper left and iterating through all elements of a
	 * column from top to bottom before going to the topmost element of the next
	 * column.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInColumnOrder(MatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn,
			int endColumn) throws MathException;

	/**
	 * Visit (and possibly change) all matrix entries using the fastest possible
	 * order.
	 * <p>
	 * The fastest walking order depends on the exact matrix class. It may be
	 * different from traditional row or column orders.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInOptimizedOrder(MatrixChangingVisitor visitor);

	/**
	 * Visit (but don't change) all matrix entries using the fastest possible
	 * order.
	 * <p>
	 * The fastest walking order depends on the exact matrix class. It may be
	 * different from traditional row or column orders.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInOptimizedOrder(MatrixPreservingVisitor visitor);

	/**
	 * Visit (and possibly change) some matrix entries using the fastest
	 * possible order.
	 * <p>
	 * The fastest walking order depends on the exact matrix class. It may be
	 * different from traditional row or column orders.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index (inclusive)
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor, int, int, int,
	 *      int)
	 * @return the value returned by {@link MatrixChangingVisitor#end()} at
	 *         the end of the walk
	 */
	double walkInOptimizedOrder(MatrixChangingVisitor visitor, int startRow, int endRow, int startColumn,
			int endColumn) throws MathException;

	/**
	 * Visit (but don't change) some matrix entries using the fastest possible
	 * order.
	 * <p>
	 * The fastest walking order depends on the exact matrix class. It may be
	 * different from traditional row or column orders.
	 * </p>
	 * 
	 * @param visitor
	 *            visitor used to process all matrix entries
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index (inclusive)
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @see #walkInRowOrder(MatrixChangingVisitor)
	 * @see #walkInRowOrder(MatrixPreservingVisitor)
	 * @see #walkInRowOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInRowOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixChangingVisitor)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor)
	 * @see #walkInColumnOrder(MatrixChangingVisitor, int, int, int, int)
	 * @see #walkInColumnOrder(MatrixPreservingVisitor, int, int, int, int)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor)
	 * @see #walkInOptimizedOrder(MatrixPreservingVisitor)
	 * @see #walkInOptimizedOrder(MatrixChangingVisitor, int, int, int, int)
	 * @return the value returned by {@link MatrixPreservingVisitor#end()}
	 *         at the end of the walk
	 */
	double walkInOptimizedOrder(MatrixPreservingVisitor visitor, int startRow, int endRow,
			int startColumn, int endColumn) throws MathException;

}
