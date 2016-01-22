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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fireflysemantics.math.exception.ExceptionKeys;
import com.fireflysemantics.math.exception.ExceptionTypes;
import com.fireflysemantics.math.exception.MathException;
import com.fireflysemantics.math.linear.vector.interfaces.Vector;

/**
 * Test cases for the {@link ArrayVector} class.
 */
public class ArrayVectorTest {

	@Test
	public void api() {
		Vector RV = new ArrayVector(new double[] { 1d, 2d });
		assertEquals(RV.getDimension(), 2);
		assertEquals(RV.getEntry(0), 1d, Double.MIN_VALUE);
		assertEquals(RV.getEntry(1), 2d, Double.MIN_VALUE);

		RV.setEntry(0, 3d);
		assertEquals(RV.getEntry(0), 3d, Double.MIN_VALUE);
	}

	@Test()
	public void exceptions() {
		try {
			new ArrayVector(new double[] { 1d, 2d }).getEntry(2);
		} catch (MathException e) {
			assertEquals(e.getType(), ExceptionTypes.OUT_OF_RANGE);
		}
		try {
			new ArrayVector(new double[] { 1d, 2d }).setEntry(2, 10d);
		} catch (MathException e) {
			assertEquals(e.getType(), ExceptionTypes.OUT_OF_RANGE);
			assertEquals(e.get(ExceptionKeys.INDEX), 2);
		}
	}
}
