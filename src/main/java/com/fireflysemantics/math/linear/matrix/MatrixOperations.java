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

import static com.fireflysemantics.math.exception.ExceptionFactory.checkNotPositive;
import static com.fireflysemantics.math.exception.ExceptionKeys.VALUE;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkAdditionCompatible;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkMultiplicationCompatible;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkNonSquareMatrix;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkSubtractionCompatible;
import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;
import com.fireflysemantics.math.linear.vector.ArrayVector;

public class MatrixOperations {

	/** Stream reference for operations */
	private static Stream<double[]> stream;

	/**
	 * Returns the {@link BiFunction} that performs the scalar addtion of
	 * {@code scalar} to the {@link SimpleMatrix} {@code matrix}.
	 * 
	 * @return the {@link BiFunction} that performs the operation.
	 */
	public static BiFunction<SimpleMatrix, Double, SimpleMatrix> addScalar() {
		return (matrix, scalar) -> {
			double[][] data = matrix.toArray();
			int rows = matrix.getRowDimension();
			int cols = matrix.getColumnDimension();

			double[][] result = new double[rows][cols];

			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < rows; c++) {
					result[r][c] = data[r][c]
							+ scalar;
				}
			}
			return new SimpleMatrix(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar subtraction of
	 * {@code scalar} from the {@link SimpleMatrix} {@code matrix}.
	 *
	 * @return the {@link BiFunction} that performs the operation.
	 */
	public static BiFunction<SimpleMatrix, Double, SimpleMatrix> subtractScalar() {
		return (matrix, scalar) -> {
			double[][] data = matrix.toArray();
			int rows = matrix.getRowDimension();
			int cols = matrix.getColumnDimension();

			double[][] result = new double[rows][cols];

			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < rows; c++) {
					result[r][c] = data[r][c]
							- scalar;
				}
			}
			return new SimpleMatrix(result);
		};
	};

	/**
	 * Returns the {@link BiFunction} that performs the scalar multiplication of
	 * {@code scalar} times the {@link SimpleMatrix} {@code matrix}.
	 *
	 * @return the {@link BiFunction} that performs the operation.
	 */
	public static BiFunction<SimpleMatrix, Double, SimpleMatrix> multiplyScalar() {
		return (matrix, scalar) -> {
			double[][] data = matrix.toArray();
			int rows = matrix.getRowDimension();
			int cols = matrix.getColumnDimension();

			double[][] result = new double[rows][cols];

			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < rows; c++) {
					result[r][c] = data[r][c]
							* scalar;
				}
			}
			return new SimpleMatrix(result);
		};
	}

	/**
	 * Returns the {@link BinaryOperator} that adds the {@link SimpleMatrix}
	 * {@code m2} to the {@link SimpleMatrix} {@code m1.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__ADDITION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the operation.
	 */
	public static BinaryOperator<SimpleMatrix> add() {
		return (m1, m2) -> {
			checkAdditionCompatible(m1, m2);
			final int rows = m1.getRowDimension();
			final int cols = m1.getColumnDimension();
			double[][] a1 = m1.toArray();
			double[][] a2 = m2.toArray();

			final double[][] result = new double[rows][cols];
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					result[r][c] = a1[r][c]
							+ a2[r][c];
				}
			}
			return new SimpleMatrix(result);
		};
	}

	/**
	 * Returns the {@link BinaryOperator} that subtracts the
	 * {@link SimpleMatrix} {@code m2} from the {@link SimpleMatrix} {@code m1}.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__SUBTRACTION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the operation.
	 */
	public static BinaryOperator<SimpleMatrix> subtract() {
		return (m1, m2) -> {
			checkSubtractionCompatible(m1, m2);
			final int rows = m1.getRowDimension();
			final int cols = m1.getColumnDimension();
			double[][] a1 = m1.toArray();
			double[][] a2 = m2.toArray();

			final double[][] result = new double[rows][cols];
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					result[r][c] = a1[r][c]
							- a2[r][c];
				}
			}
			return new SimpleMatrix(result);
		};
	}

	/**
	 * Returns a {@link BinaryOperator} that multiplies {@link SimpleMatrix}
	 * {@code m1} times {@link SimpleMatrix} {@code m2} (m1 X m2).
	 * 
	 * Example {@code multiply(true).apply(m1, m2);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__MULTIPLICATION} if
	 *             {@code m} is not the same size as {@code this}.
	 * 
	 * @return the {@link BinaryOperator} that performs the operation.
	 */
	public static BinaryOperator<SimpleMatrix> multiply() {

		return (m1, m2) -> {
			checkMultiplicationCompatible(m1, m2);

			double[][] a1 = m1.toArray();
			double[][] a2 = m2.toArray();

			final int nRows = m1.getRowDimension();
			final int nCols = m2.getColumnDimension();
			final int nSum = m1.getColumnDimension();

			final double[][] result = new double[nRows][nCols];
			// Will hold a column of "a2".
			final double[] mCol = new double[nSum];

			// Multiply.
			for (int col = 0; col < nCols; col++) {
				// Copy all elements of column "col" of "m" so that
				// will be in contiguous memory.
				for (int mRow = 0; mRow < nSum; mRow++) {
					mCol[mRow] = a2[mRow][col];
				}

				for (int row = 0; row < nRows; row++) {
					final double[] dataRow = a1[row];
					double sum = 0;
					for (int i = 0; i < nSum; i++) {
						sum += dataRow[i]
								* mCol[i];
					}
					result[row][col] = sum;
				}
			}
			return new SimpleMatrix(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies the the
	 * {@link SimpleMatrix} {@code m} times the {@link ArrayVector} {@code v}.
	 * 
	 * The operation views the {@code v} vector as a N X 1 Matrix.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             {@code m.getColumnDimension != v.getDimension()}.
	 * 
	 * @return the {@link BiFunction} that performs operation.
	 */
	public static BiFunction<SimpleMatrix, ArrayVector, ArrayVector> operate() {
		return (m, v) -> {
			checkMultiplicationCompatible(m, v);

			final int rows = m.getRowDimension();
			final int cols = m.getColumnDimension();

			double[][] matrix = m.toArray();
			double[] vector = v.toArray();

			final double[] result = new double[rows];
			for (int r = 0; r < rows; r++) {
				final double[] row = m.toArray()[r];
				double sum = 0;
				for (int c = 0; c < cols; c++) {
					sum += row[c]
							* vector[c];
				}
				result[r] = sum;
			}
			return new ArrayVector(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies the {@link ArrayVector}
	 * {@code v} times the {@link SimpleMatrix} {@code m}.
	 * 
	 * The operation views the {@code v} vector as a N x 1 Matrix.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             {@code m} is not the same size as {@code this}.
	 * 
	 * @return the {@link BiFunction} that performs operation.
	 */
	public static BiFunction<ArrayVector, SimpleMatrix, ArrayVector> preOperate() {
		return (v, m) -> {
			checkMultiplicationCompatible(v, m);
			double[][] matrix = m.toArray();
			double[] vector = v.toArray();

			final int rows = m.getRowDimension();
			final int cols = m.getColumnDimension();

			final double[] result = new double[cols];
			for (int c = 0; c < cols; ++c) {
				double sum = 0;
				for (int r = 0; r < rows; ++r) {
					sum += matrix[r][c]
							* vector[r];
				}
				result[c] = sum;
			}
			return new ArrayVector(result);
		};
	}

	/**
	 * Returns a {@link UnaryOperator} that transposes the matrix.
	 * 
	 * Example {@code transpose().apply(m);}
	 * 
	 * @return the {@link UnaryOperator} that performs the tranpose operation.
	 *
	 */
	public static UnaryOperator<SimpleMatrix> transpose() {
		return transpose(false);
	}

	/**
	 * Returns a {@link UnaryOperator} that transposes the matrix.
	 * 
	 * Example {@code transpose(true).apply(m);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link UnaryOperator} that performs the tranpose operation.
	 */
	public static UnaryOperator<SimpleMatrix> transpose(boolean parallel) {
		return m -> {
			double[][] a = m.toArray();
			IntStream stream = range(0, m.getColumnDimension());
			stream = parallel ? stream.parallel() : stream;

			double[][] transpose =
					stream.mapToObj(c -> range(0, a.length).mapToDouble(r -> a[r][c]).toArray())
							.toArray(double[][]::new);
			return new SimpleMatrix(transpose);
		};
	}

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/MaximumAbsoluteRowSumNorm.html">
	 * maximum absolute row sum norm</a> of the matrix.
	 * 
	 * Example {@code norm().apply(m);}
	 * 
	 * @return the {@link Function} that performs the tranpose operation.
	 */
	public static Function<SimpleMatrix, Double> norm() {
		return m -> {
			double[][] a = m.toArray();
			int cols = m.getColumnDimension();
			int rows = m.getRowDimension();
			double[] sums = new double[cols];

			for (int c = 0; c < cols; c++) {
				double sum = 0d;
				for (int r = 0; r < rows; r++) {
					sum += Math.abs(a[r][c]);
				}
				sums[c] = sum;
			}
			return Arrays.stream(sums).max().getAsDouble();
		};
	}

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/FrobeniusNorm.html"> Frobenius
	 * norm</a> of the matrix.
	 * 
	 * Example {@code frobeniusNorm().apply(m);}
	 * 
	 * @return the {@link Function} that performs the operation.
	 */
	public static Function<SimpleMatrix, Double> frobeniusNorm(boolean parallel) {
		return m -> {
			double[][] a = m.toArray();
			int cols = m.getColumnDimension();
			int rows = m.getRowDimension();
			IntStream stream = range(0, cols);
			stream = parallel ? stream.parallel() : stream;

			return Math.sqrt(stream.mapToDouble(c -> range(0, rows).mapToDouble(r -> a[r][c]
					* a[r][c]).sum()).sum());
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies {@code m} with itself
	 * {@code p} times}.
	 * 
	 * Example {@code power().apply(m, 2);}
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code NOT_POSITIVE_EXCEPTION} if {@code p < 0}
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_NON_SQUARE} if {@code m} is not square.
	 * 
	 * @return the {@link BiFunction} that performs the {@code m^p} power
	 *         calculation.
	 */
	public static BiFunction<SimpleMatrix, Integer, Matrix> power(boolean parallel) {
		return (m, p) -> {
			checkNotPositive(VALUE, p);
			checkNonSquareMatrix(m);

			if (p == 0) {
				return new IdentityMatrix(m.getRowDimension());
			}
			if (p == 1) {
				return m.copy();
			}

			final int power = p
					- 1;

			/*
			 * Only log_2(p) operations is used by doing as follows: 5^214 =
			 * 5^128 * 5^64 * 5^16 * 5^4 * 5^2
			 *
			 * In general, the same approach is used for A^p.
			 */

			final char[] binaryRepresentation = Integer.toBinaryString(power).toCharArray();
			final ArrayList<Integer> nonZeroPositions = new ArrayList<Integer>();
			int maxI = -1;

			for (int i = 0; i < binaryRepresentation.length; ++i) {
				if (binaryRepresentation[i] == '1') {
					final int pos = binaryRepresentation.length
							- i
							- 1;
					nonZeroPositions.add(pos);

					// The positions are taken in turn, so maxI is only changed
					// once
					if (maxI == -1) {
						maxI = pos;
					}
				}
			}

			SimpleMatrix[] results = new SimpleMatrix[maxI
					+ 1];
			results[0] = (SimpleMatrix) m.copy();

			for (int i = 1; i <= maxI; ++i) {
				results[i] = multiply().apply(results[i
						- 1],
						results[i
								- 1]);
			}

			SimpleMatrix result = (SimpleMatrix) m.copy();

			for (Integer i : nonZeroPositions) {
				result = multiply().apply(result, results[i]);
			}
			return result;
		};
	}

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/MatrixTrace.html"> trace of the NxN
	 * square matrix.
	 * 
	 * Example {@code trace(true).apply(m);}
	 * 
	 * @param parallel
	 *            Whether to calculate the trace concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_NON_SQUARE} if {@code m} is not square.
	 * 
	 * @return the {@link Function} that performs the tranpose operation.
	 */
	public static Function<SimpleMatrix, Double> trace() {
		return (m) -> {
			checkNonSquareMatrix(m);
			double sum = 0d;
			double[][] a = m.toArray();

			for (int i = 0; i < m.getColumnDimension(); i++) {
				sum += a[i][i];
			}
			return sum;
		};
	}
}