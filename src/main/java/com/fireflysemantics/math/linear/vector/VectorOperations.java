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
package com.fireflysemantics.math.linear.vector;

import static com.fireflysemantics.math.exception.ExceptionTypes.MAE__ZERO_NORM;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkDimensionMismatch;
import static com.fireflysemantics.math.linear.exceptions.LinearExceptionFactory.checkNoVectorData;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.IntStream.range;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.vector.interfaces.Vector;

/**
 * This class is to provides vector algebra function instances that can be be
 * invoked in single thread or parallel per a boolean argument. When needed to
 * speed up the calculations the arguments to the function are implementation
 * specific.
 */
public class VectorOperations {

	/**
	 * Returns a {@link BiFunction} that adds the second vector {@code v2} to
	 * the first vector {@code v1} .
	 * 
	 * Example {@code add().apply(v1, v2);}
	 *
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BinaryOperator<ArrayVector> add() {
		return add(false);
	}

	/**
	 * Returns a {@link BiFunction} that adds the second vector {@code v2} to
	 * the first vector {@code v1} .
	 * 
	 * Example {@code add(true).apply(v1, v2);}
	 *
	 * @param parallel
	 *            Whether to perform the addition in parallel.
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BinaryOperator<ArrayVector> add(boolean parallel) {
		return (v1, v2) -> {
			checkDimensionMismatch(v1, v2);
			IntStream stream = range(0, v1.getDimension());
			double[] a1 = v1.getData();
			double[] a2 = v2.getData();
			stream = parallel ? stream.parallel() : stream;
			return new ArrayVector(stream.mapToDouble(i -> a1[i]
					+ a2[i]).toArray());
		};
	}

	/**
	 * Returns a {@link BiFunction} that subtracts the second vector {@code v2}
	 * from the first vector {@code v1} .
	 * 
	 * Example {@code subtract().apply(v1, v2);}
	 *
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BinaryOperator<ArrayVector> subtract() {
		return subtract(false);
	}

	/**
	 * Returns a {@link BiFunction} that subtracts the second vector {@code v2}
	 * from the first vector {@code v1} .
	 * 
	 * Example {@code subtract(true).apply(v1, v2);}
	 *
	 * @param parallel
	 *            Whether to perform the subtraction in parallel.
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BinaryOperator<ArrayVector> subtract(boolean parallel) {
		return (v1, v2) -> {
			checkDimensionMismatch(v1, v2);
			IntStream stream = range(0, v1.getDimension());
			double[] a1 = v1.getData();
			double[] a2 = v2.getData();
			stream = parallel ? stream.parallel() : stream;
			return new ArrayVector(stream.mapToDouble(i -> a1[i]
					- a2[i]).toArray());
		};
	}

	/**
	 * Returns a {@link BiFunction} that produces the dot product of first
	 * vector {@code v1} and second vector {@code v2} .
	 * 
	 * Example {@code dotProduct().apply(v1, v2);}
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BiFunction<ArrayVector, ArrayVector, Double> dotProduct() {
		return dotProduct(false);
	};

	/**
	 * Returns a {@link BiFunction} that produces the dot product of first
	 * vector {@code v1} and second vector {@code v2} .
	 * 
	 * Example {@code dotProduct(true).apply(v1, v2);}
	 * 
	 * @param parallel
	 *            Whether to perform the multiplication in parallel.
	 * 
	 * @throws MathException
	 *             Of type {@code DIMENSION_MISMATCH} if
	 *             {@code v1.getDimension()} is != {@code v2.getDimension()}.
	 */
	public static BiFunction<ArrayVector, ArrayVector, Double> dotProduct(boolean parallel) {
		return (v1, v2) -> {
			checkDimensionMismatch(v1, v2);
			IntStream stream = range(0, v1.getDimension());
			double[] a1 = v1.getData();
			double[] a2 = v2.getData();
			stream = parallel ? stream.parallel() : stream;
			return stream.mapToDouble(i -> a1[i]
					* a2[i]).sum();
		};
	}

	/**
	 * Returns a {@link Function} that produces the norm of the vector {@code v}
	 * . Example {@code norm().apply(v);}
	 */

	public static Function<Vector, Double> norm() {
		return norm(false);
	};

	/**
	 * Returns a {@link Function} that produces the norm of the vector {@code v}
	 * .
	 * 
	 * Example {@code norm(true).apply(v);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation in parallel.
	 */

	public static Function<Vector, Double> norm(boolean parallel) {
		return (v) -> {
			IntStream stream = range(0, v.getDimension());
			stream = parallel ? stream.parallel() : stream;
			return sqrt(stream.mapToDouble(i -> pow(v.getEntry(i), 2)).sum());
		};
	}

	/**
	 * Returns a {@link Function} that produces the L!Norm of the vector
	 * {@code v} .
	 * 
	 * Example {@code l1Norm().apply(v);}
	 * 
	 */
	public static Function<Vector, Double> l1Norm() {
		return l1Norm(false);
	};

	/**
	 * Returns a {@link Function} that produces the L!Norm of the vector
	 * {@code v} .
	 * 
	 * Example {@code l1Norm(true).apply(v);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation in parallel.
	 * @throws MathException
	 *             Of type {@code NO_DATA} if {@code v1.getDimension()} is == 0.
	 */
	public static Function<Vector, Double> l1Norm(boolean parallel) {
		return (v) -> {
			checkNoVectorData(v.getDimension());
			IntStream stream = range(0, v.getDimension());
			stream = parallel ? stream.parallel() : stream;
			return stream.mapToDouble(i -> abs(v.getEntry(i))).sum();
		};
	}

	/**
	 * Returns a {@link Function} that produces the lInfNorm of the vector
	 * {@code v} .
	 * 
	 * Example {@code lInfNorm().apply(v);}
	 */
	public static Function<Vector, Double> lInfNorm() {
		return lInfNorm(false);
	};

	/**
	 * Returns a {@link Function} that produces the lInfNormNorm of the vector
	 * {@code v} .
	 * 
	 * Example {@code lInfNorm(true).apply(v);}
	 * 
	 * @param parallel
	 *            Whether to perform the operation in parallel.
	 * @throws MathException
	 *             Of type {@code NO_DATA} if {@code v1.getDimension()} is == 0.
	 *
	 */
	public static Function<Vector, Double> lInfNorm(boolean parallel) {
		return (v) -> {
			checkNoVectorData(v.getDimension());
			IntStream stream = range(0, v.getDimension());
			stream = parallel ? stream.parallel() : stream;
			return stream.mapToDouble(i -> abs(v.getEntry(i))).max().getAsDouble();
		};
	}

	/**
	 * Returns a {@link BiFunction} that computes the cosine of the angle
	 * between the first vector {@code v1} and the second vector {@code v2}.
	 * 
	 * Example {@code cosine(true).apply(v1, v2);}
	 *
	 * @param parallel
	 *            Whether to perform the addition in parallel.
	 * 
	 * @throws MathException
	 *             Of type {@code MAE__ZERO_NORM} if either computed norm is
	 *             zero.
	 */
	public static BiFunction<ArrayVector, ArrayVector, Double> cosine() {
		return cosine(false);
	}

	/**
	 * Returns a {@link BiFunction} that computes the cosine of the angle
	 * between the first vector {@code v1} and the second vector {@code v2}.
	 * 
	 * Example {@code cosine(true).apply(v1, v2);}
	 *
	 * @param parallel
	 *            Whether to perform the addition in parallel.
	 * 
	 * @throws MathException
	 *             Of type {@code MAE__ZERO_NORM} if either computed norm is
	 *             zero.
	 */
	public static BiFunction<ArrayVector, ArrayVector, Double> cosine(boolean parallel) {
		return (v1, v2) -> {
			final double norm = norm(parallel).apply(v1);
			final double vNorm = norm(parallel).apply(v2);

			if (norm == 0
					|| vNorm == 0) {
				throw new MathException(MAE__ZERO_NORM);
			}

			return dotProduct(parallel).apply(v1, v2)
					/ (norm
							* vNorm);
		};
	}
}
