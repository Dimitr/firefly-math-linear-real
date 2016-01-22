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
import com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory;
import com.fireflysemantics.math.linear.matrix.interfaces.Matrix;
import com.fireflysemantics.math.linear.vector.ArrayVector;

public class MatrixOperations {

	/** Stream reference for operations */
	private static Stream<double[]> stream;

	/**
	 * Returns the {@link BiFunction} that performs the scalar addtion of
	 * {@code d} to the {@link ArrayMatrix} {@code m}.
	 *
	 * @return the {@link BiFunction} that performs the scalar addition.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> addScalar() {
		return addScalar(false);
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar addtion of
	 * {@code d} to the {@link ArrayMatrix} {@code m}.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link BiFunction} that performs the scalar addition.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> addScalar(boolean parallel) {
		return (m, d) -> {
			stream = Arrays.stream(m.getData());
			stream = parallel ? stream.parallel() : stream;
			final double[][] result = stream.map(r -> range(0, m.getColumnDimension()).mapToDouble(c -> r[c]
					+ d).toArray()).toArray(double[][]::new);
			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar subtraction of
	 * {@code d} from the {@link ArrayMatrix} {@code m}.
	 *
	 * @return the {@link BiFunction} that performs the scalar subtraction.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> subtractScalar() {
		return subtractScalar(false);
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar subtraction of
	 * {@code d} from the {@link ArrayMatrix} {@code m}.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link BiFunction} that performs the scalar subtraction.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> subtractScalar(boolean parallel) {
		return (m, d) -> {
			stream = Arrays.stream(m.getData());
			stream = parallel ? stream.parallel() : stream;
			final double[][] result = stream.map(r -> range(0, m.getColumnDimension()).mapToDouble(c -> r[c]
					- d).toArray()).toArray(double[][]::new);
			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar multiplication of
	 * {@code d} times the {@link ArrayMatrix} {@code m}.
	 *
	 * @return the {@link BiFunction} that performs the scalar multiplication.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> multiplyScalar() {
		return multiplyScalar(false);
	}

	/**
	 * Returns the {@link BiFunction} that performs the scalar multiplication of
	 * {@code d} times the {@link ArrayMatrix} {@code m}.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link BiFunction} that performs the scalar multiplication.
	 */
	public static BiFunction<ArrayMatrix, Double, ArrayMatrix> multiplyScalar(boolean parallel) {
		return (m, d) -> {
			stream = Arrays.stream(m.getData());
			stream = parallel ? stream.parallel() : stream;
			final double[][] result = stream.map(r -> range(0, m.getColumnDimension()).mapToDouble(c -> r[c]
					* d).toArray()).toArray(double[][]::new);
			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns the {@link BinaryOperator} that adds the {@link ArrayMatrix}
	 * {@code m2} to the {@link ArrayMatrix} {@code m1}.
	 *
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__ADDITION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the addition.
	 */
	public static BinaryOperator<ArrayMatrix> add() {
		return add(false);
	}

	/**
	 * Returns the {@link BinaryOperator} that adds the {@link ArrayMatrix}
	 * {@code m2} to the {@link ArrayMatrix} {@code m1.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__ADDITION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the addition.
	 * 
	 */
	public static BinaryOperator<ArrayMatrix> add(boolean parallel) {
		return (m1, m2) -> {
			checkAdditionCompatible(m1, m2);

			double[][] a1 = m1.getData();
			double[][] a2 = m2.getData();

			IntStream stream = range(0, m1.getRowDimension());
			stream = parallel ? stream.parallel() : stream;

			double[][] result =
					stream.mapToObj(r -> range(0, m1.getColumnDimension()).mapToDouble(c -> a1[r][c]
							+ a2[r][c]).toArray()).toArray(double[][]::new);
			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns the {@link BinaryOperator} that subtracts the {@link ArrayMatrix}
	 * {@code m2} from the {@link ArrayMatrix} {@code m1}.
	 *
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__SUBTRACTION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the subtraction.
	 */
	public static BinaryOperator<ArrayMatrix> subtract() {
		return subtract(false);
	}

	/**
	 * Returns the {@link BinaryOperator} that subtracts the {@link ArrayMatrix}
	 * {@code m2} from the {@link ArrayMatrix} {@code m1}.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__SUBTRACTION} if
	 *             {@code m1} is not the same size as {@code m2}.
	 * 
	 * @return the {@link BinaryOperator} that performs the subtraction.
	 * 
	 */
	public static BinaryOperator<ArrayMatrix> subtract(boolean parallel) {
		return (m1, m2) -> {
			checkSubtractionCompatible(m1, m2);

			double[][] a1 = m1.getData();
			double[][] a2 = m2.getData();

			IntStream stream = range(0, m1.getRowDimension());
			stream = parallel ? stream.parallel() : stream;

			double[][] result =
					stream.mapToObj(r -> range(0, m1.getColumnDimension()).mapToDouble(c -> a1[r][c]
							- a2[r][c]).toArray()).toArray(double[][]::new);

			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns a {@link BinaryOperator} that multiplies {@link ArrayMatrix}
	 * {@code m1} times {@link ArrayMatrix} {@code m2}.
	 * 
	 * Example {@code multiply().apply(m1, m2);}
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__MULTIPLICATION} if
	 *             {@code m} is not the same size as {@code this}.
	 * 
	 * @return the {@link BinaryOperator} that performs the multiplication.
	 */
	public static BinaryOperator<ArrayMatrix> multiply() {
		return multiply(false);
	}

	/**
	 * Returns a {@link BinaryOperator} that multiplies {@link ArrayMatrix}
	 * {@code m1} times {@link ArrayMatrix} {@code m2} (m1 X m2).
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
	public static BinaryOperator<ArrayMatrix> multiply(boolean parallel) {

		return (m1, m2) -> {
			checkMultiplicationCompatible(m1, m2);

			double[][] a1 = m1.getData();
			double[][] a2 = m2.getData();

			Stream<double[]> stream = Arrays.stream(a1);
			stream = parallel ? stream.parallel() : stream;

			final double[][] result =
					stream.map(r -> range(0, a2[0].length)
							.mapToDouble(i -> range(0, a2.length).mapToDouble(j -> r[j]
									* a2[j][i]).sum())
							.toArray()).toArray(double[][]::new);

			return new ArrayMatrix(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies the the
	 * {@link ArrayMatrix} {@code m} times the {@link ArrayVector} {@code v}.
	 * 
	 * The operation views the {@code v} vector as a N X 1 Matrix.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             {@code m} is not the same size as {@code this}.
	 * 
	 * @return the {@link BiFunction} that performs operation.
	 */
	public static BiFunction<ArrayMatrix, ArrayVector, ArrayVector> operate(boolean parallel) {
		return (m, v) -> {
			checkMultiplicationCompatible(m, v);

			stream = Arrays.stream(m.getData());
			stream = parallel ? stream.parallel() : stream;
			double[] result =
					stream.mapToDouble(row -> IntStream.range(0, row.length).mapToDouble(col -> row[col]
							* v.getData()[col]).sum()).toArray();
			return new ArrayVector(result);
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies the {@link ArrayVector}
	 * {@code v} times the {@link ArrayMatrix} {@code m}.
	 * 
	 * The operation views the {@code v} vector as a N x 1 Matrix.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_DIMENSION_MISMATCH__OPERATE} if
	 *             {@code m} is not the same size as {@code this}.
	 * 
	 * @return the {@link BiFunction} that performs operation.
	 */
	public static BiFunction<ArrayVector, ArrayMatrix, ArrayVector> preOperate(boolean parallel) {
		return (v, m) -> {
			LinearExceptionFactory.checkMultiplicationCompatible(v, m);

			double[] result =
					range(0, m.getColumnDimension())
							.mapToDouble(c -> range(0, m.getRowDimension()).mapToDouble(r -> v.getData()[c]
									* m.getData()[c][r]).sum())
							.toArray();

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
	public static UnaryOperator<ArrayMatrix> transpose() {
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
	public static UnaryOperator<ArrayMatrix> transpose(boolean parallel) {
		return m -> {
			double[][] a = m.getData();
			IntStream stream = range(0, m.getColumnDimension());
			stream = parallel ? stream.parallel() : stream;

			double[][] transpose =
					stream.mapToObj(c -> range(0, a.length).mapToDouble(r -> a[r][c]).toArray())
							.toArray(double[][]::new);
			return new ArrayMatrix(transpose);
		};
	}

	/**
	 * Returns the
	 * <a href="http://mathworld.wolfram.com/MaximumAbsoluteRowSumNorm.html">
	 * maximum absolute row sum norm</a> of the matrix.
	 *
	 * @return norm
	 */

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/MaximumAbsoluteRowSumNorm.html">
	 * maximum absolute row sum norm</a> of the matrix.
	 * 
	 * Example {@code norm(true).apply(m);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link Function} that performs the tranpose operation.
	 */
	public static Function<ArrayMatrix, Double> norm(boolean parallel) {
		return m -> {
			double[][] a = m.getData();
			IntStream stream = range(0, m.getColumnDimension());
			stream = parallel ? stream.parallel() : stream;

			return stream
					.mapToDouble(c -> range(0, m.getRowDimension()).mapToDouble(r -> Math.abs(a[r][c])).sum())
					.max().getAsDouble();
		};
	}

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/FrobeniusNorm.html"> Frobenius
	 * norm</a> of the matrix.
	 * 
	 * Example {@code frobeniusNorm(true).apply(m);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @return the {@link Function} that performs the tranpose operation.
	 */
	public static Function<ArrayMatrix, Double> frobeniusNorm(boolean parallel) {
		return m -> {
			double[][] a = m.getData();
			IntStream stream = range(0, m.getColumnDimension());
			stream = parallel ? stream.parallel() : stream;

			return stream
					.mapToDouble(c -> range(0, m.getRowDimension()).mapToDouble(r -> Math.abs(a[r][c])).sum())
					.max().getAsDouble();
		};
	}

	/**
	 * Returns the {@link BiFunction} that multiplies {@code m} with itself
	 * {@code p} times}.
	 *
	 * @param parallel
	 *            Whether to perform the operation concurrently.
	 * 
	 * @throws MathException
	 *             Of type {@code NOT_POSITIVE_EXCEPTION} if {@code p < 0}
	 * 
	 * @return the {@link BiFunction} that performs the {@code m^p} power
	 *         calculation.
	 */
	public static BiFunction<ArrayMatrix, Integer, Matrix> power(boolean parallel) {
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

			ArrayMatrix[] results = new ArrayMatrix[maxI
					+ 1];
			results[0] = (ArrayMatrix) m.copy();

			for (int i = 1; i <= maxI; ++i) {
				results[i] = multiply(parallel).apply(results[i
						- 1],
						results[i
								- 1]);
			}

			ArrayMatrix result = (ArrayMatrix) m.copy();

			for (Integer i : nonZeroPositions) {
				result = multiply(parallel).apply(result, results[i]);
			}
			return result;
		};
	}

	/**
	 * Returns the {@link Function} that calculates the
	 * <a href="http://mathworld.wolfram.com/MatrixTrace.html"> trace of the NxN
	 * square matrix.
	 * 
	 * Example {@code trace().apply(m);}
	 * 
	 * @throws MathException
	 *             Of type {@code MATRIX_NON_SQUARE} if {@code m} is not square.
	 * 
	 * @return the {@link Function} that performs the tranpose operation.
	 */
	public static Function<ArrayMatrix, Double> trace() {
		return trace(false);
	};

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
	public static Function<ArrayMatrix, Double> trace(boolean parallel) {
		return (m) -> {
			checkNonSquareMatrix(m);
			IntStream stream = range(0, m.getColumnDimension());
			stream = parallel ? stream.parallel() : stream;

			return stream.mapToDouble(i -> m.getData()[i][i]).sum();
		};
	}
}