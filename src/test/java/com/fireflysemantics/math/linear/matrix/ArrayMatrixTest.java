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

import static com.fireflysemantics.math.linear.matrix.MatrixOperations.transpose;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import org.junit.Test;

public class ArrayMatrixTest {

	@Test
	public void testTranspose() {
		double[][] data = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
		double[][] transposed = { { 1, 3, 5 }, { 2, 4, 6 } };

		ArrayMatrix transpose = new ArrayMatrix(data);
		transpose = transpose().apply(new ArrayMatrix(data));
		assertTrue(Arrays.deepEquals(transposed, transpose.getData()));
		transpose = transpose().apply(transpose);
		assertTrue(Arrays.deepEquals(data, transpose.getData()));
	}

	public static BiFunction<ArrayMatrix, ArrayMatrix, ArrayMatrix> multiply(boolean parallel) {

		return (m1, m2) -> {
			// checkMultiplicationCompatible(m1, m2);

			final int m1Rows = m1.getRowDimension();
			final int m2Rows = m2.getRowDimension();
			final int m1Cols = m1.getColumnDimension();
			final int m2Cols = m2.getColumnDimension();

			double[][] a1 = m1.getData();
			double[][] a2 = m2.getData();

			final double[][] result = new double[m1Rows][m2Cols];

			// Buffer for the tranpose of each md2 column
			final double[] transpose = new double[m1Rows];

			range(0, m2Cols).forEach(m2Col -> {
				range(0, m2Rows).forEach(m2Row -> {
					transpose[m2Row] = a2[m2Row][m2Col];
				});

				range(0, m1Rows).forEach(row -> {
					final double[] dataRow = a1[row];
					double sum = 0;
					for (int m1Col = 0; m1Col < m1Cols; m1Col++) {
						sum += dataRow[m1Col]
								* transpose[m1Col];
					}
					result[row][m2Col] = sum;
				});
			});

			return new ArrayMatrix(result);
		};
	}

	public double[][] mult(double[][] m1, double[][] m2) {
		return Arrays.stream(m1).map(r -> IntStream.range(0, m2.length)
				.mapToDouble(i -> IntStream.range(0, m2[i].length).mapToDouble(j -> r[j]
						* m2[j][i]).sum())
				.toArray()).toArray(double[][]::new);
	}

	public double[][] mult2(double[][] m1, double[][] m2) {
		return Arrays.stream(m1).map(r -> IntStream.range(0, m2[0].length)
				.mapToDouble(i -> IntStream.range(0, m2.length).mapToDouble(j -> r[j]
						* m2[j][i]).sum())
				.toArray()).toArray(double[][]::new);
	}

	@Test
	public void testM() {
		double[][] md1 = { { 4d, 8d }, { 0d, 2d }, { 1d, 6d } };
		double[][] md2 = { { 5d, 2d, 5d, 5d }, { 9d, 4d, 5d, 5d } };

		ArrayMatrix a1 = new ArrayMatrix(md1);
		ArrayMatrix a2 = new ArrayMatrix(md2);

		ArrayMatrix r = multiply(false).apply(a1, a2);

		assertEquals(r.getData()[2][1], 26, Double.MIN_VALUE);

		double[][] r2 = mult2(md1, md2);

		// assertEquals(r2[2][1], 26, Double.MIN_VALUE);
		System.out.println(Arrays.deepToString(r.getData()));
	}

	@Test
	public void constructor() {
		double[][] data = { { 1, 2 }, { 3, 4 } };

		ArrayMatrix matrix = new ArrayMatrix(data);

		assertSame(data, matrix.getData());
		assertSame(data[0], matrix.getData()[0]);
		assertEquals(data[0][0], matrix.getData()[0][0], Double.MIN_VALUE);

		matrix = new ArrayMatrix(data);

		assertSame(data, matrix.getData());
		assertSame(data[0], matrix.getData()[0]);
		assertNotSame(data[0][0], matrix.getData()[0][0]);

		assertTrue(matrix.getData()[0][0] == 1d);
		assertTrue(matrix.getData()[0][1] == 2d);
		assertTrue(matrix.getData()[1][0] == 3d);
		assertTrue(matrix.getData()[1][1] == 4d);
	}
}
