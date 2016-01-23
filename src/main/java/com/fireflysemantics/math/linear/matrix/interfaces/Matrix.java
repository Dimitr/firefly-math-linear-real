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
 * Matrix interface.
 */
public interface Matrix {

	/**
	 * Returns matrix entries as a two-dimensional array.
	 *
	 * @return 2-dimensional array of entries.
	 */
	double[][] toArray();

	/**
	 * Is this a square matrix?
	 * 
	 * @return true if the matrix is square (rowDimension == columnDimension)
	 */
	boolean isSquare();

	/**
	 * Returns the number of rows in the matrix.
	 *
	 * @return rowDimension
	 */
	int getRowDimension();

	/**
	 * Returns the number of columns in the matrix.
	 *
	 * @return columnDimension
	 */
	int getColumnDimension();

	/**
	 * Get the entry in the specified row and column. Row and column indices
	 * start at 0.
	 *
	 * @param row
	 *            Row index of entry to be fetched.
	 * @param column
	 *            Column index of entry to be fetched.
	 * @return the matrix entry at {@code (row, column)}.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the row or column index is
	 *             not valid.
	 */
	double getEntry(int row, int column) throws MathException;

	/**
	 * Set the entry in the specified row and column. Row and column indices
	 * start at 0.
	 *
	 * @param row
	 *            Row index of entry to be set.
	 * @param column
	 *            Column index of entry to be set.
	 * @param value
	 *            the new value of the entry.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the row or column index is
	 *             not valid
	 */
	void setEntry(int row, int column, double value) throws MathException;

	/**
	 * Returns a deep copy.
	 *
	 * @return matrix copy
	 */
	Matrix copy();

}
