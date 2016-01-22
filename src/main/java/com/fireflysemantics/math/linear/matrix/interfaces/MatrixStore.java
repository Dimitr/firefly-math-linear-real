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
 * The purpose of this interface is to be able to retrieve subsections of the
 * Matrix.
 */
public interface MatrixStore {

	/**
	 * Gets a submatrix. Rows and columns are indicated counting from 0 to n-1.
	 *
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index (inclusive)
	 * @return The subMatrix containing the data of the specified rows and
	 *         columns.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 */
	Matrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws MathException;

	/**
	 * Gets a submatrix. Rows and columns are indicated counting from 0 to n-1.
	 *
	 * @param selectedRows
	 *            Array of row indices.
	 * @param selectedColumns
	 *            Array of column indices.
	 * @return The subMatrix containing the data in the specified rows and
	 *         columns
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if the row or column selections
	 *             are {@code null}
	 * @throws MathException
	 *             Of type {@code NO_DATA} if the row or column selections are
	 *             empty (zero length).
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 */
	Matrix getSubMatrix(int[] selectedRows, int[] selectedColumns) throws MathException;

	/**
	 * Copy a submatrix. Rows and columns are indicated counting from 0 to n-1.
	 *
	 * @param startRow
	 *            Initial row index
	 * @param endRow
	 *            Final row index (inclusive)
	 * @param startColumn
	 *            Initial column index
	 * @param endColumn
	 *            Final column index (inclusive)
	 * @param destination
	 *            The arrays where the submatrix data should be copied (if
	 *            larger than rows/columns counts, only the upper-left part will
	 *            be used)
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH} if the destination
	 *             array is too small.
	 */
	void Matrix(int startRow, int endRow, int startColumn, int endColumn, double[][] destination)
			throws MathException;

	/**
	 * Copy a submatrix. Rows and columns are indicated counting from 0 to n-1.
	 *
	 * @param selectedRows
	 *            Array of row indices.
	 * @param selectedColumns
	 *            Array of column indices.
	 * @param destination
	 *            The arrays where the submatrix data should be copied (if
	 *            larger than rows/columns counts, only the upper-left part will
	 *            be used)
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if the row or column selections
	 *             are {@code null}
	 * @throws MathException
	 *             Of type {@code NO_DATA} if the row or column selections are
	 *             empty (zero length).
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are not valid.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH} if the destination
	 *             array is too small.
	 */
	void copySubMatrix(int[] selectedRows, int[] selectedColumns, double[][] destination)
			throws MathException;

	/**
	 * Replace the submatrix starting at {@code row, column} using data in the
	 * input {@code subMatrix} array. Indexes are 0-based.
	 * <p>
	 * Example:<br>
	 * Starting with
	 * 
	 * <pre>
	 * 1  2  3  4
	 * 5  6  7  8
	 * 9  0  1  2
	 * </pre>
	 * 
	 * and <code>subMatrix = {{3, 4} {5,6}}</code>, invoking
	 * {@code setSubMatrix(subMatrix,1,1))} will result in
	 * 
	 * <pre>
	 * 1  2  3  4
	 * 5  3  4  8
	 * 9  5  6  2
	 * </pre>
	 * </p>
	 *
	 * @param subMatrix
	 *            array containing the submatrix replacement data
	 * @param row
	 *            row coordinate of the top, left element to be replaced
	 * @param column
	 *            column coordinate of the top, left element to be replaced
	 * @throws MathException
	 *             Of type {@code NO_DATA} if {@code subMatrix} is empty.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if {@code subMatrix} does not
	 *             fit into this matrix from element in {@code (row, column)}.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH} if
	 *             {@code subMatrix} is not rectangular (not all rows have the
	 *             same length) or empty.
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if {@code subMatrix} is
	 *             {@code null}.
	 * @throws MathException
	 *             Of type {@code ILLEGAL_STATE} if {@code data} is {@code null}
	 *             but either row or column are > 0.
	 */
	void setSubMatrix(double[][] subMatrix, int row, int column) throws MathException;

	/**
	 * Get the entries at the given row index as a double[] array instance. Row
	 * indices start at 0.
	 *
	 * @param row
	 *            Index of row to be fetched.
	 * @return the double[] array containing the row
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the specified row index is
	 *             invalid.
	 */
	double[] getRow(int row) throws MathException;

	/**
	 * Sets the specified {@code row} of {@code this} matrix to the entries of
	 * the specified row {@code matrix}. Row indices start at 0.
	 *
	 * @param row
	 *            Index of the row to be set.
	 * @param array
	 *            Array containing the row data to be copied (must have the same
	 *            number of columns as the instance).
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the specified row index is
	 *             invalid.
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if the column dimensions
	 *             of {@code this} and {@code vector} do not match.
	 */
	void setRow(int row, double[] array) throws MathException;

	/**
	 * Get the entries at the given column index as a column matrix. Column
	 * indices start at 0.
	 *
	 * @param column
	 *            Index of the column to be fetched.
	 * @return column double[] array.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the specified column index is
	 *             invalid.
	 */
	double[] getColumn(int column) throws MathException;

	/**
	 * Sets the specified {@code column} of {@code this} matrix to the entries
	 * of contain in the {@code array}. Column indices start at 0.
	 *
	 * @param column
	 *            Index of column to be set.
	 * @param array
	 *            Column array to be copied (must have the same number of rows
	 *            as the instance).
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the specified column index is
	 *             invalid.
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if the column array does
	 *             not have the same number of rows as the instance.
	 */
	void setColumn(int column, double[] array) throws MathException;
}
