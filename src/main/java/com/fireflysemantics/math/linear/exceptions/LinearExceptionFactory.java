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
package com.fireflysemantics.math.linear.exceptions;

import static com.fireflysemantics.math.exception.ExceptionFactory.checkNoData;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNullArgument;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNumberTooSmall;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkOutOfRange;
import static com.fireflysemantics.math.exception.ExceptionKeys.VALUE;
import static com.fireflysemantics.math.exception.ExceptionTypes.MATRIX_DIMENSION_MISMATCH__ADDITION;
import static com.fireflysemantics.math.exception.ExceptionTypes.MATRIX_DIMENSION_MISMATCH__MULTIPLICATION;
import static com.fireflysemantics.math.exception.ExceptionTypes.MATRIX_DIMENSION_MISMATCH__OPERATE;
import static com.fireflysemantics.math.exception.ExceptionTypes.MATRIX_DIMENSION_MISMATCH__SUBTRACTION;
import static com.fireflysemantics.math.exception.ExceptionTypes.MATRIX_NON_SQUARE;
import static com.fireflysemantics.math.exception.ExceptionTypes.NO_DATA;
import static com.fireflysemantics.math.exception.ExceptionTypes.NULL_ARGUMENT;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.COLUMN_DIMENSION;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.MATRIX_LEFT;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.MATRIX_RIGHT;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.ROW_DIMENSION;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.ROW_INDEX;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.SELECTED_COLUMN;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.SELECTED_ROW;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.VECTOR;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.VECTOR1;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.VECTOR2;

import com.fireflysemantics.math.exception.ExceptionTypes;
import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;
import com.fireflysemantics.math.linear.vector.interfaces.Vector;

public class LinearExceptionFactory {

	/**
	 * Check if matrices are addition compatible.
	 *
	 * @param left
	 *            Left hand side matrix.
	 * @param right
	 *            Right hand side matrix.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__ADDITION} if
	 *             matrices are not multiplication compatible.
	 */
	public static void checkAdditionCompatible(Matrix left, Matrix right) {
		if (matrixDimensionsNotEqual(left, right)) {
			throw new MathException(MATRIX_DIMENSION_MISMATCH__ADDITION).put(MATRIX_LEFT, left)
					.put(MATRIX_RIGHT, right);
		}
	}

	/**
	 * Check if matrices are subtraction compatible.
	 *
	 * @param left
	 *            Left hand side matrix.
	 * @param right
	 *            Right hand side matrix.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__SUBTRACTION} if
	 *             matrices are not multiplication compatible.
	 */
	public static void checkSubtractionCompatible(Matrix left, Matrix right) {
		if (matrixDimensionsNotEqual(left, right)) {
			throw new MathException(MATRIX_DIMENSION_MISMATCH__SUBTRACTION).put(MATRIX_LEFT, left)
					.put(MATRIX_RIGHT, right);
		}
	}

	/**
	 * Check if matrices are multiplication compatible
	 *
	 * @param left
	 *            Left hand side matrix.
	 * @param right
	 *            Right hand side matrix.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__MULTIPLICATION} if
	 *             matrices are not multiplication compatible.
	 */
	public static void checkMultiplicationCompatible(final Matrix left, final Matrix right)
			throws MathException {

		if (left.getColumnDimension() != right.getRowDimension()) {
			throw new MathException(MATRIX_DIMENSION_MISMATCH__MULTIPLICATION).put(MATRIX_LEFT, left)
					.put(MATRIX_RIGHT, right);
		}
	}

	/**
	 * Check if matrix is operate compatible (Multiplication) with the vector.
	 *
	 * @param m
	 *            Left hand side matrix.
	 * @param v
	 *            Right hand side vector.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             matrices are not multiplication compatible.
	 */
	public static void checkMultiplicationCompatible(final Matrix m, final Vector v) throws MathException {
		if (m.getColumnDimension() != v.getDimension()) {
			throw new MathException(MATRIX_DIMENSION_MISMATCH__OPERATE).put(MATRIX_LEFT, m).put(VECTOR, v);
		}
	}

	/**
	 * Check if matrix is operate compatible (Multiplication) with the vector.
	 *
	 * @param m
	 *            Left hand side matrix.
	 * @param v
	 *            Right hand side vector.
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             matrices are not multiplication compatible.
	 */
	public static void checkMultiplicationCompatible(final Vector v, final Matrix m) throws MathException {
		if (v.getDimension() != m.getRowDimension()) {
			throw new MathException(MATRIX_DIMENSION_MISMATCH__OPERATE).put(VECTOR, v).put(MATRIX_LEFT, m);
		}
	}

	/**
	 * Check if the matrix is square.
	 *
	 * @param m
	 *            The matrix.
	 * @throws MathException
	 *             Of type {@code MATRIX_NON_SQUARE} if the matrix is not
	 *             square.
	 */
	public static void checkNonSquareMatrix(Matrix m) {
		if (m.getRowDimension() != m.getColumnDimension()) {
			throw new MathException(MATRIX_NON_SQUARE).put(ROW_DIMENSION, m.getRowDimension())
					.put(COLUMN_DIMENSION, m.getColumnDimension());
		}
	}

	/**
	 * Check if submatrix ranges indices are valid. Rows and columns are
	 * indicated counting from 0 to {@code n - 1}.
	 *
	 * @param m
	 *            Matrix.
	 * @param startRow
	 *            Initial row index.
	 * @param endRow
	 *            Final row index.
	 * @param startColumn
	 *            Initial column index.
	 * @param endColumn
	 *            Final column index.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if the indices are invalid.
	 * @throws MathException
	 *             Of type {@code NUMBER_TOO_SMALL} if {@code endRow < startRow}
	 *             or {@code endColumn < startColumn}.
	 */
	public static void checkSubMatrixIndex(final Matrix m, final int startRow, final int endRow,
			final int startColumn, final int endColumn) throws MathException {

		checkRowIndex(m, startRow);
		checkRowIndex(m, endRow);
		checkNumberTooSmall(VALUE, endRow, startRow);

		checkColumnIndex(m, startColumn);
		checkColumnIndex(m, endColumn);
		checkNumberTooSmall(VALUE, endColumn, startColumn);
	}

	/**
	 * Check if submatrix range indices are valid. Rows and columns are
	 * indicated counting from 0 to n-1.
	 *
	 * @param m
	 *            Matrix.
	 * @param selectedRows
	 *            Array of row indices.
	 * @param selectedColumns
	 *            Array of column indices.
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if {@code selectedRows} or
	 *             {@code selectedColumns} are {@code null}.
	 * @throws MathException
	 *             Of type {@code NO_DATA} if the row or column selections are
	 *             empty (zero length).
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if row or column selections are
	 *             not valid.
	 */
	public static void checkSubMatrixIndex(final Matrix m, final int[] selectedRows,
			final int[] selectedColumns) throws MathException {
		checkNullArgument(SELECTED_ROW, selectedRows);
		checkNullArgument(SELECTED_COLUMN, selectedColumns);
		checkNoData(SELECTED_ROW, selectedRows.length);
		checkNoData(SELECTED_COLUMN, selectedColumns.length);

		for (final int row : selectedRows) {
			checkRowIndex(m, row);
		}
		for (final int column : selectedColumns) {
			checkColumnIndex(m, column);
		}
	}

	/**
	 * Check null double array.
	 *
	 * @param array
	 *            The double[][] array to null check.
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if {@code row} is not a valid
	 *             index.
	 */
	public static void checkNullDoubleArray(final double[][] array) throws MathException {
		if (array == null) {
			throw new MathException(NULL_ARGUMENT);
		}
	}

	/**
	 * Check if a row index is valid.
	 *
	 * @param m
	 *            Matrix.
	 * @param row
	 *            Row index to check.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if {@code row} is not a valid
	 *             index.
	 */
	public static void checkRowIndex(final Matrix m, final int row) throws MathException {
		if (row < 0
				|| row >= m.getRowDimension()) {
			checkOutOfRange(ROW_INDEX, row, 0, m.getRowDimension()
					- 1);
		}
	}

	/**
	 * Check if a column index is valid.
	 *
	 * @param m
	 *            Matrix.
	 * @param column
	 *            Column index to check.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if {@code column} is not a valid
	 *             index.
	 * 
	 */
	public static void checkColumnIndex(final Matrix m, final int column) throws MathException {
		checkOutOfRange(LinearExceptionKeys.COLUMN_INDEX, column, 0, m.getColumnDimension()
				- 1);
	}

	/**
	 * Check whether the row or column indexes are greater than zero.
	 * 
	 * @param row
	 *            row index to check.
	 * @param column
	 *            Column index to check.
	 * @throws MathException
	 *             Of type {@code OUT_OF_RANGE} if {@code column} is not a valid
	 *             index.
	 * 
	 */
	public static void checkSetSubMatrixIllegalState(final int row, final int column) throws MathException {
		if (row > 0
				|| column > 0) {
			throw new MathException(ExceptionTypes.MISE);
		}
	}

	/**
	 * Check whether two matrices have equal dimensions.
	 * 
	 * @param left
	 *            Left matrix.
	 * @param right
	 *            Right matrix.
	 * @return true if the matrix dimensions are not equal, false otherwise.
	 */
	private static boolean matrixDimensionsNotEqual(Matrix left, Matrix right) {
		if ((left.getRowDimension() != right.getRowDimension())
				|| (left.getColumnDimension() != right.getColumnDimension())) {
			return true;
		}
		return false;
	}

	/**
	 * Construct and throw a NO_DATA exception if {@code arg.length} is zero.
	 * 
	 * @param key
	 *            The context key that labels the {@code arg} parameter.
	 * @param arrayLength
	 *            The parameter to perform a null check against.
	 */
	public static void checkNoVectorData(int length) {
		if (length == 0) {
			throw new MathException(NO_DATA);
		}
	}

	/**
	 * Construct and throw a DIMENSION_MISMATCH exception if
	 * {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 * 
	 * @param v1
	 *            The first vector.
	 * @param v1
	 *            The second vector.
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static void checkDimensionMismatch(Vector v1, Vector v2) {
		if (v1.getDimension() != v2.getDimension()) {
			throw new MathException(ExceptionTypes.DIMENSION_MISMATCH).put(VECTOR1, v1).put(VECTOR2, v2);
		}
	}
}
