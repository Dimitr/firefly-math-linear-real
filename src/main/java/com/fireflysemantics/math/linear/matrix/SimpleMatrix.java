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

import static com.fireflysemantics.math.exception.ExceptionFactory.checkDimensionMismatch;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkIndexOutOfRange;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNoData;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNotStrictlyPositive;
import static com.fireflysemantics.math.exception.ExceptionFactory.checkNullArgument;
import static com.fireflysemantics.math.exception.ExceptionKeys.VALUE;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.COLUMN_DIMENSION;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionKeys.ROW_DIMENSION;
import static java.util.stream.IntStream.range;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.UnaryOperator;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;

import lombok.Getter;

/**
 * The purpose of this class that is to provide checked and unchecked
 * construction and access to a retangular array of primitive double numbers, as
 * well as matrix meta data, such as the length of the row dimension.
 *
 * @see #SimpleMatrix(double[][] data, UnaryOperator<double[][]> process) for
 *      more information on safe construction.
 */
public class SimpleMatrix implements Matrix, Serializable {

	/** Serializable version identifier. */
	private static final long serialVersionUID = 3474194863519362457L;

	/** {@inheritDoc */
	public double[][] toArray() {
		return data;
	};

	/**
	 * Storage.
	 */
	private double[][] data;

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
	 * Create a new SimpleMatrix with the supplied row and column dimensions.
	 * All matrix entries will be initialized to their java default of 0d.
	 *
	 * @param rowDimension
	 *            the number of rows in the new matrix
	 * @param columnDimension
	 *            the number of columns in the new matrix
	 * @throws MathException
	 *             Of type {code NOT_STRICTLY_POSITIVE} if the row or column
	 *             dimension is not positive.
	 */
	public SimpleMatrix(final int rowDimension, final int columnDimension) throws MathException {
		checkNotStrictlyPositive(ROW_DIMENSION, rowDimension);
		checkNotStrictlyPositive(COLUMN_DIMENSION, columnDimension);
		this.rowDimension = rowDimension;
		this.columnDimension = columnDimension;
		this.data = new double[rowDimension][columnDimension];
	}

	/**
	 * Creates a new {@code SimpleMatrix} that references the {@code data}
	 * argument.
	 * <p>
	 * To both copy and check the matrix call @{code new SimpleMatrix(data,
	 * SimpleMatrix.copyAndCheck());}
	 * </p>
	 * <p>
	 * To only check row that row length are all equal call @{code new
	 * SimpleMatrix(data, SimpleMatrix.check());}.
	 * </p>
	 * 
	 * @param data
	 *            Input array.
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if {@code data} is {@code null}
	 *             .
	 * @throws MathException
	 *             Of type {@code NO_DATA} if {@code data} row or column
	 *             dimension is zero.
	 * @see #SimpleMatrix(double[][] data, UnaryOperator<double[][]> process)
	 */
	public SimpleMatrix(final double[][] data) throws MathException {
		checkNullArgument(VALUE, data);
		this.rowDimension = data.length;
		checkNoData(ROW_DIMENSION, this.rowDimension);
		this.columnDimension = data[0].length;
		checkNoData(COLUMN_DIMENSION, this.columnDimension);
		this.data = data;
	}

	/**
	 * Create a new SimpleMatrix using the input array processed by the
	 * {@code Function} argument.
	 *
	 * @param data
	 *            Data for new matrix.
	 * @param operator
	 *            The {@code Function<double[][], double[][]>} to apply to the
	 *            data before the instance reference is set.
	 * @throws MathException
	 *             Of type {@code NO_DATA} if {@code d} row or column dimension
	 *             is zero.
	 * @throws MathException
	 *             Of type {@code NULL_ARGUMENT} if {@code data} is {@code null}
	 *             .
	 * 
	 * @see #SimpleMatrix(double[][])
	 */
	protected SimpleMatrix(final double[][] data, UnaryOperator<double[][]> operator) throws MathException {
		checkNullArgument(VALUE, data);
		this.rowDimension = data.length;
		checkNoData(ROW_DIMENSION, this.rowDimension);
		this.columnDimension = data[0].length;
		checkNoData(COLUMN_DIMENSION, this.columnDimension);
		this.data = operator.apply(data);
	}

	/** {@inheritDoc} */
	@Override
	public Matrix copy() {
		// TODO revert once this is fixed:
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=485593
		// return new
		// SimpleMatrix(Arrays.stream(this.getData()).map(double[]::clone).toArray(double[][]::new));
		double[][] copy =
				Arrays.stream(this.toArray()).map(a -> (double[]) a.clone()).toArray(double[][]::new);
		return new SimpleMatrix(copy);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSquare() {
		return this.getRowDimension() == this.getColumnDimension();
	}

	/** {@inheritDoc} */
	@Override
	public double getEntry(int row, int column) throws MathException {
		checkIndexOutOfRange(row, 0, rowDimension);
		checkIndexOutOfRange(column, 0, columnDimension);
		return data[row][column];
	}

	/** {@inheritDoc} */
	@Override
	public void setEntry(int row, int column, double value) throws MathException {
		checkIndexOutOfRange(row, 0, rowDimension);
		checkIndexOutOfRange(column, 0, columnDimension);
		data[row][column] = value;
	}

	/**
	 * Returns a {@link UnaryOperator} instance that copies the {@code data}
	 * argument and verifies that row lengths have equal length.
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if {@code data} supplied
	 *             to the {@link UnaryOperator} is not rectangular.
	 * @return the {@link UnaryOperator} instance.
	 */
	public static UnaryOperator<double[][]> copyAndCheck() {
		return (data) -> {
			int columnDimension = data[0].length;
			range(0, data.length).forEach(r -> {
				checkDimensionMismatch(COLUMN_DIMENSION, data[r].length, columnDimension);
				data[r] = data[r].clone();
			});
			return data;
		};
	}

	/**
	 * Returns a {@link UnaryOperator} instance that checks that all row lengths
	 * are equal.
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if {@code data} supplied
	 *             to the {@link UnaryOperator} is not rectangular.
	 * @return the {@link UnaryOperator} instance.
	 */
	public static UnaryOperator<double[][]> check() {
		return (data) -> {
			int columnDimension = data[0].length;
			range(0, data.length).forEach(r -> {
				checkDimensionMismatch(COLUMN_DIMENSION, data[r].length, columnDimension);
			});
			return data;
		};
	}
}
