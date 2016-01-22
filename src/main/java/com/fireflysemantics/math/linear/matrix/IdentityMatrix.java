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
package com.fireflysemantics.math.linear.matrix;

import static com.fireflysemantics.math.exception.ExceptionFactory.checkIndexOutOfRange;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNotStrictlyPositive;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.SQUARE_DIMENSION;
import static java.util.stream.IntStream.range;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;

import lombok.Getter;

public class IdentityMatrix implements Matrix {

	/** {@inheritDoc} */
	public double[][] toArray() {
		return range(0, rowDimension)
				.mapToObj(r -> range(0, columnDimension).mapToDouble(c -> r == c ? 1 : 0).toArray())
				.toArray(double[][]::new);
	};

	/**
	 * {@link interfaces.Matrix#getRowDimension}
	 * 
	 * @return the row dimension
	 */
	@Getter
	private int rowDimension;

	/**
	 * {@link interfaces.Matrix#getColumnDimension}
	 * 
	 * @return the column dimension
	 */
	@Getter
	private int columnDimension;

	/**
	 * Create a new ArrayMatrix with the supplied row and column dimensions. All
	 * matrix entries will be initialized to their java default of 0d.
	 *
	 * @param dimension
	 *            the number of rows and columns in the new matrix
	 * @throws MathException
	 *             Of type {code NOT_STRICTLY_POSITIVE} if the {@code dimension}
	 *             is not positive.
	 */
	public IdentityMatrix(final int dimension) throws MathException {
		checkNotStrictlyPositive(SQUARE_DIMENSION, dimension);
		this.rowDimension = dimension;
		this.columnDimension = dimension;
	}

	@Override
	public boolean isSquare() {
		return true;
	}

	@Override
	public double getEntry(int row, int column) throws MathException {
		checkIndexOutOfRange(row, 0, rowDimension);
		checkIndexOutOfRange(column, 0, columnDimension);
		if (row == column) {
			return 1d;
		}
		return 0d;
	}

	@Override
	public void setEntry(int row, int column, double value) throws MathException {
		// Do nothing. Diagnoal entries are one and all other are zero.
	}

	@Override
	public Matrix copy() {
		return this;
	}
}
