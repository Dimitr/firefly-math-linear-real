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

import static com.fireflysemantics.math.linear.vector.VectorOperations.add;
import static com.fireflysemantics.math.linear.vector.VectorOperations.l1Norm;
import static com.fireflysemantics.math.linear.vector.VectorOperations.subtract;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fireflysemantics.math.exception.ExceptionTypes;
import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.vector.interfaces.Vector;

/**
 * Tests for {@link VectorOperations}.
 */
public class VectorOperationsTest {

	final double ZERO = 0d;

	final ArrayVector v1 = new ArrayVector(new double[] { ZERO, 1d, ZERO, 2d });
	final ArrayVector v2 = new ArrayVector(new double[] { ZERO, 2d, ZERO, 4d });

	final ArrayVector d1 = new ArrayVector(new double[] { 1d, 2d });
	final ArrayVector d2 = new ArrayVector(new double[] { 1d, 2d, 3d });

	@Test
	public void addOperation() {
		Vector result = add().apply(v1, v2);
		assertEquals(result.getEntry(0), 0.0, ZERO);
		assertEquals(result.getEntry(1), 3d, ZERO);
	}

	@Test
	public void addException() {
		try {
			Vector result = add().apply(d1, d2);
		} catch (MathException e) {
			assertEquals(e.getType(), ExceptionTypes.DIMENSION_MISMATCH);
		}
	}

	@Test
	public void subtractOperation() {
		Vector result = subtract().apply(v1, v2);
		assertEquals(result.getEntry(0), 0.0, ZERO);
		assertEquals(result.getEntry(1), -1d, ZERO);
	}

	@Test
	public void l1NormOperation() {
		final double actual = l1Norm().apply(v1);
		final double expected = range(0, v1.getDimension()).mapToDouble(i -> v1.getEntry(i)).sum();
		assertEquals(expected, actual, ZERO);
	}

	@Test
	public void linfNormOperation() {
		final double actual = VectorOperations.lInfNorm().apply(v1);
		final double expected =
				range(0, v1.getDimension()).mapToDouble(i -> Math.abs(v1.getEntry(i))).max().getAsDouble();
		assertEquals(expected, actual, ZERO);
	}
}
