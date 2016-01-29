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

import static com.fireflysemantics.math.linear.matrix.MatrixOperations.add;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.addScalar;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.multiply;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.multiplyScalar;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.norm;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.operate;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.power;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.subtract;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.subtractScalar;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.trace;
import static com.fireflysemantics.math.linear.matrix.MatrixOperations.transpose;
import static java.util.Arrays.deepEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.vector.ArrayVector;

/**
 * Tests for various Matrix operations. Use Arrays.deepToString() to get a
 * String value containing results. For example:
 * 
 * Arrays.deepToString(resultA1A2.toArray())
 * 
 * Yields: [[92.0], [18.0], [59.0]]
 */
public class MatrixOperationsTest {

	@Test
	public void testAddScalar() {
		double[][] a1 = { { 1d, 1d }, { 3d, 3d } };
		double[][] expected = { { 2d, 2d }, { 4d, 4d } };
		double[][] result = addScalar().apply(new SimpleMatrix(a1), 1d).toArray();

		assertTrue(Arrays.deepEquals(result, expected));
	}

	@Test
	public void testSubtractScalar() {
		double[][] a1 = { { 1d, 1d }, { 3d, 3d } };
		double[][] expected = { { 0d, 0d }, { 2d, 2d } };
		double[][] result = subtractScalar().apply(new SimpleMatrix(a1), 1d).toArray();

		assertTrue(Arrays.deepEquals(result, expected));
	}

	@Test
	public void testMultiplyScalar() {
		double[][] a1 = { { 1d, 1d }, { 3d, 3d } };
		double[][] expected = { { 2d, 2d }, { 6d, 6d } };
		double[][] result = multiplyScalar().apply(new SimpleMatrix(a1), 2d).toArray();

		assertTrue(Arrays.deepEquals(result, expected));
	}

	@Test
	public void testAdd() {
		double[][] a1 = { { 1d, 1d }, { 3d, 3d } };
		double[][] a2 = { { 2d, 2d }, { 1d, 1d } };
		double[][] expected = { { 3d, 3d }, { 4d, 4d } };
		double[][] result = add().apply(new SimpleMatrix(a1), new SimpleMatrix(a2)).toArray();

		assertTrue(Arrays.deepEquals(result, expected));
	}

	@Test
	public void testSubtract() {
		double[][] a1 = { { 5d, 5d }, { 3d, 3d } };
		double[][] a2 = { { 4d, 4d }, { 1d, 1d } };
		double[][] expected = { { 1d, 1d }, { 2d, 2d } };
		double[][] result = subtract().apply(new SimpleMatrix(a1), new SimpleMatrix(a2)).toArray();

		assertTrue(Arrays.deepEquals(result, expected));
	}

	@Test
	public void testMultiply() {
		double[][] a1 = { { 4d, 8d }, { 0d, 2d }, { 1d, 6d } };
		double[][] a2 = { { 5d }, { 9d } };
		double[][] a3 = { { 5d, 2d, 5d, 5d }, { 9d, 4d, 5d, 5d } };

		double[][] expectedA1A2 = { { 92d }, { 18d }, { 59d } };
		double[][] expectedA1A3 = { { 92d, 40d, 60d, 60d }, { 18d, 8d, 10d, 10d }, { 59d, 26d, 35d, 35d } };

		SimpleMatrix am1 = new SimpleMatrix(a1);
		SimpleMatrix am2 = new SimpleMatrix(a2);
		SimpleMatrix am3 = new SimpleMatrix(a3);

		SimpleMatrix resultA1A2 = multiply().apply(am1, am2);
		SimpleMatrix resultA1A3 = multiply().apply(am1, am3);

		assertTrue(Arrays.deepEquals(resultA1A2.toArray(), expectedA1A2));
		assertTrue(Arrays.deepEquals(resultA1A3.toArray(), expectedA1A3));
	}

	@Test
	public void testOperate() {
		ArrayVector v = new ArrayVector(new double[] { 3, 4, 3 });
		SimpleMatrix m = new SimpleMatrix(new double[][] { { 0d, 3d, 5d }, { 5d, 5d, 2d } });
		double[] expected = { 27, 41 };

		ArrayVector result = operate().apply(m, v);
		assertTrue(Arrays.equals(result.toArray(), expected));
	}

	@Test
	public void testPreOperate() {
		ArrayVector v = new ArrayVector(new double[] { 1d, 2d, 3d });
		SimpleMatrix m = new SimpleMatrix(new double[][] { { 1d, 1d, 1d }, { 2d, 2d, 2d }, { 3d, 3d, 3d } });
		double[] expected = { 6, 12, 18 };

		ArrayVector result = operate().apply(m, v);
		assertTrue(Arrays.equals(result.toArray(), expected));
	}

	@Test
	public void testNorm() {
		double[][] data = { { 1, -7 }, { -2, -3 } };
		double result = norm().apply(new SimpleMatrix(data));
		assertEquals(10d, result, Double.MIN_VALUE);

		SimpleMatrix m = new SimpleMatrix(new double[][] { { -100 } });
		result = MatrixOperations.norm().apply(m);
		assertEquals(100d, result, Double.MIN_VALUE);

	}

	@Test
	public void testFrobeniusNorm() {
		double[][] data = { { 1, -7 }, { -2, -3 } };
		double result = MatrixOperations.frobeniusNorm(false).apply(new SimpleMatrix(data));
		System.out.println(result);
		assertEquals(7.937253933193772, result, 100
				* Double.MIN_VALUE);

		SimpleMatrix m = new SimpleMatrix(new double[][] { { -100 } });
		result = MatrixOperations.frobeniusNorm(false).apply(m);
		assertEquals(100d, result, Double.MIN_VALUE);

	}

	@Test(expected = MathException.class)
	public void testNorm_NO_DATA_EXCEPTION() {
		SimpleMatrix m = new SimpleMatrix(new double[][] { {} });
		norm().apply(m);
	}

	@Test
	public void testTranspose() {
		double[][] data = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		double[][] expected = { { 1, 3, 5 }, { 2, 4, 6 } };

		SimpleMatrix transpose = new SimpleMatrix(data);
		transpose = transpose().apply(transpose);
		assertTrue(Arrays.deepEquals(expected, transpose.toArray()));
		transpose = transpose().apply(transpose);
		assertTrue(Arrays.deepEquals(data, transpose.toArray()));

		double[][] data1 = { { 0d } };
		transpose = transpose().apply(new SimpleMatrix(data1));
		assertTrue(deepEquals(data1, transpose.toArray()));
	}

	@Test
	public void testTrace() {
		double[][] data = { { 1, 2 }, { 3, 4 } };
		double trace = trace().apply(new SimpleMatrix(data));
		assertEquals(5, trace, Double.MIN_VALUE);
	}

	@Test
	public void testPower() {
		double[][] data = { { 1, 2 }, { 3, 4 } };
		double[][] expected2 = { { 7, 10 }, { 15, 22 } };
		double[][] expected3 = { { 37, 54 }, { 81, 118 } };

		SimpleMatrix result = (SimpleMatrix) MatrixOperations.power(false).apply(new SimpleMatrix(data), 2);
		assertTrue(deepEquals(expected2, result.toArray()));
		result = (SimpleMatrix) power(false).apply(new SimpleMatrix(data), 3);
		assertTrue(deepEquals(expected3, result.toArray()));
		// System.out.println(Arrays.deepToString(result.toArray()));
	}
}